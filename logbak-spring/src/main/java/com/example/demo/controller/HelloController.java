package com.example.demo.controller;

import com.example.demo.log.LogA;
import com.example.demo.log.LogB;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Slf4j
public class HelloController {

    static Logger tmcLogger = LoggerFactory.getLogger("tmcLogger");



    @RequestMapping("/hi/{name}")
    public String  hi(@PathVariable String name) {
        log.info("info:{}，信息：{},姓名：{}","收到请求","/hi",name);
        log.error("error:{},信息：{}","错误日志","测试错误日志消息");
        log.debug("debug:{},信息：{}","调试日志","调试日志");
        LogA.log("特殊类A的日志，name:"+name);
        LogB.log("特殊类B的日志，文件放入专门的路径，name:"+name);
        tmcLogger.error("ddddd ===" +new Date());
        return "hello:"+name;
    }
}
