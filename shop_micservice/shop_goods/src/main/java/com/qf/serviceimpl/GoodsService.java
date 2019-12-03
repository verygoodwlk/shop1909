package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.GoodsImagesMapper;
import com.qf.dao.GoodsMapper;
import com.qf.entity.Goods;
import com.qf.entity.GoodsImages;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoodsService implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsImagesMapper goodsImagesMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Goods> list() {
        return goodsMapper.queryList();
    }

    @Override
    @Transactional
    public int insert(Goods goods) {

        //保存商品信息
        goodsMapper.insert(goods);

        //保存封面
        GoodsImages goodsImages = new GoodsImages()
                .setGid(goods.getId())
                .setIsfengmian(1)
                .setUrl(goods.getFmurl());

        goodsImagesMapper.insert(goodsImages);

        //保存其他图片
        for (String otherurl : goods.getOtherurls()) {
            GoodsImages gi = new GoodsImages()
                    .setGid(goods.getId())
                    .setIsfengmian(0)
                    .setUrl(otherurl);

            goodsImagesMapper.insert(gi);
        }

        return 1;
    }
}
