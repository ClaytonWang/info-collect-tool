package com.dw3c.infocollecttool.service.impl;

import com.dw3c.infocollecttool.service.IDynamicSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

@Service
public class DynamicSchedulerServiceImpl implements IDynamicSchedulerService, CommandLineRunner {

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    private ScheduledFuture<?> scheduledFuture;

    /**
     * 启动调度
     */
    @Override
    public void startScheduler() {
        if (scheduledFuture == null || scheduledFuture.isCancelled())  {
            scheduledFuture = Objects.requireNonNull(taskScheduler).scheduleAtFixedRate(
                    this::executeTask, // 任务逻辑
                    Duration.ofSeconds(1)  // 执行间隔
            );
            System.out.println(" 调度已启动");
        } else {
            System.out.println(" 调度已在运行中");
        }
    }

    /**
     * 停止调度
     */
    @Override
    public void stopScheduler() {
        if (scheduledFuture != null && !scheduledFuture.isCancelled())  {
            scheduledFuture.cancel(false);  // 取消任务,且不中断正在执行的任务
            System.out.println(" 调度已停止");
        } else {
            System.out.println(" 调度未运行");
        }
    }

    /**
     * 任务逻辑
     */
    private void executeTask() {
        System.out.println(" 任务执行时间: " + new java.util.Date());
        // 任务逻辑 扫描上传的文件，读取数据，处理数据，写入数据库等

    }

    /**
     * 应用启动时自动启动调度
     */
    @Override
    public void run(String... args) throws Exception {
//        startScheduler();
    }
}
