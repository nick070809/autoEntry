package org.nixk.txf.app.xbu;

import org.nixk.txf.common.BizOrder;
import org.nixk.txf.common.PayContext;
import org.nixk.txf.common.Profit;
import org.nixk.txf.common.ProfitContext;
import org.nixk.txf.ext.TradePayExt;
import org.nixk.txf.ext.TradeProfitExt;
import org.nixk.txf.extension.PddExt;

import java.math.BigDecimal;


/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/6/12
 */

@PddExt(codes = {XbuConstants.BIZ_CODE})
public class XbuTradeExt implements TradePayExt, TradeProfitExt {

    private ProfitContext profitContext;

    @Override
    public void prePay(PayContext payContext) {
        //订单打标 可变
        payContext.getBizOrder().getAttribute().put("996.sellerId", "99302_30");
    }

    @Override
    public void afterPay(PayContext payContext) {
        //订单打标 不可变

    }

    @Override
    public String getInaccount() {
        //获取996的卖家
        String sellerId = this.profitContext.getBizOrder().getAttribute().get("996.sellerId");

        return sellerId.split("_")[0];


    }

    @Override
    public Long getFrofitAmount() {
        String sellerId = this.profitContext.getBizOrder().getAttribute().get("996.sellerId");
        BigDecimal settlFee = new  BigDecimal( this.profitContext.getBizOrder().getDisburseFee()).multiply(new BigDecimal(sellerId.split("_")[1]));
        return settlFee.longValue();
    }

    @Override
    public Integer getPriority() {
        // 不可变
        return XbuConstants.PROFIT_PRIORITY;
    }

    @Override
    public Integer getTimeOutDays() {
        // 不可变
        return 7;
    }


    public void craeteProfit(ProfitContext profitContext) {

        Profit profit = new Profit();
        BizOrder bizOrder = profitContext.getBizOrder();
        this.profitContext = profitContext;
        profit.setBizCode(XbuConstants.BIZ_CODE);
        profit.setInAccount(getInaccount());
        profit.setPriority(getPriority());
        profit.setOutAccount(bizOrder.getPayerId());
        profit.setProfitFee(getFrofitAmount());
        profitContext.getProfits().add(profit);

    }


}
