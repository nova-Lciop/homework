package ssh.homework.dao;


import static ssh.homework.common.HomeworkConstants.WORKBOOKTABLE;

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

import ssh.homework.dao.provider.WorkbookDynaSqlProvider;
import ssh.homework.domain.Workbook;

public interface WorkbookDao {
	// 根据id查询作业
		@Select("select * from "+WORKBOOKTABLE+" where id = #{id}")
		@Results({
			@Result(id=true,column="id",property="id"),
			@Result(column="wflag",property="wflag"),
			@Result(column="term",property="term"),
			@Result(column="title",property="title"),
			@Result(column="fileName",property="fileName"),
			@Result(column="createDate",property="createDate" ,javaType=java.util.Date.class),
			@Result(column="teacher_id", property="teacher",
			one=@One(select="ssh.homework.dao.TeacherDao.selectById",
			 fetchType=FetchType.EAGER)),
			@Result(column="clazz_id", property="clazz",
			one=@One(select="ssh.homework.dao.ClazzDao.selectById",
			 fetchType=FetchType.EAGER)),
			@Result(column="course_id", property="course",
			one=@One(select="ssh.homework.dao.CourseDao.selectById"	,
			 fetchType=FetchType.EAGER))
			})
		Workbook selectById(Integer id);
		
		// 根据id删除作业
		@Delete(" delete from "+WORKBOOKTABLE+" where id = #{id} ")
		void deleteById(@Param("id") Integer id);
			
		
			
		// 动态查询
		@SelectProvider(type=WorkbookDynaSqlProvider.class,method="selectWhitParam")
		@Results({
			@Result(id=true,column="id",property="id"),
			@Result(column="wflag",property="wflag"),
			@Result(column="term",property="term"),
			@Result(column="title",property="title"),
			@Result(column="fileName",property="fileName"),
			@Result(column="createDate",property="createDate" ,javaType=java.util.Date.class),
			@Result(column="teacher_id", property="teacher",
			one=@One(select="ssh.homework.dao.TeacherDao.selectById",
			 fetchType=FetchType.EAGER)),
			@Result(column="clazz_id", property="clazz",
			one=@One(select="ssh.homework.dao.ClazzDao.selectById",
			 fetchType=FetchType.EAGER)),
			@Result(column="course_id", property="course",
			one=@One(select="ssh.homework.dao.CourseDao.selectById"	,
			 fetchType=FetchType.EAGER))
			})
		List<Workbook> selectByPage(Map<String, Object> params);
		
		// 根据参数查询作业总数
		@SelectProvider(type=WorkbookDynaSqlProvider.class,method="count")
		Integer count(Map<String, Object> params);
		
		// 动态插入作业
		@SelectProvider(type=WorkbookDynaSqlProvider.class,method="insert")
		void save(Workbook workbook);
		// 动态更新作业
			@SelectProvider(type=WorkbookDynaSqlProvider.class,method="update")
		void update(Workbook workbook);
		
			//根据班级ID查询关联的workbook
			@Select("select * from "+WORKBOOKTABLE+" where clazz_id=#{id}")
			@Results({
				@Result(id=true,column="id",property="id"),
				@Result(column="wflag",property="wflag"),
				@Result(column="term",property="term"),
				@Result(column="title",property="title"),
				@Result(column="fileName",property="fileName"),
				@Result(column="createDate",property="createDate" ,javaType=java.util.Date.class),
				@Result(column="teacher_id", property="teacher",
				one=@One(select="ssh.homework.dao.TeacherDao.selectById",
				 fetchType=FetchType.EAGER)),
				@Result(column="clazz_id", property="clazz",
				one=@One(select="ssh.homework.dao.ClazzDao.selectById",
				 fetchType=FetchType.EAGER)),
				@Result(column="course_id", property="course",
				one=@One(select="ssh.homework.dao.CourseDao.selectById"	,
				 fetchType=FetchType.EAGER))
				})
			List<Workbook> selectWorkbooksByClazzId(Integer id);
			//根据班级ID和作业名称查询
			@Select("select * from "+WORKBOOKTABLE+" where title=#{title} and clazz_id = #{id}")
			List<Workbook> selectWorkbookByTitle(@Param("title")String title,@Param("id")Integer id);
			//根据教师，课程ID,班级ID,已批改作业和学期查询作业
			@Select("select * from "+WORKBOOKTABLE+" where"
					+ " teacher_id=#{teacher_id} and"
					+ " course_id=#{course_id} and "
					+ " clazz_id = #{clazz_id} and "
					+ "wflag='2' and term=#{term}")
			List<Workbook> selectWorkbookByCourseIdAndClazzIdAndWflagAndTerm(
					@Param("teacher_id")Integer teacher_id,
					@Param("course_id")Integer course_id,
					@Param("clazz_id")Integer clazz_id,
					@Param("term")String term);
			//查询workbook表中的所有学期（去掉重复）
			@Select("select distinct term   FROM "+WORKBOOKTABLE)
			List<String> selectTerm();

}
