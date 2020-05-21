package org.nixk.bizModules;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/5/20
 */

public interface BizModule {

    public  Object dealBiz(Object obj);

    public  String getBizIndentiry();

    public  void register();

}
