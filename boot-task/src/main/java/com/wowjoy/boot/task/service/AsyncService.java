package com.wowjoy.boot.task.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class AsyncService {

    /**
     * 声明该方法为异步的
     */
    @Async
    public void outputInfo() {
        try {
            Thread.sleep(3000L);

        } catch (InterruptedException e) {
            log.info(e.getMessage());
        }
        log.info("数据处理中....");
    }

    /**
     * 枚举
     * second minute hour day month day of week
     * 0 * * * * MON-FRI
     */
    @Scheduled(cron = "0,1,2,3,4 * * * * MON-FRI")
    public void taskInfo() {
        log.info("," + new Date());
    }

    /**
     * 区间
     */
    @Scheduled(cron = "5-6 * * * * MON-FRI")
    public void taskInfoDuration() {
        log.info("-" + new Date());
    }

    /**
     * 步长：从第五秒开始，每6秒执行一次
     */
    @Scheduled(cron = "5/6 * * * * MON-FRI")
    public void taskInfoSize() {
        log.info("*" + new Date());
    }
}
