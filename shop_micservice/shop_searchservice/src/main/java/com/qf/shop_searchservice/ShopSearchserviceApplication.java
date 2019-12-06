package com.qf.shop_searchservice;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.qf")
@DubboComponentScan("com.qf.service")
public class ShopSearchserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopSearchserviceApplication.class, args);
    }

}
