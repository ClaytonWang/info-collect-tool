package com.dw3c.infocollecttool;

import com.dw3c.infocollecttool.entity.UploadFile;
import com.dw3c.infocollecttool.mapper.IFileUploadMapper;
import com.dw3c.infocollecttool.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}
