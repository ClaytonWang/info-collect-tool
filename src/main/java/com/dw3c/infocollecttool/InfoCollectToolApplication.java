package com.dw3c.infocollecttool;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dw3c.infocollecttool.mapper")
public class InfoCollectToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfoCollectToolApplication.class, args);
    }

}
