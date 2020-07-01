package org.nixk.nodes.impl;

import org.nixk.nodes.BNode;
import org.nixk.nodes.dto.BusiContext;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/7/1
 */

public class EndNode implements BNode {


  /*  @Override
    public void beforeDeal(BusiContext context) {

    }*/

    @Override
    public void deal(BusiContext context) {
        System.out.println("EndNode NOTHING TO DO .");
    }

 /*   @Override
    public void nextNodeDeal() {

    }*/

    @Override
    public BNode getNextNode() {
        return null;
    }


    public EndNode() { }
}
