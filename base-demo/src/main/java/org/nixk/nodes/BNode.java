package org.nixk.nodes;

import org.nixk.nodes.dto.BusiContext;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/7/1
 */

public interface BNode {


    public  void  deal(BusiContext context);


    public BNode getNextNode();

}
