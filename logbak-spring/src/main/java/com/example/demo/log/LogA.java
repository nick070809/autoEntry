package com.example.demo.log;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志特殊类指定存放位置
 */
@Slf4j
public class LogA {
    public static void log(String msg){
        log.debug("debug:"+msg);
        log.info("info"+msg);
        log.warn("warn"+msg);
    }
}
