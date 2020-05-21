package org.nixk;

import lombok.Data;

import java.util.Date;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/5/21
 */

@Data
public class NObjectUser {


    static {
        System.out.println("=========" + new Date());

    }


    private Long id = 10L;
    private String name ="nixk";




}
