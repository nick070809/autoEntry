package org.nixk.nodes;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/7/1
 */

public  abstract  class BaseNode {
    BNode nextNode;

    public BNode getNextNode() {
        return nextNode;
    }

    public void setNextNode(BNode nextNode) {
        this.nextNode = nextNode;
    }
}
