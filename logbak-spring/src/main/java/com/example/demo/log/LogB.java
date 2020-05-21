package com.example.demo.log;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogB {
    public static void log(String msg){
        log.debug("debug:"+msg);
        log.info("info"+msg);
        log.warn("warn"+msg);
    }
}
