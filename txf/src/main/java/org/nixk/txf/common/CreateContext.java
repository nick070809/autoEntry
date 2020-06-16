package org.nixk.txf.common;

import lombok.Data;

/**
 * Description ： create上下文
 * Created by  xianguang.skx
 * Since 2020/6/12
 */

@Data
public class CreateContext {

    private BizOrder bizOrder ;


    public  static  CreateContext  of(BizOrder bizOrder){
        CreateContext createContext = new  CreateContext();
        createContext.setBizOrder(bizOrder);
        return createContext;
    }
}
