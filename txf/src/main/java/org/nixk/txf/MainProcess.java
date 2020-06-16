package org.nixk.txf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.kx.util.generate.IdGenerate;
import org.nixk.txf.app.xbu.XbuTradeExt;
import org.nixk.txf.app.tk.TkTradeExt;
import org.nixk.txf.common.BizOrder;
import org.nixk.txf.common.CreateContext;
import org.nixk.txf.common.PayContext;
import org.nixk.txf.common.ProfitContext;
import org.nixk.txf.ext.Ext;
import org.nixk.txf.ext.TradeCreateExt;
import org.nixk.txf.ext.TradePayExt;
import org.nixk.txf.ext.TradeProfitExt;
import org.nixk.txf.hoz.dap.DapTradeExt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/6/12
 */

public class MainProcess {

    List<Ext> appExts = new ArrayList<>();

    private  BizOrder bizOrder ;

    //加载所有的app , 应用为spring-bean的方式 ；
    public  void loadApps(){

        TkTradeExt taoke = new TkTradeExt();
        DapTradeExt dap = new  DapTradeExt();
        XbuTradeExt xbuTradeExt = new XbuTradeExt();
        appExts.add(taoke);
        appExts.add(dap);
        appExts.add(xbuTradeExt);

    }


    //创单
    public  void create() throws IOException {

        BizOrder bizOrder =  new BizOrder();
        this.bizOrder =  bizOrder;
        bizOrder.setBizOrderId(IdGenerate.getId());
        bizOrder.setSellerId(2830500L);
        bizOrder.setOrderPrice(20000L);
        bizOrder.setPayStatus(18);
        bizOrder.setPayerId("38549758930");

        System.out.println("create before" +JSON.toJSONString(bizOrder));

        CreateContext createContext = CreateContext.of(bizOrder);

        List<Ext> createExts = appExts.stream().filter(e -> e instanceof TradeCreateExt).collect(Collectors.toList());

        //扩展点的优先级， 暂不考虑

        for(Ext ext : createExts){
            TradeCreateExt tradeCreateExt = (TradeCreateExt) ext;
            tradeCreateExt.preCreate(createContext);
        }
        //数据落库
        //发消息
        bizOrder.setPayStatus(16);
         //
        for(Ext ext : createExts){
            TradeCreateExt tradeCreateExt = (TradeCreateExt) ext;
            tradeCreateExt.afterCreate(createContext);
        }

        System.out.println("create after" +JSON.toJSONString(bizOrder));

    }


    //支付
    public  void pay() throws IOException {

        PayContext payContext = PayContext.of(this.bizOrder);

        List<Ext> payExts = appExts.stream().filter(e -> e instanceof TradePayExt).collect(Collectors.toList());

        //扩展点的优先级， 暂不考虑

        payExts.stream().forEach(ext -> {
            TradePayExt tradePayExt = (TradePayExt) ext;
            tradePayExt.prePay(payContext);
        });

        //数据落库

        //发消息
        payExts.stream().forEach(ext -> {
            TradePayExt tradePayExt = (TradePayExt) ext;
            tradePayExt.afterPay(payContext);
        });
        System.out.println("pay after" +JSON.toJSONString(bizOrder));
    }



    //发货
    public  void ship() throws IOException {
        System.out.println("ship");
    }


    //打款
    public  void disburse() throws IOException {
        bizOrder.setDisburseFee(bizOrder.getOrderPrice()-2);
        System.out.println("disburse");
    }


    //分润 TradeProfitExt
    public  void share() throws IOException {


        ProfitContext payContext = ProfitContext.of(this.bizOrder);

        List<Ext> payExts = appExts.stream().filter(e -> e instanceof TradeProfitExt).collect(Collectors.toList());

        //扩展点的优先级， 暂不考虑
        payExts.stream().forEach(ext -> {
            TradeProfitExt tradeProfitExt = (TradeProfitExt) ext;
            tradeProfitExt.craeteProfit(payContext);
        });
        //数据落库
        bizOrder.setConfirmFee(bizOrder.getOrderPrice()-2);
        //发消息

        String pretty = JSON.toJSONString(payContext, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);

        System.out.println("share after " +pretty);


    }




    //扣拥
    public  void commsion() throws IOException {


    }





    public static void main(String[] args) throws IOException {
        MainProcess mainProcess = new  MainProcess();
        mainProcess.loadApps();
        mainProcess.create();
        mainProcess.pay();
        mainProcess.ship();
        mainProcess.disburse();
        mainProcess.share();
    }

}
