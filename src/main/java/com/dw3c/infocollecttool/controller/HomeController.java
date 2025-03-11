package com.dw3c.infocollecttool.controller;

import com.dw3c.infocollecttool.entity.UploadFile;
import com.dw3c.infocollecttool.service.IFileUploadService;
import com.dw3c.infocollecttool.service.IScanLogsService;
import com.dw3c.infocollecttool.utils.ExcelUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.FileSystemResource;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

    @GetMapping("/download/summary")
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

    @GetMapping("/download/all")
    public ResponseEntity<Resource> downloadAllFiles() throws IOException {
        String zipFileName = generateZipFileName();
        String ZIP_FILE_PATH = "zip_folder/" + zipFileName;
        String FOLDER_PATH = "uploads/";
        // 获取所有文件
        List<Path> filePaths = getAllFilesInFolder(FOLDER_PATH);

        // 打包为 ZIP
        zipFiles(filePaths, ZIP_FILE_PATH);
        // 创建 Resource 对象
        File zipFile = new File(ZIP_FILE_PATH);
        Resource resource = new FileSystemResource(zipFile);

        // 设置 HTTP 响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,  "attachment; filename=" + zipFile.getName());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        // 返回响应
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(zipFile.length())
                .body(resource);
    }

    private String generateZipFileName() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        return "files_" + LocalDateTime.now().format(formatter)  + ".zip";
    }

    private List<Path> getAllFilesInFolder(String folderPath) throws IOException {
        return Files.walk(Paths.get(folderPath))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
    }

    private void zipFiles(List<Path> filePaths, String zipFilePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {
            for (Path filePath : filePaths) {
                ZipEntry zipEntry = new ZipEntry(filePath.getFileName().toString());
                zipOut.putNextEntry(zipEntry);
                Files.copy(filePath,  zipOut);
                zipOut.closeEntry();
            }
        }
    }
}
