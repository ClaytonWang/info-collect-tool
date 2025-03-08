package com.dw3c.infocollecttool;

import com.dw3c.infocollecttool.entity.UploadFile;
import com.dw3c.infocollecttool.mapper.IFileUploadMapper;
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
    private IFileUploadMapper fileUploadMapper;

    @Test
    void contextLoads() {
        String updateDate = DateUtils.formatDateTime(DateUtils.dateToLocalDateTime(new Date()));
        fileUploadMapper.insert(new UploadFile(null, "test.txt", "test.txt", updateDate,null));
    }

    @Test
    void readExcel() {
        ExcelReaderUtils.readExcel("uploads/20250308143703_154.98171777670078.xlsx");
    }

}
