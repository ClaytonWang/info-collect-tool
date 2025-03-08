package com.dw3c.infocollecttool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulerConfig {

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);  // 设置线程池大小
        scheduler.setThreadNamePrefix("ScheduledTask-");  // 设置线程名前缀
        scheduler.initialize();  // 初始化
        return scheduler;
    }
}