package org.nixk.txf.common;

import lombok.Data;

/**
 * Description ： pay上下文
 * Created by  xianguang.skx
 * Since 2020/6/12
 */

@Data
public class PayContext {

    private BizOrder bizOrder ;


    public  static  PayContext  of(BizOrder bizOrder){
        PayContext payContext = new  PayContext();
        payContext.setBizOrder(bizOrder);
        return payContext;
    }


}
