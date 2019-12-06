package com.qf.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qf.entity.Goods;

import java.util.List;

public interface IGoodsService {

    List<Goods> list();

    IPage<Goods> listPage(Page<Goods> page);

    int insert(Goods goods);

    Goods queryById(Integer id);
}
