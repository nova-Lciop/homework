package ssh.homework.dao.provider;

import static ssh.homework.common.HomeworkConstants.WORKBOOKTABLE;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import ssh.homework.domain.Workbook;

public class WorkbookDynaSqlProvider {
	// 分页动态查询
			public String selectWhitParam(Map<String, Object> params){
				String sql =  new SQL(){
					{
						SELECT("*");
						FROM(WORKBOOKTABLE) ;
						if(params.get("workbook") != null){
							Workbook workbook = (Workbook)params.get("workbook");
								if(workbook.getCreateDate()!= null){
									WHERE("  createDate=#{workbook.createDate}");
								}
								if(workbook.getWflag()!=null && workbook.getWflag()!="" && !workbook.getWflag().equals("-1")){
									WHERE("  wflag=#{workbook.wflag}");
								}
								if(workbook.getTerm()!=null&&workbook.getTerm()!=""){
									WHERE("  term  LIKE CONCAT ('%',#{workbook.term},'%')");
								}
								if(workbook.getClazz()!= null &&workbook.getClazz().getId()!=null&&workbook.getClazz().getId()!=0){
									WHERE("  clazz_id=#{workbook.clazz.id}");
								}
								if(workbook.getCourse()!= null &&workbook.getCourse().getId()!=null&&workbook.getCourse().getId()!=0){
									WHERE("  course_id=#{workbook.course.id}");
								}
								if(workbook.getTeacher()!= null &&workbook.getTeacher().getId()!=null&&workbook.getTeacher().getId()!=0){
									WHERE("  teacher_id=#{workbook.teacher.id}");
								}
								if(workbook.getFileName()!= null && workbook.getTeacher().getId()!=0){
									WHERE("  fileName=#{workbook.fileName}");
								}
								
							
						}WHERE("  wflag!=3");
					}
				}.toString();
				sql+=" ORDER BY course_id ";
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
						FROM(WORKBOOKTABLE);
						if(params.get("workbook") != null){
							Workbook workbook = (Workbook)params.get("workbook");
							if(workbook.getCreateDate()!= null){
								WHERE("  createDate=#{workbook.createDate}");
							}
							if(workbook.getWflag()!=null && workbook.getWflag()!="" && !workbook.getWflag().equals("-1")){
								WHERE("  wflag=#{workbook.wflag}");
							}
							if(workbook.getTerm()!=null&&workbook.getTerm()!=""){
								WHERE("  term  LIKE CONCAT ('%',#{workbook.term},'%')");
							}
							if(workbook.getClazz()!= null &&workbook.getClazz().getId()!=null&&workbook.getClazz().getId()!=0){
								WHERE("  clazz_id=#{workbook.clazz.id}");
							}
							if(workbook.getCourse()!= null &&workbook.getCourse().getId()!=null&&workbook.getCourse().getId()!=0){
								WHERE("  course_id=#{workbook.course.id}");
							}
							if(workbook.getTeacher()!= null &&workbook.getTeacher().getId()!=null&&workbook.getTeacher().getId()!=0){
								WHERE("  teacher_id=#{workbook.teacher.id}");
							}
							if(workbook.getFileName()!= null && workbook.getTeacher().getId()!=0){
								WHERE("  fileName=#{workbook.fileName}");
							}
							
						WHERE("  wflag!=3");

						
						}
					}
				}.toString();
			}	
			
			// 动态插入
			public String insert(Workbook workbook){
				return new SQL(){
					{if(workbook!=null) {
						INSERT_INTO(WORKBOOKTABLE);
						
							VALUES("teacher_id", "#{teacher.id}");
							VALUES("course_id", "#{course.id}");
							VALUES("title", "#{title}");
							VALUES("clazz_id", "#{clazz.id}");
							VALUES("wflag", "#{wflag}");
							VALUES("term", "#{term}");
							VALUES("createdate", "#{createDate}");
							VALUES("fileName","#{fileName}");
					}
					}
				}.toString();
				
			}
			// 动态更新
				public String update(Workbook workbook){
					
					return new SQL(){
						{
							UPDATE(WORKBOOKTABLE);
							if(workbook.getCreateDate() != null){
								SET(" createdate = #{createDate} ");
							}
							if(workbook.getWflag()!=null||workbook.getWflag()!=""){
								SET("wflag = #{wflag} ");
							}
							if(workbook.getTerm()!= null){
								SET(" term = #{term} ");
							}
							if(workbook.getTitle()!= null){
								SET(" title= #{title} ");
							}
							if(workbook.getClazz()!= null){
								SET(" clazz_id = #{clazz.id} ");
							}
							if(workbook.getCourse()!= null){
								SET(" course_id = #{course.id} ");
							}
							if(workbook.getTeacher()!= null){
								SET(" teacher_id = #{teacher.id} ");
							}
							if(workbook.getFileName()!=null) {
								SET("fileName=#{fileName}");
							}
							
							WHERE(" id = #{id} ");
						}
					}.toString();
				}
}
