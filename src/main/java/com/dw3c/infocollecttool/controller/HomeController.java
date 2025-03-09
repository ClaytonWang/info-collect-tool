package com.dw3c.infocollecttool.controller;

import com.dw3c.infocollecttool.service.IFileUploadService;
import com.dw3c.infocollecttool.service.IScanLogsService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import com.dw3c.infocollecttool.entity.InfoCollection;
import com.dw3c.infocollecttool.service.IInfoCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class HomeController {

    @Value("${app.upload.dir:uploads/}")
    private String filePath;

    @Autowired
    private IInfoCollectionService infoCollectionService;

    @Autowired
    private IFileUploadService fileUploadService;

    @Autowired
    private IScanLogsService scanLogsService;;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/summary")
    public String info(Model model) {
        List<InfoCollection> list = infoCollectionService.getAll();
        model.addAttribute("list", list);
        return "summary";
    }

    @GetMapping("/upload")
    public String upload(Model model) {
        return "upload";
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> download(@PathVariable String fileName) {
        Path fileStorageLocation = Paths.get(filePath).toAbsolutePath().normalize();
        try {
            // 加载文件
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            // 检查文件是否存在
            if (resource.exists()  || resource.isReadable())  {
                // 设置 HTTP 头
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
                headers.setContentDispositionFormData("attachment",  fileName);

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/delete/{id}/{fileId}")
    public String delete(@PathVariable Integer id,@PathVariable Integer fileId) {
        try {
            infoCollectionService.delete(id);
            fileUploadService.deleteUploadFile(fileId);
            scanLogsService.delete(fileId);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "index";
    }
}
