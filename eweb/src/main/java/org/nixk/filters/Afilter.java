package org.nixk.filters;

import org.springframework.stereotype.Component;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/5/20
 */

@Component
public class Afilter implements BizFilter{
    @Override
    public boolean isIn(Object obj) {
        return false;
    }
}
