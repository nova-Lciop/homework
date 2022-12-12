package ssh.homework.dao;


import static ssh.homework.common.HomeworkConstants.EXERCISETABLE;

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

import ssh.homework.dao.provider.ExerciseDynaSqlProvider;
import ssh.homework.domain.Exercise;
import ssh.homework.domain.ExerciseInfo;
//tb_exercise表的操作类
public interface ExerciseDao {
	// 根据id查询习题
	@Select("select * from "+EXERCISETABLE+" where id = #{id} ")
	@Results({
		@Result(id=true,column="id",property="id"),
		@Result(column="answer",property="answer"),
		@Result(column="chapter",property="chapter"),
		@Result(column="content",property="content"),
		@Result(column="tea_id", property="teacher",
		one=@One(select="ssh.homework.dao.TeacherDao.selectById",
		 fetchType=FetchType.EAGER)),
		@Result(column="course_id", property="course",
		one=@One(select="ssh.homework.dao.CourseDao.selectById"	,
		 fetchType=FetchType.EAGER))
		})
	Exercise selectById(Integer id);
	
	// 根据id删除习题
	@Delete(" delete from "+EXERCISETABLE+" where id = #{id} ")
	void deleteById(@Param("id") Integer id);
		
	
		
	// 动态查询
	@SelectProvider(type=ExerciseDynaSqlProvider.class,method="selectWhitParam")
	@Results({
		@Result(id=true,column="id",property="id"),
		@Result(column="answer",property="answer"),
		@Result(column="chapter",property="chapter"),
		@Result(column="content",property="content"),
		@Result(column="course_id", property="course",
		one=@One(select="ssh.homework.dao.CourseDao.selectById"	,
		 fetchType=FetchType.EAGER)),
		@Result(column="tea_id", property="teacher",
		one=@One(select="ssh.homework.dao.TeacherDao.selectById",
		 fetchType=FetchType.EAGER))
		})
	List<Exercise> selectByPage(Map<String, Object> params);
	
	// 根据参数查询习题总数
	@SelectProvider(type=ExerciseDynaSqlProvider.class,method="count")
	Integer count(Map<String, Object> params);
	
	// 动态插入习题
	@SelectProvider(type=ExerciseDynaSqlProvider.class,method="insert")
	void save(Exercise exercise);
	// 动态更新习题
		@SelectProvider(type=ExerciseDynaSqlProvider.class,method="update")
	void update(Exercise exercise);
		
	//将已经布置到tb_assignment表中的习题从tb_exercise表中除去，这样已经布置过的习题不会出现在习题列表中
	@SelectProvider(type=ExerciseDynaSqlProvider.class,method="selectByWorkbook")
	@Results({
		@Result(id=true,column="id",property="id"),
		@Result(column="answer",property="answer"),
		@Result(column="chapter",property="chapter"),
		@Result(column="content",property="content"),
		@Result(column="course_id", property="course",
		one=@One(select="ssh.homework.dao.CourseDao.selectById"	,
		 fetchType=FetchType.EAGER)),
		@Result(column="tea_id", property="teacher",
		one=@One(select="ssh.homework.dao.TeacherDao.selectById",
		 fetchType=FetchType.EAGER))
		})
	List<Exercise> selectNotInWorkbookIdAndByCourseId(Map<String, Object> params);
	
	@SelectProvider(type=ExerciseDynaSqlProvider.class,method="countByWorkbook")
	Integer countByWorkbook(Map<String, Object> params);
	//统计同一个作业中的每个习题的错误次数
	@Select("SELECT  exercise_id,count(*) as count "+ 
			"FROM student_workbook where workbook_id=#{workbook_id} and grade>score "
			+ "group by exercise_id ")
			@Results({
				@Result(column="count",property="count"),
				@Result(column="exercise_id", property="exercise",
				one=@One(select="ssh.homework.dao.ExerciseDao.selectById",
				 fetchType=FetchType.EAGER))				
				})
	List<ExerciseInfo> selectExerciseInfoByWorkbookId(Integer Workbook_id);	
}
