package org.nixk.txf.common;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Description ：订单
 * Created by  xianguang.skx
 * Since 2020/6/12
 */

@Data
public class BizOrder implements Serializable {

    private  Long bizOrderId;

    private  Long sellerId ;

    private  Long confirmFee ;

    private  Long disburseFee ;

    private  Long orderPrice ;

    private  Integer payStatus ;

    private  String payerId ; //卖家账户

    private  HashMap<String,String> attribute = new HashMap<>();


}
