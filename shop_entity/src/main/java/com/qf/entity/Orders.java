package com.qf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class Orders extends BaseEntity {

    private String orderid;
    private Integer uid;
    private String person;
    private String address;
    private String phone;
    private String code;
    private BigDecimal allprice;

    @TableField(exist = false)
    private List<OrderDetils> orderDetils;
}
