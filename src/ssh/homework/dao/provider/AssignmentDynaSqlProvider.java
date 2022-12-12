package ssh.homework.dao.provider;

import static ssh.homework.common.HomeworkConstants.ASSIGNMENTTABLE;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import ssh.homework.domain.Assignment;

public class AssignmentDynaSqlProvider {
	// 分页动态查询
				public String selectWhitParam(Map<String, Object> params){
					String sql =  new SQL(){
						{
							SELECT("*");
							FROM(ASSIGNMENTTABLE);
							if(params.get("assignment") != null){
								Assignment assignment = (Assignment)params.get("assignment");
								if(assignment.getExercise()!= null&&assignment.getExercise().getId()!=0) {
									WHERE("  exercise_id=#{assignment.course.id}");
								}
								if(assignment.getWorkbook()!= null&&assignment.getWorkbook().getId()!=0) 
								{
									
									WHERE("  workbook_id=#{assignment.workbook.id}");
									
										
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
							FROM(ASSIGNMENTTABLE);
							if(params.get("assignment") != null){
								Assignment assignment = (Assignment)params.get("assignment");
								if(assignment.getExercise()!= null&&assignment.getExercise().getId()!=0) {
									WHERE("  exercise_id=#{assignment.course.id}");
								}
								if(assignment.getWorkbook()!= null&&assignment.getWorkbook().getId()!=0) {
									WHERE("  workbook_id=#{assignment.workbook.id}");
								}
							}
						}
					}.toString();
				}	
				
				// 动态插入
				public String insert(Assignment assignment){
					return new SQL(){
						{
							INSERT_INTO(ASSIGNMENTTABLE);
							
							if(assignment.getExercise() != null){
								VALUES("exercise_id", "#{exercise.id}");
							}
							if(assignment.getWorkbook()!= null){
								VALUES("workbook_id", "#{workbook.id}");
							}
							if(assignment.getGrade()!= 0){
								VALUES("grade", "#{grade}");
							}
						}
					}.toString();
				}
				// 动态更新
					public String update(Assignment assignment){
									
						return new SQL(){
							{
								UPDATE(ASSIGNMENTTABLE);
								if(assignment.getExercise()!= null){
									SET(" exercise_id = #{exercise.id} ");
								}
								if(assignment.getWorkbook()!= null){
									SET(" workbook_id = #{workbook.id} ");
								}
								if(assignment.getGrade()!= 0){
									SET(" grade = #{grade} ");
								}
								
								WHERE(" id = #{id} ");
							}
						}.toString();
					}

}
