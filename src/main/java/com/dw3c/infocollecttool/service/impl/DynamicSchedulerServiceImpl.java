package com.dw3c.infocollecttool.service.impl;

import com.dw3c.infocollecttool.entity.ScanLogs;
import com.dw3c.infocollecttool.entity.UploadFile;
import com.dw3c.infocollecttool.service.IDynamicSchedulerService;
import com.dw3c.infocollecttool.service.IFileUploadService;
import com.dw3c.infocollecttool.service.IInfoCollectionService;
import com.dw3c.infocollecttool.service.IScanLogsService;
import com.dw3c.infocollecttool.utils.ExcelReaderUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

@Service
@Slf4j
public class DynamicSchedulerServiceImpl implements IDynamicSchedulerService, CommandLineRunner {

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private IFileUploadService fileUploadService;

    @Autowired
    private IInfoCollectionService infoCollectionService;

    @Autowired
    private IScanLogsService scanLogsService;

    private ScheduledFuture<?> scheduledFuture;

    /**
     * 应用启动时自动启动调度
     */
    @Override
    public void run(String... args) throws Exception {
        // 启动时创建上传目录
        Path uploadsDir = Paths.get("uploads").toAbsolutePath().normalize();
        if (!Files.exists(uploadsDir))  {
            Files.createDirectories(uploadsDir);
        }

//        startScheduler();
    }

    /**
     * 启动调度
     */
    @Override
    public void startScheduler() {
        if (scheduledFuture == null || scheduledFuture.isCancelled())  {
            scheduledFuture = Objects.requireNonNull(taskScheduler).scheduleAtFixedRate(
                    this::executeTask, // 任务逻辑
                    Duration.ofSeconds(10)  // 执行间隔
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
            log.info(" 调度已停止");
        } else {
            log.info(" 调度未运行");
        }
    }

    /**
     * 任务逻辑
     */
    private void executeTask() {
        System.out.println(" 任务执行时间: " + new java.util.Date());
        // 任务逻辑 扫描上传的文件，读取数据，处理数据，写入数据库等

        List<UploadFile> rawFiles = fileUploadService.getRawFiles();
        var scanLog = new ScanLogs();
        for (UploadFile file : rawFiles) {
            String filePath = "uploads/" + file.getFileName();

            try {

                var fileId = file.getId();
                scanLog.setId(fileId);

                // 读取数据，处理数据，写入数据库等
                var infoCollection = ExcelReaderUtils.populateInfoFromExcel(filePath);
                infoCollection.setFileId(fileId);
                infoCollectionService.insert(infoCollection);

                //记录日志信息
                scanLog.setLogMsg("SUCCESS");
                scanLogsService.insert(scanLog);

                // todo：修改上传文件状态

            } catch (Exception e) {
                String logMsg = " 执行executeTask失败: " + filePath+"--->"+e.getMessage();
                log.error(logMsg);
                scanLog.setLogMsg(logMsg);
                scanLogsService.insert(scanLog);
            }
        }
    }
}
