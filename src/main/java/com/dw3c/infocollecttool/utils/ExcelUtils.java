package com.dw3c.infocollecttool.utils;

import com.dw3c.infocollecttool.entity.InfoCollection;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class ExcelUtils {
    // 将需要检查的字符串提取到一个集合中
    private static final Set<String> CHECK_STRINGS = new HashSet<>(Arrays.asList(
            "EM Name",
            "DE Name",
            "Central Name",
            "Approving Date",
            "EM Team",
            "DE Team",
            "Central Team",
            "Approving Conclusion",
            "EM Email",
            "DE Email",
            "Approver Email",
            "Additional Comments (if any)"
    ));

    private static final String COMMENTS = "Comments (If any)";

    public static InfoCollection populateInfoFromExcel(String filePath) {
        int commentsCellNum = -1;
        var infoCollection = new InfoCollection();
        try (FileInputStream file = new FileInputStream(filePath)) {
            // 创建工作簿对象
            Workbook workbook = new XSSFWorkbook(file);
            // 获取第一个工作表
            Sheet sheet = workbook.getSheetAt(0);
            // 遍历每一行
            for (Row row : sheet) {

                if (commentsCellNum != -1) {
                    Cell cell3 = row.getCell(commentsCellNum);
                    if (cell3 != null) {
                        String comments = readCellValue(cell3);
                        setInfoValues(infoCollection, COMMENTS, comments);
                        commentsCellNum = -1;
                        log.info("Comments (If any):{}", comments);
                    } else {
                        setInfoValues(infoCollection, COMMENTS, "");
                        log.info("Comments (If any): No corresponding value");
                    }
                }

                // 遍历每一列
                for (var i = 1; i <= row.getLastCellNum(); i++) {
                    Cell cell1 = row.getCell(i); // 获取单元格
                    if (cell1 != null) {
                        String strName = readCellValue(cell1);

                        if (CHECK_STRINGS.contains(strName)) {
                            Cell cell2 = row.getCell(i + 1);
                            if (cell2 != null) {
                                String strValue = readCellValue(cell2);
                                if (strValue != null &&
                                        (strValue.trim().equalsIgnoreCase("(pls fill in your comments here if any)")
                                                || strValue.trim().equalsIgnoreCase("(please fill in your name here)")
                                                || strValue.trim().equalsIgnoreCase("((please fill in your CG email here))")
                                        )) {
                                    strValue = "";
                                }
                                setInfoValues(infoCollection, strName, strValue);
                                System.out.println(strValue + ":" + readCellValue(cell2));
                            } else {
                                setInfoValues(infoCollection, strName, "");
                                System.out.println(strName + ": No corresponding value");
                            }
                        } else if (strName.equalsIgnoreCase(COMMENTS)) {
                            commentsCellNum = i;
                        }
                    }
                }
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("populateInfoFromExcel failed: {}",e.getMessage());
            return null;
        }

        return infoCollection;
    }

    public static Resource saveSummaryToExcel(List<InfoCollection> infoCollectionList) {
        // 创建工作簿对象
        FileOutputStream outputStream = null;
        List<String> headers = Arrays.asList("Type", "Name", "Team", "Email", "Approving Date", "Approving Conclusion", "Additional Comments (if any)", COMMENTS, "Update Time");
        try (Workbook workbook = new XSSFWorkbook()) {
            // 创建工作表
            Sheet sheet = workbook.createSheet("Summary");

            // 创建加粗字体样式
            CellStyle boldCellStyle = workbook.createCellStyle();
            Font boldFont = workbook.createFont();
            boldFont.setBold(true);  // 设置字体加粗
            boldCellStyle.setFont(boldFont);

            // 设置表头
            Row row = sheet.createRow(0);
            int cellIndex =0;
            for(String title : headers){
                Cell cell = row.createCell(cellIndex);
                cell.setCellValue(title);
                cell.setCellStyle(boldCellStyle);
                cellIndex++;
            }

            // 写入数据
            int rowIndex = 1;
            for (InfoCollection info : infoCollectionList) {
                row = sheet.createRow(rowIndex);
                row.createCell(0).setCellValue(info.getInfoType());
                row.createCell(1).setCellValue(info.getApproverName());
                row.createCell(2).setCellValue(info.getTeam());
                row.createCell(3).setCellValue(info.getEmail());
                row.createCell(4).setCellValue(info.getApprovingDate());
                row.createCell(5).setCellValue(info.getApprovingConclusion());
                row.createCell(6).setCellValue(info.getAdditionalComments());
                row.createCell(7).setCellValue(info.getComments());
                row.createCell(8).setCellValue(info.getUpdatedAt());
                rowIndex++;
            }
            // 保存文件
            String fileName = "uploads/Summary_" + System.currentTimeMillis() + ".xlsx";
            File file = new File(fileName);
            outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
            // 读取文件
            return new UrlResource(file.toURI().toURL());
        } catch (IOException e) {
            e.printStackTrace();
            log.info("Failed to save summary to Excel:{}" , e.getMessage() );
            return null;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void setInfoValues(InfoCollection info, String name, String value) {
        switch (name) {
            case "EM Name":
                info.setInfoType("EM");
                info.setApproverName(value);
                break;

            case "DE Name":
                info.setInfoType("DE");
                info.setApproverName(value);
                break;

            case "Central Name":
                info.setInfoType("Central");
                info.setApproverName(value);
                break;

            case "EM Team":
            case "DE Team":
            case "Central Team":
                info.setTeam(value);
                break;

            case "EM Email":
            case "DE Email":
            case "Approver Email":
                info.setEmail(value);
                break;

            case "Approving Date":
                info.setApprovingDate(value);
                break;

            case "Approving Conclusion":
                info.setApprovingConclusion(value);
                break;

            case "Additional Comments (if any)":
                info.setAdditionalComments(value);
                break;

            case COMMENTS:
                info.setComments(value);
                break;
            default:
                break;
        }
    }

    private static String readCellValue(Cell cell) {
        try {
            switch (cell.getCellType()) {
                case STRING:
                case BLANK:
                    return cell.getStringCellValue();
                case NUMERIC:
                    return DateUtils.formatDateTime(DateUtils.dateToLocalDateTime(cell.getDateCellValue()), "yyyy/MM/dd");
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                default:
                    return "UNKNOWN";
            }
        } catch (Exception e) {
            System.err.println("Error reading cell value: " + e.getMessage());
            return "ERROR\t";
        }
    }
}
