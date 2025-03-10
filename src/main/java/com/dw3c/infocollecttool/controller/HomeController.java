package com.dw3c.infocollecttool.controller;

import com.dw3c.infocollecttool.entity.UploadFile;
import com.dw3c.infocollecttool.service.IFileUploadService;
import com.dw3c.infocollecttool.service.IScanLogsService;
import com.dw3c.infocollecttool.utils.ExcelUtils;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/summary")
    public String info(Model model) {
        List<InfoCollection> list = infoCollectionService.getAll();
        model.addAttribute("contextPath",  request.getServletPath());
        model.addAttribute("list", list);
        return "summary";
    }

    @GetMapping("/upload")
    public String upload(Model model) {
        return "upload";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable Integer fileId) {

        try {
            UploadFile file = fileUploadService.getUploadFileById(fileId);

            Path fileStorageLocation = Paths.get(filePath).toAbsolutePath().normalize();
            // 加载文件
            String fileName = file.getFileName();
            String fileOriginalName = file.getOriginalFileName();
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            // 检查文件是否存在
            if (resource.exists()  || resource.isReadable())  {
                // 设置 HTTP 头
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
                headers.setContentDispositionFormData("attachment",  fileOriginalName);

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

    @GetMapping("/download/all")
    public ResponseEntity<Resource> downloadAll() {
        String fileName = "summary.xlsx";
        List<InfoCollection> list = infoCollectionService.getAll();
        Resource resource = ExcelUtils.saveSummaryToExcel(list);
        // 检查文件是否存在
        if (resource != null && (resource.exists() || resource.isReadable())) {
            // 设置 HTTP 头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", fileName);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        }
        return ResponseEntity.internalServerError().build();
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
