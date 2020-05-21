package org.nixk.filters;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/5/20
 */

@Component
public class BizFilterInstance {

    List<BizFilter> list = new ArrayList<>();

    @Resource
    Afilter afilter;


    @Resource
    Bfilter bfilter;



    @PostConstruct
    void init(){
        list.add(afilter);
        list.add(bfilter);
    }

    public List<BizFilter> getList() {
        return list;
    }

    public void put(BizFilter bizFilter) {
        list.add(bizFilter);
    }
}
