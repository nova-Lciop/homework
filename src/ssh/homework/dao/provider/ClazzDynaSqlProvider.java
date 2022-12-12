package ssh.homework.dao.provider;

import static ssh.homework.common.HomeworkConstants.CLAZZTABLE;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import ssh.homework.domain.Clazz;

public class ClazzDynaSqlProvider {
	// 分页动态查询
	public String selectWhitParam(Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("*");
				FROM(CLAZZTABLE);
				if (params.get("clazz") != null) {
					Clazz clazz = (Clazz) params.get("clazz");
					if (clazz.getCname() != null && !clazz.getCname().equals("")) {
						WHERE("  cname LIKE CONCAT ('%',#{clazz.cname},'%') ");
					}

				}
			}
		}.toString();

		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize}  ";
		}

		return sql;
	}

	// 动态查询总数量
	public String count(Map<String, Object> params) {
		return new SQL() {
			{
				SELECT("count(*)");
				FROM(CLAZZTABLE);
				if (params.get("clazz") != null) {
					Clazz tea = (Clazz) params.get("clazz");
					if (tea.getCname() != null && !tea.getCname().equals("")) {
						WHERE(" cname LIKE CONCAT ('%',#{clazz.cname},'%') ");
					}

				}
			}
		}.toString();
	}

}
