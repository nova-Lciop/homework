package ssh.homework.dao.provider;

import static ssh.homework.common.HomeworkConstants.COURSETABLE;


import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import ssh.homework.domain.*;

public class CourseDynaSqlProvider {
	// 分页动态查询
		public String selectWhitParam(Map<String, Object> params){
			String sql =  new SQL(){
				{
					SELECT("*");
					FROM(COURSETABLE);
					if(params.get("course") != null){
						Course course = (Course)params.get("course");
						if(course.getCname() != null && !course.getCname().equals("")){
							WHERE("  cname LIKE CONCAT ('%',#{course.cname},'%') ");
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
					FROM(COURSETABLE);
					if(params.get("course") != null){
						Course tea = (Course)params.get("course");
						if(tea.getCname()!= null && !tea.getCname().equals("")){
							WHERE(" cname LIKE CONCAT ('%',#{course.cname},'%') ");
						}
						
					
					}
				}
			}.toString();
		}	

}
