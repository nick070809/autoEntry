package org.nixk.txf.ext;

import org.nixk.txf.common.ProfitContext;

/**
 * Description ： 交易分账
 * Created by  xianguang.skx
 * Since 2020/6/12
 */

public interface TradeProfitExt extends Ext{

    /**
     * 获取收款账户
      * @return
     */
    public  String getInaccount( );

    /**
     * 检查金额
      */
    public  Long getFrofitAmount( );


    /**
     * 获取优先级
      */
    public  Integer getPriority( );


    /**
     * 获取超时时间
      */
    public  Integer getTimeOutDays( );




    public void craeteProfit(ProfitContext profitContext);


}
