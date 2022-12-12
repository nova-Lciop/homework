package ssh.homework.dao;

import static ssh.homework.common.HomeworkConstants.STUDENTTABLE;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.mapping.FetchType;

import ssh.homework.dao.provider.StudentDynaSqlProvider;
import ssh.homework.domain.Student;

/** 数据库表tb_student的操作类*/
public interface StudentDao {
	// 根据登录名和密码查询学生
		@Select("select * from "+STUDENTTABLE+" where loginname = #{loginname} and password = #{password}")
		Student selectByLoginnameAndPassword(
				@Param("loginname") String loginname,
				@Param("password") String password);
		
		// 根据id查询学生
		@Select("select * from "+STUDENTTABLE+" where id = #{id}")
		@Results({
			@Result(id=true,column="id",property="id"),
			@Result(column="loginname",property="loginname"),
			@Result(column="password",property="password"),
			@Result(column="username",property="username"),
			@Result(column="clazz_id", property="clazz",
			one=@One(select="ssh.homework.dao.ClazzDao.selectById"	,
			 fetchType=FetchType.EAGER))
			})
		Student selectById(Integer id);
		
			//根据登录名查找学生
		@Select("select * from "+STUDENTTABLE+" where loginname = #{loginname}")
		Student selectByLoginname(String loginname);
		// 根据id删除学生
		@Delete(" delete from "+STUDENTTABLE+" where id = #{id} ")
		void deleteById(@Param("id") Integer id);		
			
		// 动态查询
		@SelectProvider(type=StudentDynaSqlProvider.class,method="selectWhitParam")
		@Results({
			@Result(id=true,column="id",property="id"),
			@Result(column="loginname",property="loginname"),
			@Result(column="password",property="password"),
			@Result(column="username",property="username"),
			@Result(column="clazz_id", property="clazz",
			one=@One(select="ssh.homework.dao.ClazzDao.selectById"	,
			 fetchType=FetchType.EAGER))
			})
		List<Student> selectByPage(Map<String, Object> params);
		
		// 根据参数查询学生总数
		@SelectProvider(type=StudentDynaSqlProvider.class,method="count")
		Integer count(Map<String, Object> params);
		
		// 动态插入学生
		@SelectProvider(type=StudentDynaSqlProvider.class,method="insert")
		void save(Student student);
		// 动态更新学生
		@SelectProvider(type=StudentDynaSqlProvider.class,method="update")
		void update(Student student);
		//根据班级ID查询学生	
		@Select("select * from "+STUDENTTABLE+" where clazz_id = #{id}")
		@Results({
			@Result(id=true,column="id",property="id"),
			@Result(column="loginname",property="loginname"),
			@Result(column="password",property="password"),
			@Result(column="username",property="username"),
			@Result(column="clazz_id", property="clazz",
			one=@One(select="ssh.homework.dao.ClazzDao.selectById"	,
			 fetchType=FetchType.EAGER))
			})
		List<Student> selectStudentsByClazzId(Integer id);	
		

}
