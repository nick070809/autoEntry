package org.nixk.clazz.loader;
import org.junit.Test;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/5/21
 */

public class TestUnit {


    @Test
    public void testClassLoader() throws ClassNotFoundException {

        NClassLoader nClassLoader = new NClassLoader();
       // nClassLoader.
        Class<?> clazz = nClassLoader.loadClass("org.nixk.clazz.loader.NObjectUser1");

        System.out.println(clazz.getDeclaredFields().length);

    }


    @Test
    public void classForname() throws ClassNotFoundException {
        //Class<?> clazz = Class.forName("org.nixk.clazz.loader.NObjectUser1"); //appLoader
        //java.lang.ClassNotFoundException: org.nixk.clazz.loader.NObjectUser1

        Class<?> clazz = Class.forName("org.nixk.clazz.loader.NObjectUser1",true ,new NClassLoader()); //myclassLoader
        //initialize表示是否初始化类 ,java.lang.ClassNotFoundException: NClassLoader not found Class org.nixk.clazz.loader.NObjectUser1

    }


    @Test
    public void classInitialize() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("org.nixk.clazz.loader.NObjectUser",true ,new NClassLoader()); //myclassLoader
        //initialize表示是否初始化类 , 会执行静态块

    }


}
