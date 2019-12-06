package com.qf.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Goods;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private SolrClient solrClient;

    @Override
    public int insertSolr(Goods goods) {

        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", goods.getId() + "");
        document.addField("subject", goods.getSubject());
        document.addField("info", goods.getInfo());
        document.addField("price", goods.getPrice().doubleValue());
        document.addField("save", goods.getSave());
        document.addField("image", goods.getFmurl());

        try {
            solrClient.add(document);
            //增删改都必须要commit
            solrClient.commit();

            return 1;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public List<Goods> querySolr(String keyword) {
        System.out.println("搜索服务关键字：" + keyword);

        SolrQuery query = new SolrQuery();
        if(keyword != null && !keyword.equals("")){
            query.setQuery("subject:" + keyword + " || info:" + keyword);
        } else {
            query.setQuery("*:*");
        }

        //进行分页的设置 limit 0,10
        query.setStart(0);
        query.setRows(10);

        //设置搜索的高亮
        query.setHighlight(true);//开启高亮
        query.setHighlightSimplePre("<font color='red'>");//前缀
        query.setHighlightSimplePost("</font>");//后缀
        query.addHighlightField("subject");
//        query.setHighlightSnippets(3);//高亮折叠的次数
//        query.setHighlightFragsize(10);//高亮折叠的大小


        try {
            QueryResponse response = solrClient.query(query);

            //获得高亮后的结果
            //Map<id, 高亮信息>
            //高亮信息 -   Map<字段, 高亮内容的集合>
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

            //获得搜索的结果
            SolrDocumentList results = response.getResults();

            //符合关键字的记录条数
            long numFound = results.getNumFound();//1千万条

            List<Goods> goodsList = new ArrayList<>();
            for (SolrDocument document : results) {
                //document -> Goods
                Goods goods = new Goods();
                goods.setId(Integer.parseInt((String)document.get("id")));
                goods.setSubject(document.get("subject") + "")
                        .setSave((int)document.get("save"))
                        .setPrice(BigDecimal.valueOf((double)document.get("price")))
                        .setFmurl(document.get("image") + "");

                //处理高亮结果
                if(highlighting.containsKey(goods.getId() + "")){
                    //说明当前商品有高亮信息
                    //依次获取高亮的字段
                    Map<String, List<String>> stringListMap = highlighting.get(goods.getId() + "");
                    List<String> subject = stringListMap.get("subject");
                    if(subject != null){
                        goods.setSubject(subject.get(0));
                    }
                }

                goodsList.add(goods);
            }

            return goodsList;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
