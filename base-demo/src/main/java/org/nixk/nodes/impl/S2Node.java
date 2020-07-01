package org.nixk.nodes.impl;

import org.nixk.nodes.BNode;
import org.nixk.nodes.BaseNode;
import org.nixk.nodes.dto.BusiContext;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/7/1
 */

public class S2Node extends BaseNode implements BNode {


    @Override
    public void deal(BusiContext context) {
        System.out.println(this.getClass().getSimpleName() +","+context.getUser().getName()+ " , before state : "+ context.getUser().getState());
        context.getUser().setState(context.getUser().getState() + 1);
    }


}
