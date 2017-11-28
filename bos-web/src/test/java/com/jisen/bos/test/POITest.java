package com.jisen.bos.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

/**
 * 使用POI技术解析Excel文件,
 * hssfworkbook,Excel文件对象
 * @author Administrator
 *
 */
public class POITest {
	//使用POI解析Excel文件
	@Test
	public void test1() throws FileNotFoundException, IOException{
		String filePath = "G:\\BaiduNetdiskDownload\\java\\11-物流BOS系统（ssh统合项目二第58-71天）\\BOS-day05\\BOS-day05\\资料\\区域导入测试数据.xls";
		//包装一个Excel文件对象
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(filePath)));
		//读取文件中一个Sheet标签页
		HSSFSheet hssfSheet = workbook.getSheetAt(0);
		//遍历标签中所有的行
		for(Row row : hssfSheet){
			int num = row.getRowNum();//获得行号
			if(num==0){
				continue;
			}
			System.out.println();
			for(Cell cell : row){
				String value = cell.getStringCellValue();
				System.out.print(value);
			}
		}
	}
}
