package org.nixk.function;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/5/27
 */

@FunctionalInterface
public interface BizFilterInterface {

    abstract  boolean isIn(Object request);

    public default String  covertString(Object request){
            return  request.toString();
    }
}
