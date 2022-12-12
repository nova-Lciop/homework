package ssh.homework.dao.provider;



import static ssh.homework.common.HomeworkConstants.ASSIGNMENTTABLE;
import static ssh.homework.common.HomeworkConstants.EXERCISETABLE;

import java.util.Map;


import org.apache.ibatis.jdbc.SQL;

import ssh.homework.domain.Exercise;
import ssh.homework.domain.Workbook;


public class ExerciseDynaSqlProvider {
	// 分页动态查询
			public String selectWhitParam(Map<String, Object> params){
				String sql =  new SQL(){
					{
						SELECT("*");
						FROM(EXERCISETABLE);
						if(params.get("exercise") != null){
							Exercise exercise = (Exercise)params.get("exercise");
							if(exercise.getChapter()!= null && !exercise.getChapter().equals("")
									&& !exercise.getChapter().equals("0")){
								WHERE(" chapter=#{exercise.chapter}");
							}
							if(exercise.getKind()!= null && !exercise.getKind().equals("")
									&& !exercise.getKind().equals("0")){
								WHERE("  kind=#{exercise.kind}");
							}
							if(exercise.getCourse()!= null&&exercise.getCourse().getId()!=null&&exercise.getCourse().getId()!=0) {
								WHERE("  course_id=#{exercise.course.id}");
							}
							if(exercise.getTeacher()!= null&&exercise.getTeacher().getId()!=null&&exercise.getTeacher().getId()!=0) {
								WHERE("  tea_id=#{exercise.teacher.id}");
							}
							
							
						}
					}
				}.toString();
				sql+=" order by chapter";
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
						FROM(EXERCISETABLE);
						if(params.get("exercise") != null){
							Exercise exercise = (Exercise)params.get("exercise");
							if(exercise.getChapter()!= null && !exercise.getChapter().equals("")
									&& !exercise.getChapter().equals("0")){
								WHERE(" chapter=#{exercise.chapter}");
							}
							if(exercise.getKind()!= null && !exercise.getKind().equals("")
									&& !exercise.getKind().equals("0")){
								WHERE("  kind=#{exercise.kind}");
							}
							if(exercise.getCourse()!= null&&exercise.getCourse().getId()!=null&&exercise.getCourse().getId()!=0) {
								WHERE("  course_id=#{exercise.course.id}");
							}
							if(exercise.getTeacher()!= null&&exercise.getTeacher().getId()!=null&&exercise.getTeacher().getId()!=0) {
								WHERE("  tea_id=#{exercise.teacher.id}");
							}
							
							
						}
					}
				}.toString();
			}	
			
			// 动态插入
			public String insert(Exercise exercise){
				return new SQL(){
					{
						INSERT_INTO(EXERCISETABLE);
						if(exercise.getAnswer()!= null && !exercise.getAnswer().equals("")){
							VALUES("answer", "#{answer}");
						}
						if(exercise.getChapter()!= null && !exercise.getChapter().equals("")){
							VALUES("chapter", "#{chapter}");
						}
						if(exercise.getContent() != null && !exercise.getContent().equals("")){
							VALUES("content", "#{content}");
						}
						if(exercise.getCourse() != null){
							VALUES("course_id", "#{course.id}");
						}
						if(exercise.getTeacher()!= null){
							VALUES("tea_id", "#{teacher.id}");
						}
						if(exercise.getKind()!= null){
							VALUES("kind", "#{kind}");
						}
					}
				}.toString();
			}
			// 动态更新
				public String update(Exercise exercise){
								
					return new SQL(){
						{
							UPDATE(EXERCISETABLE);
							if(exercise.getKind() != null){
								SET(" kind = #{kind} ");
							}
							if(exercise.getAnswer() != null){
								SET(" answer = #{answer} ");
							}
							if(exercise.getChapter()!= null){
								SET("chapter = #{chapter} ");
							}
							if(exercise.getContent()!= null){
								SET(" content = #{content} ");
							}
							if(exercise.getCourse()!= null){
								SET(" course_id = #{course.id} ");
							}
							if(exercise.getTeacher()!= null){
								SET(" tea_id = #{teacher.id} ");
							}
							
							WHERE(" id = #{id} ");
						}
					}.toString();
				}
				// 根据workbook分页动态查询
				public String selectByWorkbook(Map<String, Object> params){
					
					String sql =  new SQL(){
						{
							SELECT("*");
							FROM(EXERCISETABLE);
							if(params.get("workbook")!= null&&params.get("isNotIn")!=null){
								Workbook workbook=(Workbook)params.get("workbook");
								String isNotIn=(String)params.get("isNotIn");
								if(workbook.getId()!=null){
									WHERE(" id "+isNotIn+"( select exercise_id from "+ 
											ASSIGNMENTTABLE+" where  workbook_id=#{workbook.id})");
								}
								if(workbook.getCourse()!= null&&workbook.getCourse().getId()!=null){
									WHERE("  course_id=#{workbook.course.id}");
								}																
							}
							
						}
					}.toString();
					sql+=" ORDER BY chapter ";
					if(params.get("pageModel") != null){
						sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize}  ";
					}
					
					return sql;
				}	
				// 根据Workbook动态查询总数量
				public String countByWorkbook(Map<String, Object> params){
					return new SQL(){
						{
							SELECT("count(*)");
							FROM(EXERCISETABLE) ;							 
							if(params.get("workbook")!= null&&params.get("isNotIn")!=null){
								Workbook workbook=(Workbook)params.get("workbook");
								String isNotIn=(String)params.get("isNotIn");
								if(workbook.getId()!=null){
									WHERE(" id "+isNotIn+"( select exercise_id from "+ 
											ASSIGNMENTTABLE+" where  workbook_id=#{workbook.id})");
								}
								if(workbook.getCourse()!= null&&workbook.getCourse().getId()!=null){
									WHERE("  course_id=#{workbook.course.id}");
								}																
							}
							
						}
					}.toString();
					
				}	
				
				
}
