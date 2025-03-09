package com.dw3c.infocollecttool;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
@MapperScan("com.dw3c.infocollecttool.mapper")
public class InfoCollectToolApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(InfoCollectToolApplication.class);
        app.addInitializers(new  StartupInitializer());
        app.run(args);
    }

}
@Slf4j
class StartupInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String uploadPath="uploads/";

    private static final String dbPath="db/";

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        // 启动时创建上传目录
        Path uploadsDir = Paths.get(uploadPath).toAbsolutePath().normalize();
        if (!Files.exists(uploadsDir))  {
            try {
                Files.createDirectories(uploadsDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // 启动时创建数据库目录
        Path dbDir = Paths.get(dbPath).toAbsolutePath().normalize();
        if (!Files.exists(dbDir))  {
            try {
                Files.createDirectories(dbDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}