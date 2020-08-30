package com.selenium.library;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.log4testng.Logger;

public class ReadData {
	Logger logger = Logger.getLogger(ReadData.class);
	
	Workbook wb;
	Sheet sheet;
	
	public ReadData(String excelPath) {
	try {
		File src = new File(excelPath);
		FileInputStream fis =new FileInputStream(src);
		wb = getWorkbook(fis,excelPath);   //it can read both "xls" "xlsx"
		
	} catch (Exception e) {
	
		System.out.println(e.getMessage());
	}
	
	}
    public String getData(int sheetnumber,int row,int column)
    {
    	sheet = wb.getSheetAt(sheetnumber);
    	String data = sheet.getRow(row).getCell(column).getStringCellValue();
    	return data;
    }
    public int getRowCount(int SheetIndex) {
    	int row = wb.getSheetAt(SheetIndex).getLastRowNum();
    	row=row+1;
    	return row;
    }
	private Workbook getWorkbook(FileInputStream fis, String excelFilePath) {
		Workbook workbook = null;
		try {
			if (excelFilePath.endsWith("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else if (excelFilePath.endsWith("xls")) {
				workbook = new HSSFWorkbook(fis);
			} else {
				throw new IllegalArgumentException("The specified file is not Excel file");
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
		return workbook;
	}
}
