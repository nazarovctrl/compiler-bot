package uz.katkit.util;


import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import uz.katkit.dto.RequestShortInfoDTO;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelUtil {
    public static File createExcel(List<RequestShortInfoDTO> requestShortDTOList, Long userId) {
        String fileName = "E:\\Java\\SelfStudy\\CompilerOnlineBot\\data\\" + userId + ".xlsx";
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {

            XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
            XSSFSheet sheet = xssfWorkbook.createSheet();

            XSSFCellStyle cellStyle = xssfWorkbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setFillPattern(FillPatternType.LEAST_DOTS);

            cellStyle.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());
            XSSFRow row = sheet.createRow(0);

            XSSFCell cell = row.createCell(0);
            cell.setCellValue("№");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(1);
            cell.setCellValue("Code");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(2);
            cell.setCellValue("Result");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3);
            cell.setCellValue("Language");
            cell.setCellStyle(cellStyle);


            cell = row.createCell(4);
            cell.setCellValue("Version");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(5);
            cell.setCellValue("Created date");
            cell.setCellStyle(cellStyle);


            sheet.setColumnWidth(0, 1400);
            sheet.setColumnWidth(1, 8000);
            sheet.setColumnWidth(2, 7000);
            sheet.setColumnWidth(3, 7000);
            sheet.setColumnWidth(4, 2800);
            sheet.setColumnWidth(5, 3000);


            cellStyle = xssfWorkbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);

            for (int i = 0; i < requestShortDTOList.size(); i++) {
                RequestShortInfoDTO requestShortInfoDTO = requestShortDTOList.get(i);
                row = sheet.createRow(i + 1);

                cell = row.createCell(0);
                cell.setCellValue(i + 1);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(1);
                cell.setCellValue(requestShortInfoDTO.getCode());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellValue(requestShortInfoDTO.getResult());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(3);
                cell.setCellValue(requestShortInfoDTO.getLangName());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4);
                cell.setCellValue(requestShortInfoDTO.getVersionName());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(5);
                cell.setCellValue(requestShortInfoDTO.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
                cell.setCellStyle(cellStyle);

            }
            xssfWorkbook.write(outputStream);
            xssfWorkbook.close();
            outputStream.close();


            return new File(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
