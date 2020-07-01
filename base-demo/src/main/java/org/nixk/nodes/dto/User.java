package org.nixk.nodes.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/7/1
 */

@Data
public class User implements Serializable {

    private  String name ;

    private  Integer state  = 0 ;


    private  String content ;


    public User(String name, Integer state, String content) {
        this.name = name;
        this.state = state;
        this.content = content;
    }
}
