package ssh.homework.dao.provider;

import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

import ssh.homework.domain.Teacher;
import static ssh.homework.common.HomeworkConstants.TEACHERTABLE;

/** 教师动态SQL语句提供类 */
public class TeacherDynaSqlProvider {
	/** 动态分页查询 */
	public String selectWhitParam(Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("*");
				FROM(TEACHERTABLE);
				if (params.get("teacher") != null) {
					Teacher tea = (Teacher) params.get("teacher");
					if (tea.getUsername() != null && !tea.getUsername().equals("")) {
						WHERE("  username LIKE CONCAT ('%',#{teacher.username},'%') ");
					}
					if (tea.getLoginname() != null && !tea.getLoginname().equals("")) {
						WHERE("  loginname LIKE CONCAT ('%',#{teacher.loginname},'%') ");
					}

				}
			}
		}.toString();

		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize}  ";
		}

		return sql;
	}

	/** 动态查询总数量 */
	public String count(Map<String, Object> params) {
		return new SQL() {
			{
				SELECT("count(*)");
				FROM(TEACHERTABLE);
				if (params.get("teacher") != null) {
					Teacher tea = (Teacher) params.get("teacher");
					if (tea.getLoginname() != null && !tea.getLoginname().equals("")) {
						WHERE(" loginname LIKE CONCAT ('%',#{tea.loginname},'%') ");
					}
					if (tea.getUsername() != null && !tea.getUsername().equals("")) {
						WHERE(" username LIKE CONCAT ('%',#{tea.username},'%') ");
					}

				}
			}
		}.toString();
	}

	/** 动态插入 */
	public String insertTea(Teacher tea) {
		return new SQL() {
			{
				INSERT_INTO(TEACHERTABLE);
				if (tea.getLoginname() != null && !tea.getLoginname().equals("")) {
					VALUES("loginname", "#{loginname}");
				}
				if (tea.getUsername() != null && !tea.getUsername().equals("")) {
					VALUES("username", "#{username}");
				}
				if (tea.getPassword() != null && !tea.getPassword().equals("")) {
					VALUES("password", "#{password}");
				}
				if (tea.getRole() != null && !tea.getRole().equals("")) {
					VALUES("role", "#{role}");
				}
			}
		}.toString();
	}

	/** 动态更新 */
	public String updateTeacher(Teacher tea) {

		return new SQL() {
			{
				UPDATE(TEACHERTABLE);
				if (tea.getLoginname() != null) {
					SET(" loginname = #{loginname} ");
				}
				if (tea.getUsername() != null) {
					SET("username = #{username} ");
				}
				if (tea.getPassword() != null) {
					SET(" password = #{password} ");
				}
				if (tea.getRole() != null) {
					SET(" role = #{role} ");
				}

				WHERE(" id = #{id} ");
			}
		}.toString();
	}
}
