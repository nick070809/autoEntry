package org.nixk.txf.common;

import lombok.Data;

/**
 * Description ： 分账列表
 * Created by  xianguang.skx
 * Since 2020/6/12
 */

@Data
public class Profit {

    private Long profitId;

    private Long profitFee;

    private String inAccount;

    private String outAccount;

    private Integer priority;

    private String bizCode;


}
