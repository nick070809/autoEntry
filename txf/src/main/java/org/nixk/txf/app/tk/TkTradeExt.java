package org.nixk.txf.app.tk;

import org.nixk.txf.common.BizOrder;
import org.nixk.txf.common.PayContext;
import org.nixk.txf.common.Profit;
import org.nixk.txf.common.ProfitContext;
import org.nixk.txf.ext.TradePayExt;
import org.nixk.txf.ext.TradeProfitExt;
import org.nixk.txf.extension.PddExt;


/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/6/12
 */

@PddExt(codes = {TkConstants.BIZ_CODE})
public class TkTradeExt implements TradePayExt, TradeProfitExt {

    private ProfitContext profitContext;

    @Override
    public void prePay(PayContext payContext) {
        //订单打标 可变
        payContext.getBizOrder().getAttribute().put("tkTuan", "8934589_300");
    }

    @Override
    public void afterPay(PayContext payContext) {
        //订单打标 不可变
        payContext.getBizOrder().getAttribute().put("XXX", "1");
    }

    @Override
    public String getInaccount() {
        //获取淘客活动团
        String team = this.profitContext.getBizOrder().getAttribute().get("tkTuan");

        return team.split("_")[0];
    }

    @Override
    public Long getFrofitAmount() {
        //获取  不可变
        String team = this.profitContext.getBizOrder().getAttribute().get("tkTuan");
        Long setteFee = Long.parseLong(team.split("_")[1]);
        return setteFee;
    }

    @Override
    public Integer getPriority() {
        // 不可变
        return TkConstants.PROFIT_PRIORITY;
    }

    @Override
    public Integer getTimeOutDays() {
        // 不可变
        return 2;
    }


    public void craeteProfit(ProfitContext profitContext) {

        Profit profit = new Profit();
        BizOrder bizOrder = profitContext.getBizOrder();
        this.profitContext = profitContext;
        profit.setBizCode(TkConstants.BIZ_CODE);
        profit.setInAccount(getInaccount());
        profit.setPriority(getPriority());
        profit.setOutAccount(bizOrder.getPayerId());
        profit.setProfitFee(getFrofitAmount());
        profitContext.getProfits().add(profit);

    }


}
