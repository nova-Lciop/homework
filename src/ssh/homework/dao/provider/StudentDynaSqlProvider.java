package ssh.homework.dao.provider;

import static ssh.homework.common.HomeworkConstants.STUDENTTABLE;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import ssh.homework.domain.Student;

public class StudentDynaSqlProvider {
	// 分页动态查询
		public String selectWhitParam(Map<String, Object> params){
			String sql =  new SQL(){
				{
					SELECT("*");
					FROM(STUDENTTABLE);
					if(params.get("student") != null){
						Student student = (Student)params.get("student");
						if(student.getUsername() != null && !student.getUsername().equals("")){
							WHERE("  username LIKE CONCAT ('%',#{student.username},'%') ");
						}
						if(student.getLoginname() != null && !student.getLoginname().equals("")){
							WHERE("  loginname LIKE CONCAT ('%',#{student.loginname},'%') ");
						}
						if(student.getClazz()!= null && student.getClazz().getId()!=0){
							WHERE("  clazz_id=#{student.clazz.id}");
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
					FROM(STUDENTTABLE);
					if(params.get("student") != null){
						Student student = (Student)params.get("student");
						if(student.getUsername() != null && !student.getUsername().equals("")){
							WHERE("  username LIKE CONCAT ('%',#{student.username},'%') ");
						}
						if(student.getLoginname() != null && !student.getLoginname().equals("")){
							WHERE("  loginname LIKE CONCAT ('%',#{student.loginname},'%') ");
						}
						if(student.getClazz()!= null && student.getClazz().getId()!=0){
							WHERE("  clazz_id=#{student.clazz.id}");
						}
					}
				}
			}.toString();
		}	
		
		// 动态插入
		public String insert(Student student){
			return new SQL(){
				{
					INSERT_INTO(STUDENTTABLE);
					if(student.getLoginname() != null && !student.getLoginname().equals("")){
						VALUES("loginname", "#{loginname}");
					}
					if(student.getUsername() != null && !student.getUsername().equals("")){
						VALUES("username", "#{username}");
					}
					if(student.getPassword() != null && !student.getPassword().equals("")){
						VALUES("password", "#{password}");
					}
					if(student.getClazz() != null){
						VALUES("clazz_id", "#{clazz.id}");
					}
				}
			}.toString();
		}
		// 动态更新
			public String update(Student student){
				
				return new SQL(){
					{
						UPDATE(STUDENTTABLE);
						if(student.getLoginname() != null){
							SET(" loginname = #{loginname} ");
						}
						if(student.getUsername() != null){
							SET("username = #{username} ");
						}
						if(student.getPassword()!= null){
							SET(" password = #{password} ");
						}
						if(student.getClazz()!= null){
							SET(" clazz_id = #{clazz.id} ");
						}
						
						WHERE(" id = #{id} ");
					}
				}.toString();
			}
}
