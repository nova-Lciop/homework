package ssh.homework.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ssh.homework.domain.StudentWorkbook;
import ssh.homework.service.SimilarityService;
import ssh.homework.service.StudentWorkbookService;


@Service("similarityService")
public class SimilarityImpl implements SimilarityService {
	@Autowired
	@Qualifier(value="studentWorkbookService")
	StudentWorkbookService studentWorkbookService;

	public double doSimilarity(String s1, String s2) {
		
		//length
		int Length1=s1.length();
		int Length2=s2.length();
		
		int Distance=0;
		if (Length1==0) {
			Distance=Length2;
		}
		if(Length2==0)
		{
			Distance=Length1;
		}
		if(Length1!=0&&Length2!=0){
			int[][] Distance_Matrix=new int[Length1+1][Length2+1];
			//编号
			int Bianhao=0;
			for (int i = 0; i <= Length1; i++) {
					Distance_Matrix[i][0]=Bianhao;
					Bianhao++;
			}
			Bianhao=0;
			for (int i = 0; i <=Length2; i++) {
				Distance_Matrix[0][i]=Bianhao;
				Bianhao++;
			}
			
			
			char[] Str_1_CharArray=s1.toCharArray();
			char[] Str_2_CharArray=s2.toCharArray();
			
			
			for (int i = 1; i <= Length1; i++) {
				for(int j=1;j<=Length2;j++){
					if(Str_1_CharArray[i-1]==Str_2_CharArray[j-1]){
						Distance=0;
					}	
					else{
						Distance=1;
					}
						
						int Temp1=Distance_Matrix[i-1][j]+1;
						int Temp2=Distance_Matrix[i][j-1]+1;
						int Temp3=Distance_Matrix[i-1][j-1]+Distance;
						
						Distance_Matrix[i][j]=Temp1>Temp2?Temp2:Temp1;
						Distance_Matrix[i][j]=Distance_Matrix[i][j]>Temp3?Temp3:Distance_Matrix[i][j];
					
				}
				
			}
			
			Distance=Distance_Matrix[Length1][Length2];
		}
		
		double Aerfa=1-1.0*Distance/(Length1>Length2?Length1:Length2);
		//System.out.println(Aerfa);
		return Aerfa;
	}
	//根据学生作业对作业列表中的学生进行查重，查重的标准是如果查重率>=90%，将进重结果和相关学生ID添加到studentWorkbook表
	//的rate和instructions字段中
	public void studentWorkbookSimilarity(List<StudentWorkbook> studentWorkbooks,float checkRate) {

		StudentWorkbook studentWorkbook1;
		StudentWorkbook studentWorkbook2;
	
		Iterator<StudentWorkbook> it=studentWorkbooks.iterator();
		while(it.hasNext()) {
			StudentWorkbook studentWorkbook=it.next();
			if(!studentWorkbook.getExercise().getKind().equals("3")) {//判断是否是简答题
				it.remove();//如果不是简答题则从数组中去掉
				continue;
			}
			studentWorkbook.setStudentRate("");//清空student_workbook表中的studentRate字段的值
			studentWorkbook.setInstructions("");//清空student_workbook表中的instructions字段的值
			studentWorkbook.setRate(0);
			studentWorkbookService.modifyStudentWorkbook(studentWorkbook);
		}
		int n=studentWorkbooks.size();
		for(int i=0;n>1&&i<n;i++) {
			
			studentWorkbook1=studentWorkbooks.get(i);//第i个学生
			String s1=studentWorkbook1.getStudentAnswer();//第i个学生的作业的答案
			String name1=studentWorkbook1.getStudent().getUsername();//第i个学生的姓名
			String studentRate1=studentWorkbook1.getStudentRate();//第i个学生的对应其他学生的查重率
			String instructions1=studentWorkbook1.getInstructions();//第i个学生的查重率对应的学生的id
			double rate1=0;//与第i个学生比较过程中查重率最大的值
			for(int j=i+1;j<n;j++)
			{//第i个学生与i后面的学生的作业答案进行对比
				studentWorkbook2=studentWorkbooks.get(j);//得到第j个学生作业信息				
				String s2=studentWorkbook2.getStudentAnswer();//第j个学生的作业的答案
				String name2=studentWorkbook2.getStudent().getUsername();//得到第j个学生姓名
				String studentRate2=studentWorkbook2.getStudentRate();//第j个学生的查重率
				String instructions2=studentWorkbook2.getInstructions();//第j个学生的查重率对应的学生的id
				if(name1.equals(name2))continue;//如果是同一学生的作业不需比较
				double rate=0;
				String srate="";
				//判断s1或s2是否为空
				if(s1!=null&&!s1.equals("")&&s2!=null&&!s2.equals("")) {
				rate=doSimilarity(s1, s2);//对两个学生进行查重得到查重率				
				srate=String.valueOf((int)(rate*100));//将查重率转换为字符串
				}
				if(rate>=checkRate) 
				{//如果两个学生的答案相似度>=0.85%
					if(rate>rate1)rate1=rate;//存放查重率最大的值
					if(studentRate1==null||studentRate1.equals("")){//第i个学生第一次添加	
						studentRate1=srate;
						instructions1=name2;
						}
					else {//表示已经添加了一次
						studentRate1=studentRate1+","+srate;
						instructions1=instructions1+","+name2;
					}
					studentWorkbook1.setStudentRate(studentRate1);
					studentWorkbook1.setInstructions(instructions1);
					studentWorkbook1.setRate((int)(rate1*100));
					
					if(studentRate2==null||studentRate2.equals("")){//第j个学生第一次添加	
						studentRate2=srate;
						instructions2=name1;
						}
					else {//表示已经添加了一次
						studentRate2=studentRate2+","+srate;
						instructions2=instructions2+","+name1;
					}
					studentWorkbook2.setStudentRate(studentRate2);
					studentWorkbook2.setInstructions(instructions2);
					studentWorkbook2.setRate((int)(rate1*100));

					studentWorkbookService.modifyStudentWorkbook(studentWorkbook1);//更新student_workbook表
					studentWorkbookService.modifyStudentWorkbook(studentWorkbook2);//更新student_workbook表					
				}
				
			}
		}
		
	}

}
