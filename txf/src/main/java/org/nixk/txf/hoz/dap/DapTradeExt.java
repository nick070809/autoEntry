package org.nixk.txf.hoz.dap;

import org.nixk.txf.common.BizOrder;
import org.nixk.txf.common.CreateContext;
import org.nixk.txf.common.Profit;
import org.nixk.txf.common.ProfitContext;
import org.nixk.txf.ext.TradeCreateExt;
import org.nixk.txf.ext.TradeProfitExt;
import org.nixk.txf.extension.PddExt;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/6/12
 */

@PddExt(codes = {DapConstants.BIZ_CODE})
public class DapTradeExt implements TradeCreateExt,TradeProfitExt {

    private ProfitContext profitContext ;
    @Override
    public String getInaccount( ) {

        return profitContext.getBizOrder().getPayerId();
     }

    @Override
    public Long getFrofitAmount( ) {
        return  profitContext.getBizOrder().getDisburseFee();
    }

    @Override
    public Integer getPriority( ) {
        // 不可变
        return DapConstants.PROFIT_PRIORITY;
    }

    @Override
    public Integer getTimeOutDays( ) {
        // 不可变
        String dapFlag = profitContext.getBizOrder().getAttribute().get("dapFlag");
        return  Integer.parseInt(dapFlag);
    }

    @Override
    public void preCreate(CreateContext createContext) {
        createContext.getBizOrder().getAttribute().put("dapFlag","3");
    }

    @Override
    public void afterCreate(CreateContext createContext) {

    }

    public void craeteProfit(ProfitContext profitContext){

        Profit profit = new Profit();
        BizOrder bizOrder = profitContext.getBizOrder();
        this.profitContext = profitContext;

        profit.setBizCode(DapConstants.BIZ_CODE);
        profit.setInAccount(getInaccount());
        profit.setPriority(getPriority());
        profit.setOutAccount(bizOrder.getPayerId());
        profit.setProfitFee(getFrofitAmount());
        profitContext.getProfits().add(profit);

    }
}
