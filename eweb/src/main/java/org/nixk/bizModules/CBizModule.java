package org.nixk.bizModules;

import org.springframework.stereotype.Component;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/5/20
 */
@Component
public class CBizModule implements BizModule {
    @Override
    public Object dealBiz(Object obj) {
        return null;
    }

    @Override
    public String getBizIndentiry() {
        return "C";
    }

    @Override
    public void register() {
        System.out.println( getBizIndentiry() );
    }
}
