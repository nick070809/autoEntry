package org.nixk.nodes;

import org.nixk.nodes.dto.BusiContext;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/7/1
 */

public class ProxyNode {

    BNode currentNode;

    ProxyNode nextProxyNode;


    public ProxyNode(BNode bNode) {
        this.currentNode = bNode;
        if(bNode != null){
            nextProxyNode  = new ProxyNode(bNode.getNextNode());
        }
    }



    private ProxyNode getNext() {
        return nextProxyNode;
    }

    public void deal(BusiContext context) {
        if(currentNode == null ){
            return;
        }
        currentNode.deal(context);
        getNext().deal(context);
    }

}
