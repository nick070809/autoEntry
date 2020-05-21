package org.nixk.bizModules;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/5/20
 */

@Component
public class BizModuleInstance {

    Map<String,BizModule> moduleMaps= new HashMap<>();

    @Resource
    ABizModule aBizModule;


    @Resource
    CBizModule cBizModule;



    @PostConstruct
    void init(){
        moduleMaps.put(aBizModule.getBizIndentiry(),aBizModule);
        moduleMaps.put(cBizModule.getBizIndentiry(),cBizModule);

    }

    public  BizModule getBizModule(String bizIndetity){
        return moduleMaps.get(bizIndetity);
    }

    public void add(BizModule bizModule) {

        moduleMaps.put(bizModule.getBizIndentiry(),bizModule);
    }

    public Map<String, BizModule> getModuleMaps() {
        return moduleMaps;
    }
}
