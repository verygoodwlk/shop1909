package com.qf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Goods extends BaseEntity {

    private String subject;
    private String info;
    private BigDecimal price;
    private Integer save;

    @TableField(exist = false)
    private String fmurl;//封面的图片路径

    @TableField(exist = false)
    private List<String> otherurls;//其他图片

//    public static void main(String[] args) {
//        BigDecimal bigDecimal1 = BigDecimal.valueOf(5.0);
//        BigDecimal bigDecimal2 = BigDecimal.valueOf(4.9);
//
//        BigDecimal subtract = bigDecimal1.subtract(bigDecimal2);
//        System.out.println(subtract);
//    }

}
