package ssh.homework.dao;

import static ssh.homework.common.HomeworkConstants.ASSIGNMENTTABLE;

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

import ssh.homework.dao.provider.AssignmentDynaSqlProvider;
import ssh.homework.domain.Assignment;

public interface AssignmentDao {
	// 根据id查询作业内容
			@Select("select * from "+ASSIGNMENTTABLE+" where id = #{id}")
			@Results({
				@Result(id=true,column="id",property="id"),
				@Result(column="grade",property="grade"),
				@Result(column="workbook_id", property="workbook",
				one=@One(select="ssh.homework.dao.WorkbookDao.selectById",
				 fetchType=FetchType.EAGER)),
				@Result(column="exercise_id", property="exercise",
				one=@One(select="ssh.homework.dao.ExerciseDao.selectById",
				 fetchType=FetchType.EAGER))				
				})
			Assignment selectById(Integer id);
			
			// 根据id删除作业内容
			@Delete(" delete from "+ASSIGNMENTTABLE+" where id = #{id} ")
			void deleteById(@Param("id") Integer id);
				
			// 动态查询
			@SelectProvider(type=AssignmentDynaSqlProvider.class,method="selectWhitParam")
			
			@Results({
				@Result(id=true,column="id",property="id"),
				@Result(column="grade",property="grade"),
				@Result(column="workbook_id", property="workbook",
				one=@One(select="ssh.homework.dao.WorkbookDao.selectById",
				 fetchType=FetchType.EAGER)),
				@Result(column="exercise_id", property="exercise",
				one=@One(select="ssh.homework.dao.ExerciseDao.selectById",
				 fetchType=FetchType.EAGER))				
				})
			
			List<Assignment> selectByPage(Map<String, Object> params);
			
			// 根据参数查询作业内容总数
			@SelectProvider(type=AssignmentDynaSqlProvider.class,method="count")
			Integer count(Map<String, Object> params);
			
			// 动态插入作业内容
			@SelectProvider(type=AssignmentDynaSqlProvider.class,method="insert")
			void save(Assignment assignment);
			// 动态更新作业内容
			@SelectProvider(type=AssignmentDynaSqlProvider.class,method="update")
			void update(Assignment assignment);
			@Select("select * from "+ASSIGNMENTTABLE+" where workbook_id=#{workbookId}")
			
			//根据workbook_id查询tb_assignment表
			List<Assignment> selectAssignmentsByWorkbookId(Integer workbookId);
				


}
