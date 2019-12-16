package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.CartMapper;
import com.qf.entity.Goods;
import com.qf.entity.ShopCart;
import com.qf.entity.User;
import com.qf.service.ICartService;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 添加购物车
     * @param cart
     * @param user
     * @return
     */
    @Override
    public String insertCart(ShopCart cart, User user, String cartToken) {

        //通过商品id查询商品详细信息
        Goods goods = goodsService.queryById(cart.getGid());
        //计算当前的商品小计
        BigDecimal cartPrice = goods.getPrice().multiply(BigDecimal.valueOf(cart.getNumber()));
        cart.setCartPrice(cartPrice);

        if(user != null){
            //已经登录
            cart.setUid(user.getId());
            cartMapper.insert(cart);
        } else {
            //未登录
            cartToken = cartToken != null ? cartToken : UUID.randomUUID().toString();
            redisTemplate.opsForList().leftPush(cartToken, cart);


            //全部取出list
            //遍历list
            //修改某个购物车的数量，将这个购物车商品提到最上面
            //清空所有list
            //将当前list写入redis
        }

        return cartToken;
    }

    /**
     * 查询用户的购物车信息
     * @param cartToken
     * @param user
     * @return
     */
    @Override
    public List<ShopCart> listCarts(String cartToken, User user) {

        List<ShopCart> shopCarts = null;
        if(user != null){
            //直接查询数据库
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("uid", user.getId());
            queryWrapper.orderByDesc("create_time");
            shopCarts = cartMapper.selectList(queryWrapper);
        } else {
            //查询redis
            if(cartToken != null){
                //获得redis中购物车的长度
                Long size = redisTemplate.opsForList().size(cartToken);
                shopCarts = redisTemplate.opsForList().range(cartToken,0, size);
            }
        }

        //关联查询所有购物车的商品信息，方便页面展示
        for (ShopCart shopCart : shopCarts) {
            Integer gid = shopCart.getGid();
            Goods goods = goodsService.queryById(gid);
            shopCart.setGoods(goods);
        }

        return shopCarts;
    }

    @Override
    public List<ShopCart> queryCartsByGid(Integer[] gid, User user) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", user.getId());
        queryWrapper.in("gid", gid);
        List<ShopCart> carts = cartMapper.selectList(queryWrapper);

        //关联查询所有购物车的商品信息，方便页面展示
        for (ShopCart shopCart : carts) {
            Integer id = shopCart.getGid();
            Goods goods = goodsService.queryById(id);
            shopCart.setGoods(goods);
        }

        return carts;
    }
}
