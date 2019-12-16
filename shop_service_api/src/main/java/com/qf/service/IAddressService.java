package com.qf.service;

import com.qf.entity.Address;
import com.qf.entity.User;

import java.util.List;

public interface IAddressService {

    List<Address> queryByUid(Integer uid);

    Address queryById(Integer id);

    int insertAddress(Address address, User user);

}
