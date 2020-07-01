package org.nixk.nodes;

import org.junit.Before;
import org.junit.Test;
import org.nixk.nodes.dto.BusiContext;
import org.nixk.nodes.dto.User;
import org.nixk.nodes.impl.*;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/7/1
 */

public class RunNodes {
    RootNode rootNode;
    ProxyNode rootNodeProxy;


    @Before
    public void registerNodeLink() {

        EndNode endNode = new EndNode();
        S4Node s4Node = new S4Node();
        S3Node s3Node = new S3Node();
        S1Node s1Node = new S1Node();
        S2Node s2Node = new S2Node();
        rootNode = new RootNode();


        rootNode.setNextNode(s1Node);
        s1Node.setNextNode(s3Node);
        s3Node.setNextNode(s4Node);
        s4Node.setNextNode(s2Node);
        s2Node.setNextNode(endNode);



        rootNodeProxy = new ProxyNode(rootNode);

    }


    @Test
    public void runNode() throws InterruptedException {

        BusiContext busiContext0 = new BusiContext();
        User user0 = new User("zhans", 10, "this is a test .");
        busiContext0.setUser(user0);


        BusiContext busiContext1 = new BusiContext();
        User user1 = new User("lisi", 100, "this is a test .");
        busiContext1.setUser(user1);


        BusiContext busiContext2 = new BusiContext();
        User user2 = new User("wangwu", 1000, "this is a test .");
        busiContext2.setUser(user2);


        new Thread(new Runnable() {
            @Override
            public void run() {
                rootNodeProxy.deal(busiContext0);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                rootNodeProxy.deal(busiContext1);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                rootNodeProxy.deal(busiContext2);
            }
        }).start();

        Thread.sleep(2000L);
    }

}
