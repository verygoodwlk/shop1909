package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Goods;

import java.util.List;

public interface GoodsMapper extends BaseMapper<Goods> {

    List<Goods> queryList();

    Goods queryById(Integer gid);
}
