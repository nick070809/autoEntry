package org.nixk.txf.ext;

import org.nixk.txf.common.PayContext;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/6/12
 */

public interface TradePayExt extends Ext{

    public  void prePay(PayContext payContext);

    public  void afterPay(PayContext payContext);


}
