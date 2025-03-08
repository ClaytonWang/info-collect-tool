package com.dw3c.infocollecttool;

import com.dw3c.infocollecttool.entity.UploadFile;
import com.dw3c.infocollecttool.mapper.IFileUploadMapper;
import com.dw3c.infocollecttool.service.IFileUploadService;
import com.dw3c.infocollecttool.service.IInfoCollectionService;
import com.dw3c.infocollecttool.service.IScanLogsService;
import com.dw3c.infocollecttool.utils.DateUtils;
import com.dw3c.infocollecttool.utils.ExcelReaderUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.util.Date;

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
        fileUploadService.insert(new UploadFile(null, "test.txt", "test.txt", updateDate,null));
    }

    @Test
    void readExcel() {
        var obg = ExcelReaderUtils.populateInfoFromExcel("uploads/20250308143703_154.98171777670078.xlsx");
        obg.setFileId(1);
        var id= infoCollectionService.insert(obg);
        System.out.println(obg);
    }

    @Test
    void insertScanLog() {

    }

    @Test
    void insertInfoCollection() {

    }

}
