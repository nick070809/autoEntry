package org.nixk.nodes.impl;

import org.nixk.nodes.BNode;
import org.nixk.nodes.BaseNode;
import org.nixk.nodes.dto.BusiContext;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/7/1
 */

public class RootNode  extends BaseNode implements BNode {


    @Override
    public void deal(BusiContext context) {
         //beforeDeal(context);
         System.out.println("RootNode NOTHING TO DO .");
         //nextNodeDeal();
    }



}
