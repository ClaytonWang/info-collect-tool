package com.dw3c.infocollecttool;

import com.dw3c.infocollecttool.entity.InfoCollection;
import com.dw3c.infocollecttool.entity.ScanLogs;
import com.dw3c.infocollecttool.entity.UploadFile;
import com.dw3c.infocollecttool.service.IFileUploadService;
import com.dw3c.infocollecttool.service.IInfoCollectionService;
import com.dw3c.infocollecttool.service.IScanLogsService;
import com.dw3c.infocollecttool.utils.DateUtils;
import com.dw3c.infocollecttool.utils.ExcelUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.util.Date;
import java.util.List;

@SpringBootTest
class InfoCollectToolApplicationTests {

    @Autowired
    private IFileUploadService fileUploadService;

    @Autowired
    private IScanLogsService scanLogsService;

    @Autowired
    private IInfoCollectionService infoCollectionService;

    @Test
    void contextLoads() {
        String updateDate = DateUtils.formatDateTime(DateUtils.dateToLocalDateTime(new Date()));
//        fileUploadService.insert(new UploadFile(null, "test.txt", "test.txt", updateDate,null));
    }

    @Test
    void readExcel() {
        List<UploadFile> rawFiles = fileUploadService.getRawFiles();
        for (UploadFile file : rawFiles) {
            String filePath = "uploads/" + file.getFileName();
            try {
                var obg = ExcelUtils.populateInfoFromExcel(filePath);
                obg.setFileId(file.getId());
                var id= infoCollectionService.insert(obg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void insertScanLog() {
        var log = new ScanLogs();
        log.setFileId(1);
        var id = scanLogsService.insert(log);
        System.out.println(id);
    }

    @Test
    void insertInfoCollection() {

    }

    @Test
    void exportSummaryExcelTest() {
        List<InfoCollection> list = infoCollectionService.getAll();
        Resource resource = ExcelUtils.saveSummaryToExcel(list);
    }
}
