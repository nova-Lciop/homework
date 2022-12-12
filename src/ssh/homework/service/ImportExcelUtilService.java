package ssh.homework.service;

import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
//处理EXCEL文件的接口
public interface ImportExcelUtilService {
	
	public  List<List<Object>> getBankListByExcel(InputStream in,String fileName) throws Exception;
	public  Object getCellValue(Cell cell);
	public  Workbook getWorkbook(InputStream inStr,String fileName) throws Exception;
}
