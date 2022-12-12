package ssh.homework.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import ssh.homework.dao.provider.TeacherDynaSqlProvider;
import ssh.homework.domain.Teacher;

import static ssh.homework.common.HomeworkConstants.TEACHERTABLE;
/**   
 *TeacherDao接口
 */
public interface TeacherDao{

	// 根据登录名和密码查询员工
	@Select("select * from "+TEACHERTABLE+" where loginname = #{loginname} and password = #{password}")
	Teacher selectByLoginnameAndPassword(
			@Param("loginname") String loginname,
			@Param("password") String password);
	
	// 根据id查询用户
	@Select("select * from "+TEACHERTABLE+" where id = #{id}")
	Teacher selectById(Integer id);

	// 查询所有用户
	@Select("select * from "+TEACHERTABLE+" ")
	List<Teacher> selectAll();
	
	// 根据id删除用户
	@Delete(" delete from "+TEACHERTABLE+" where id = #{id} ")
	void deleteById(@Param("id") Integer id);
		
	
		
	// 动态查询
	@SelectProvider(type=TeacherDynaSqlProvider.class,method="selectWhitParam")
	List<Teacher> selectByPage(Map<String, Object> params);
	
	// 根据参数查询用户总数
	@SelectProvider(type=TeacherDynaSqlProvider.class,method="count")
	Integer count(Map<String, Object> params);
	
	// 动态插入用户
	@SelectProvider(type=TeacherDynaSqlProvider.class,method="insertTea")
	void save(Teacher tea);
	// 动态更新用户
		@SelectProvider(type=TeacherDynaSqlProvider.class,method="updateTeacher")
	void update(Teacher tea);
	//根据登录用户名查询用户
		@Select("select * from "+TEACHERTABLE+" where loginname = #{loginname}")
	Teacher selectByLoginname(String loginname);
}
