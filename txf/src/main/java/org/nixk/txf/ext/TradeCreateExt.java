package org.nixk.txf.ext;

import org.nixk.txf.common.CreateContext;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/6/12
 */

public interface TradeCreateExt extends Ext{


    public  void preCreate(CreateContext payContext);

    public  void afterCreate(CreateContext payContext);



}
