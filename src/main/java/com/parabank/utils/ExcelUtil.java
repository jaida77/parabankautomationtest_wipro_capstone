package com.parabank.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;

public class ExcelUtil {
    public static Object[][] readSheet(String path, String sheetName) {
        try (InputStream in = new FileInputStream(path)) {
            Workbook wb = new XSSFWorkbook(in);
            Sheet sheet = wb.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in file: " + path);
            }

            int rows = sheet.getPhysicalNumberOfRows();
            int cols = sheet.getRow(0).getLastCellNum();
            Object[][] data = new Object[rows - 1][cols];

            for (int i = 1; i < rows; i++) {
                Row r = sheet.getRow(i);
                for (int j = 0; j < cols; j++) {
                    Cell c = r.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    c.setCellType(CellType.STRING);
                    data[i - 1][j] = c.getStringCellValue();
                }
            }
            wb.close();
            return data;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
