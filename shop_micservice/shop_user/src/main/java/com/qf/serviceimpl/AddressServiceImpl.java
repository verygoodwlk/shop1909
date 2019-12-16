package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.AddressMapper;
import com.qf.entity.Address;
import com.qf.entity.User;
import com.qf.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> queryByUid(Integer uid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        return addressMapper.selectList(queryWrapper);
    }

    @Override
    public Address queryById(Integer id) {
        return null;
    }

    /**
     * #存储过程 - 添加收货地址
     * #Java方法
     * drop procedure if exists insert_address;
     * delimiter %%
     * create procedure insert_address(
     * 	in p_person varchar(10),
     * 	in p_address varchar(200),
     * 	in p_phone char(11),
     * 	in p_code char(6),
     * 	in p_isdefault int,
     * 	in p_uid int)
     * begin
     * 	#判断是否新增的默认收货地址
     * 	if p_isdefault = 1 then
     * 		#修改原来的收货地址为非默认
     * 		update address set isdefault = 0 where uid = p_uid and isdefault = 1;
     * 	end if;
     *
     * 	#新增收货地址
     * 	insert into address value(null, p_person, p_address, p_phone, p_code, p_isdefault, p_uid, now(), 0);
     * end %%
     * delimiter ;
     *
     * @param address
     * @param user
     * @return
     */
    @Override
    public int insertAddress(Address address, User user) {

//        address.setUid(user.getId());
//        if(address.getIsdefault() == 1){
//            //修改原来的默认收货地址
//            QueryWrapper queryWrapper = new QueryWrapper();
//            queryWrapper.eq("uid", user.getId());
//            queryWrapper.eq("isdefault", 1);
//            Address defaultAddress = addressMapper.selectOne(queryWrapper);
//            defaultAddress.setIsdefault(0);
//            addressMapper.updateById(defaultAddress);
//        }
//        return addressMapper.insert(address);

        address.setUid(user.getId());
        return addressMapper.insertAddress(address);
    }
}
