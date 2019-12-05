package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qf.entity.Goods;

import java.util.List;

public interface GoodsMapper extends BaseMapper<Goods> {

    List<Goods> queryList();

    //分页查询全部
    IPage<Goods> queryListPage(Page<Goods> page);

    Goods queryById(Integer gid);
}
