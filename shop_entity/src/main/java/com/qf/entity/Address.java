package com.qf.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Address extends BaseEntity {

    private Integer uid;
    private String person;
    private String address;
    private String phone;
    private String code;
    private Integer isdefault = 0;
}
