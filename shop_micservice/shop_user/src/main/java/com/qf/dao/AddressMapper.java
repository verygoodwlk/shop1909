package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Address;

public interface AddressMapper extends BaseMapper<Address> {

    int insertAddress(Address address);

}
