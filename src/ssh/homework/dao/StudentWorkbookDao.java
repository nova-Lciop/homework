package ssh.homework.dao;


import static ssh.homework.common.HomeworkConstants.STUDENTWORKBOOKTABLE;

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

import ssh.homework.dao.provider.StudentWorkbookDynaSqlProvider;
import ssh.homework.domain.ExerciseInfo;
import ssh.homework.domain.StudentInfo;
import ssh.homework.domain.StudentWorkbook;

public interface StudentWorkbookDao {
	// 根据id查询学生作业内容
				@Select("select * from "+STUDENTWORKBOOKTABLE+" where id = #{id}")
				@Results({
					@Result(id=true,column="id",property="id"),
					@Result(column="studentAnswer",property="studentAnswer"),
					@Result(column="studentAnswer",property="studentAnswer"),
					@Result(column="grade",property="grade"),
					@Result(column="score", property="score"),
					@Result(column="notes",property="notes"),
					@Result(column="rate",property="rate"),
					@Result(column="studentRate",property="studentRate"),
					@Result(column="instructions",property="instructions"),
					@Result(column="fileName",property="fileName"),
					@Result(column="workbook_id", property="workbook",
					one=@One(select="ssh.homework.dao.WorkbookDao.selectById",
					 fetchType=FetchType.EAGER)),
					@Result(column="exercise_id", property="exercise",
					one=@One(select="ssh.homework.dao.ExerciseDao.selectById",
					 fetchType=FetchType.EAGER)),
					@Result(column="student_id", property="student",
					one=@One(select="ssh.homework.dao.StudentDao.selectById",
					 fetchType=FetchType.EAGER))				
					})
				StudentWorkbook selectById(Integer id);
				
				// 根据id删除学生作业内容
				@Delete(" delete from "+STUDENTWORKBOOKTABLE+" where id = #{id} ")
				void deleteById(@Param("id") Integer id);
					
				// 根据workbook_id和student_id删除学生作业内容
				@Delete(" delete from "+STUDENTWORKBOOKTABLE+" where workbook_id = #{workbook_id} "
						+ "and student_id=#{student_id} ")
				void deleteByWorkbookIdAndStudentId(@Param("workbook_id") Integer workbook_id,
						@Param("student_id") Integer student_id);
				
				// 动态查询
				@SelectProvider(type=StudentWorkbookDynaSqlProvider.class,method="selectWhitParam")
				@Results({
					@Result(id=true,column="id",property="id"),
					@Result(column="studentAnswer",property="studentAnswer"),
					@Result(column="grade",property="grade"),
					@Result(column="score", property="score"),
					@Result(column="notes",property="notes"),
					@Result(column="rate",property="rate"),
					@Result(column="studentRate",property="studentRate"),
					@Result(column="instructions",property="instructions"),
					@Result(column="fileName",property="fileName"),
					@Result(column="workbook_id", property="workbook",
					one=@One(select="ssh.homework.dao.WorkbookDao.selectById",
					 fetchType=FetchType.EAGER)),
					@Result(column="exercise_id", property="exercise",
					one=@One(select="ssh.homework.dao.ExerciseDao.selectById",
					 fetchType=FetchType.EAGER)),
					@Result(column="student_id", property="student",
					one=@One(select="ssh.homework.dao.StudentDao.selectById",
					 fetchType=FetchType.EAGER))				
					})
				List<StudentWorkbook> selectByPage(Map<String, Object> params);
				
				// 根据参数查询学生作业内容总数
				@SelectProvider(type=StudentWorkbookDynaSqlProvider.class,method="count")
				Integer count(Map<String, Object> params);
				
				// 动态插入学生作业内容
				@SelectProvider(type=StudentWorkbookDynaSqlProvider.class,method="insert")
				void save(StudentWorkbook studentWorkbook);
				// 动态更新学生作业内容
				@SelectProvider(type=StudentWorkbookDynaSqlProvider.class,method="update")
				void update(StudentWorkbook studentWorkbook);
				
				
				//根据workbook_id按student_id统计每学生的成绩，最大查重率和习题数
				@Select("SELECT  student_id, fileName,sum(score) as score,max(rate) as rate,count(*) as count "+ 
				"FROM student_workbook where  workbook_id=#{workbook_id}  group by student_id  ")
				@Results({
					@Result(column="fileName",property="fileName"),
					@Result(column="score",property="score"),
					@Result(column="rate",property="rate"),		
					@Result(column="count", property="count"),
					@Result(column="student_id", property="student",
					one=@One(select="ssh.homework.dao.StudentDao.selectById",
					 fetchType=FetchType.EAGER))				
					})
				List<StudentInfo> selectStudentInfoGroupByStudentId(Integer workbook_id); 
				
		

}
