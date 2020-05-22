package org.nixk.clazz.loader;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/5/21
 */

public class NClassLoader extends ClassLoader{

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //ClassLoader.loadClass方法中搜索不到类时，会调用此方法 ，显然需要重新编译类文件
        //定义ForwardingJavaFileManager 对原文件进行编译
        //参考 org.kx.util.reflect.MyObjectMaker
        throw  new ClassNotFoundException("NClassLoader not found Class "+ name);
    }
}
