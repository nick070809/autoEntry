package org.nixk.txf.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Description ：分账上下文
 * Created by  xianguang.skx
 * Since 2020/6/12
 */

@Data
public class ProfitContext {

    private BizOrder bizOrder ;

    private List<Profit> profits  = new ArrayList<>();



    public  static  ProfitContext  of(BizOrder bizOrder){
        ProfitContext profitContext = new  ProfitContext();
        profitContext.setBizOrder(bizOrder);
        return profitContext;
    }


}
