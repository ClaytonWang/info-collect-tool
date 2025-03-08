package com.dw3c.infocollecttool.utils;

import com.dw3c.infocollecttool.entity.InfoCollection;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ExcelReaderUtils {
    // 将需要检查的字符串提取到一个集合中
    private static final Set<String> CHECK_STRINGS = new HashSet<>(Arrays.asList(
            "EM Name",
            "Approving Date",
            "EM Team",
            "Approving Conclusion",
            "EM Email",
            "Additional Comments (if any)"
    ));

    private static final String COMMENTS = "Comments (If any)";

    private static void setInfoValues(InfoCollection info,String name,String value) {
        switch (name) {
            case "EM Name":
            case "DE Name":
            case "Central Name":
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

                if(commentsCellNum!=-1){
                    Cell cell3 = row.getCell(commentsCellNum);
                    if (cell3 != null) {
                        String comments = readCellValue(cell3);
                        setInfoValues(infoCollection, COMMENTS, comments);
                        commentsCellNum = -1;
                        System.out.println("Comments (If any):" + comments);
                    } else {
                        setInfoValues(infoCollection, COMMENTS, "");
                        System.out.println("Comments (If any): No corresponding value");
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
                                setInfoValues(infoCollection, strName, strValue);
                                System.out.println(strValue + ":" + readCellValue(cell2));
                            } else {
                                setInfoValues(infoCollection, strName, "");
                                System.out.println(strName + ": No corresponding value");
                            }
                        }else if(strName.equalsIgnoreCase(COMMENTS)){
                            commentsCellNum=i;
                        }
                    }
                }
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return infoCollection;
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
