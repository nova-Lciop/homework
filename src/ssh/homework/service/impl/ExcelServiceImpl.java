package ssh.homework.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;


import ssh.homework.domain.Exercise;
import ssh.homework.domain.StudentScore;
import ssh.homework.service.ExcelService;

@Service("excelService")
public class ExcelServiceImpl implements ExcelService {
	@Override
	public void export(List<Exercise> list, ServletOutputStream out) throws Exception {

    try{
                     // 第一步，创建一个workbook，对应一个Excel文件
                     HSSFWorkbook workbook = new HSSFWorkbook();
                     //设置自动换行
                     HSSFCellStyle cellStyle=workbook.createCellStyle();    
                     cellStyle.setWrapText(true);    
                 
                      
                     
                     // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
                     HSSFSheet hssfSheet = workbook.createSheet("sheet1");
                     hssfSheet.setColumnWidth(1,256*100);//设置第一列单元格宽度
                     hssfSheet.setColumnWidth(2,256*100);//设置第二列单元格宽度
                     hssfSheet.autoSizeColumn((short)1);
                     hssfSheet.autoSizeColumn((short)2);
                     
                     // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
                     
                     HSSFRow row = hssfSheet.createRow(0);
                    // 第四步，创建单元格，并设置值表头 设置表头居中
                     HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
                     
                     //居中样式
                     hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         
                     HSSFCell hssfCell = null;
                     String titles[]=new String[] {"习题类型","习题内容","答案","习题所在章"};
                     for (int i = 0; i < 4; i++) {
                         hssfCell = row.createCell(i);//列索引从0开始
                         hssfCell.setCellValue(titles[i]);//列名1
                         hssfCell.setCellStyle(hssfCellStyle);//列居中显示           
                       
                     }
                     
                     // 第五步，写入实体数据 
                 
                     
                         for (int i = 0; i < list.size(); i++) {
                             row = hssfSheet.createRow(i+1);                
                             Exercise exercise = list.get(i);
                             
                             // 第六步，创建单元格，并设置值
    
                             if(exercise.getKind() != null){
                            	 String kind=exercise.getKind();
                            	 if(kind.equals("1"))kind="选择题";
                            	 else if(kind.equals("2"))kind="填空题";
                            	 else kind="简答题";
                            	 row.createCell(0).setCellValue(kind);
                            	 
                             }
                             if(exercise.getContent()!= null){
                            	 hssfCell=row.createCell(1);
                            	 hssfCell.setCellStyle(cellStyle);
                            	 
                            	 hssfCell.setCellValue(exercise.getContent());
                            	 
                             }
                             if(exercise.getAnswer()!= null){
                            	 row.createCell(2).setCellValue(exercise.getAnswer());
                             }
                             if(exercise.getChapter() !=null){
                                 row.createCell(3).setCellValue(exercise.getChapter());
                             }
                             }
 
                     // 第七步，将文件输出到客户端浏览器
                     try {
                         workbook.write(out);
                         out.flush();
                        out.close();
         
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }catch(Exception e){
                     e.printStackTrace();
                    throw new Exception("导出信息失败！");
                    
                    }
                 }

	@Override
	public void export(Map<Integer, StudentScore> map, ServletOutputStream out,String workbookInfo) throws Exception {
		try {
			// 第一步，创建一个workbook，对应一个Excel文件
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 设置自动换行
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setWrapText(true);

			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet

			HSSFSheet hssfSheet = workbook.createSheet(workbookInfo);
			
			hssfSheet.setColumnWidth(0, 256*12);// 设置第一列单元格宽度
			hssfSheet.autoSizeColumn((short)0);//自动调整大小
			hssfSheet.setColumnWidth(1, 256*8);// 设置第二列单元格宽度
			hssfSheet.autoSizeColumn((short)1);
							
			for (int i = 2; i < 30; i++) {
				hssfSheet.setColumnWidth(i, 256*8);// 设置每列单元格宽度
				hssfSheet.autoSizeColumn((short)i);
			}
			// 创建单元格，并设置值表头 设置表头居中
			
			HSSFFont font2 =workbook.createFont();
	        font2.setFontName("宋体");
	        font2.setFontHeightInPoints((short) 10);
			HSSFCellStyle style2 = workbook.createCellStyle();
			style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
			style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
			style2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
			style2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			style2.setFont(font2);
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
			style2.setWrapText(true); // 换行
			style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
			// 居中样式
			//hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			//在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = hssfSheet.createRow(0);
			HSSFCell hssfCell = null;
			hssfCell = row.createCell(0);// 列索引从0开始
			hssfCell.setCellValue(workbookInfo);// 第一行显示作业信息
			
				
			
			  // 第五步，写入实体数据 
            
            Set<Integer> set=map.keySet();
            Iterator<Integer> it=set.iterator();
            int i=0;
            int lastCol=20;
           while(it.hasNext()) {
        	   Integer studentId=it.next();
                row = hssfSheet.createRow(i+2);   //从第三行开始
                HSSFCell hssfCell1=row.createCell(0);//第一列
                hssfCell1.setCellStyle(style2);
                hssfCell1.setCellValue(map.get(studentId).getStudent().getLoginname());//学号　
                hssfCell1=row.createCell(1);//第二列
                hssfCell1.setCellStyle(style2);
                hssfCell1.setCellValue(map.get(studentId).getStudent().getUsername());//学生姓名
                List<Float> scores=map.get(studentId).getScores();//当前学生的所有作业成绩
                lastCol=scores.size();//学生的作业数
                for(int j=0;j<lastCol;j++) {
					HSSFCell hssfCell2 = row.createCell(j + 2);// 从第三列开始
					hssfCell2.setCellStyle(style2);
					hssfCell2.setCellValue(scores.get(j));// 将学生成绩写入单元格
				}
				i++;
			}
			int k;
			row = hssfSheet.createRow(1);//从第二行开始根据lastCol设置列数
			hssfCell = row.createCell(0);// 列索引从0开始
			hssfCell.setCellStyle(style2);
			hssfCell.setCellValue("学号");// 列名1
			hssfCell = row.createCell(1);
			hssfCell.setCellStyle(style2);
			hssfCell.setCellValue("姓名");// 列名2
			for (k = 1; k < lastCol +1; k++) {
				hssfCell = row.createCell(k + 1);// 列索引从0开始
				hssfCell.setCellStyle(style2);// 设置样式
				hssfCell.setCellValue("作业" + k);// 列名1
			}
           hssfCell.setCellValue("平均");
           hssfSheet.addMergedRegion(new CellRangeAddress(0, 0,0,lastCol+2));//合并第一行单元格
           try {
               workbook.write(out);
               out.flush();
              out.close();

           } catch (Exception e) {
               e.printStackTrace();
           }
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("导出信息失败！");

		}

	}

  
}