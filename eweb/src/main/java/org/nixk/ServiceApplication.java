package org.nixk;

import com.alibaba.fastjson.JSON;
import org.kx.util.FileUtil;
import org.kx.util.reflect.MyObjectMaker;
import org.nixk.bizModules.BizModule;
import org.nixk.bizModules.BizModuleInstance;
import org.nixk.filters.BizFilterInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Hello world!
 * nohup /opt/taobao/java/bin/java -jar /home/xianguang.skx/xian_app.jar > nohup.out 2>&1 &
 * sudo -u root kiil -9 89277
 */
@SpringBootApplication
@RestController
 public class ServiceApplication {
    public static void main(String[] args) {
         SpringApplication.run(ServiceApplication.class, args);
     }

     @Resource
    BizFilterInstance bizFilterInstance;

    @Resource
    BizModuleInstance bizModuleInstance;



    @RequestMapping("/hi")
    public String home() {
        return "hi,bb891008";
    }


    @RequestMapping("/listBizModule")
    public String listBizModule() {
        return JSON.toJSONString(bizModuleInstance.getModuleMaps());
    }


    @RequestMapping("/addBizModule")
    public String addBizModule(@RequestParam String source) {
        ///Users/xianguang/IdeaProjects/nick070809/autoEntry/eweb/src/main/resources/NbizModule.txt
        try {
            String  bizSource = FileUtil.readFile(source);
            BizModule newBizModule = (BizModule) new MyObjectMaker().makeObject(bizSource);
            newBizModule.register();
            bizModuleInstance.add(newBizModule);

        } catch (Throwable e) {
            e.printStackTrace();
            return "error";
        }


        return "done";
    }



    @RequestMapping("/addFilter")
    public String addFilter(@RequestParam String code) {
        return "hi,bb891008";
    }







}
