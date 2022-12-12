package ssh.homework.dao.provider;

import static ssh.homework.common.HomeworkConstants.STUDENTWORKBOOKTABLE;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import ssh.homework.domain.StudentWorkbook;

public class StudentWorkbookDynaSqlProvider {
	// 分页动态查询
	public String selectWhitParam(Map<String, Object> params){
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(STUDENTWORKBOOKTABLE);
				if(params.get("studentWorkbook") != null){
					StudentWorkbook studentWorkbook = (StudentWorkbook)params.get("studentWorkbook");
					if(studentWorkbook.getStudent()!= null&&studentWorkbook.getStudent().getId()!=null) {
						WHERE("  student_id=#{studentWorkbook.student.id}"); //根据学生id查找
					}
					if(studentWorkbook.getWorkbook()!= null&&studentWorkbook.getWorkbook().getId()!=null) 
					{
						
						WHERE("  workbook_id=#{studentWorkbook.workbook.id}");
						
							
					}
					if(studentWorkbook.getExercise()!= null&&studentWorkbook.getExercise().getId()!=null) 
					{
						
						WHERE("  exercise_id=#{studentWorkbook.exercise.id} ");
						
							
					}
				}
			}
		}.toString();
		
		if(params.get("pageModel") != null){
			sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize}  ";
		}
		
		return sql;
	}	
	// 动态查询总数量
	public String count(Map<String, Object> params){
		return new SQL(){
			{
				SELECT("count(*)");
				FROM(STUDENTWORKBOOKTABLE);
				if(params.get("studentWorkbook") != null){
					StudentWorkbook studentWorkbook = (StudentWorkbook)params.get("studentWorkbook");
					if(studentWorkbook.getStudent()!= null) {
						WHERE("  student_id=#{studentWorkbook.student.id}"); //根据学生id查找
					}
					if(studentWorkbook.getWorkbook()!= null) 
					{
						
						WHERE("  workbook_id=#{studentWorkbook.workbook.id}");
								}
					if(studentWorkbook.getExercise()!= null&&studentWorkbook.getExercise().getId()!=null) 
					{
						
						WHERE("  exercise_id=#{studentWorkbook.exercise.id}");
						
							
					}
				}
			}
		}.toString();
	}	
	
	// 动态插入
	public String insert(StudentWorkbook studentWorkbook){
		return new SQL(){
			{
				INSERT_INTO(STUDENTWORKBOOKTABLE);
				
				if(studentWorkbook.getExercise()!= null){
					VALUES("exercise_id", "#{exercise.id}");
				}
				if(studentWorkbook.getWorkbook()!= null){
					VALUES("workbook_id", "#{workbook.id}");
				}
				if(studentWorkbook.getStudent()!= null){
					VALUES("student_id", "#{student.id}");
				}
				if(studentWorkbook.getStudentAnswer()!= null){
					VALUES("studentAnswer", "#{studentAnswer}");
				}
				if(studentWorkbook.getGrade()>=0){
					VALUES("grade", "#{grade}");
				}
				if(studentWorkbook.getScore()>=0){
					VALUES("score", "#{score}");
				}
				if(studentWorkbook.getNotes()!=null){
					VALUES("notes", "#{nosts}");
				}
				if(studentWorkbook.getRate()>=0){
					VALUES("rate", "#{rate}");
				}
				if(studentWorkbook.getStudentRate()!=null){
					VALUES("studentRate", "#{studentRate}");
				}
				if(studentWorkbook.getInstructions()!=null){
					VALUES("instructions", "#{instructions}");
				}
				if(studentWorkbook.getFileName()!=null) {
					VALUES("fileName","fileName");
				}
			}
		}.toString();
	}
	// 动态更新
		public String update(StudentWorkbook studentWorkbook){
						
			return new SQL(){
				{
					UPDATE(STUDENTWORKBOOKTABLE);
					if(studentWorkbook.getExercise()!= null){
						SET(" exercise_id = #{exercise.id} ");
					}
					if(studentWorkbook.getWorkbook()!= null){
						SET(" workbook_id = #{workbook.id} ");
					}
					if(studentWorkbook.getStudent()!= null){
						SET(" student_id = #{student.id} ");
					}
					if(studentWorkbook.getStudentAnswer()!= null){
						SET(" studentAnswer = #{studentAnswer} ");
					}
					if(studentWorkbook.getScore()>=0){
						SET(" score = #{score} ");
					}
					if(studentWorkbook.getGrade()>=0){
						SET(" grade = #{grade} ");
					}
					if(studentWorkbook.getNotes()!=null){
						SET(" notes = #{notes} ");
					}
					if(studentWorkbook.getRate()>=0){
						SET(" rate = #{rate} ");
					}

					if(studentWorkbook.getStudentRate()!=null){
						SET(" studentRate = #{studentRate} ");
					}
					if(studentWorkbook.getInstructions()!=null){
						SET(" instructions = #{instructions} ");
					}
					if(studentWorkbook.getFileName()!=null) {
						SET(" fileName=#{fileName}");
					}
					
					WHERE(" id = #{id} ");
				}
			}.toString();
		}

}
