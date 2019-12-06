package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

public interface ISearchService {

    /**
     * 添加索引库
     * @param goods
     * @return
     */
    int insertSolr(Goods goods);

    /**
     * 根据关键字查询索引库
     * @param keyword
     * @return
     */
    List<Goods> querySolr(String keyword);

}
