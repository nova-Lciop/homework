package ssh.homework.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import ssh.homework.dao.provider.CourseDynaSqlProvider;

import ssh.homework.domain.Course;

import static ssh.homework.common.HomeworkConstants.COURSETABLE;

public interface CourseDao {
	// 根据id查询课程
	@Select("select * from " + COURSETABLE + " where id = #{id}")
	Course selectById(Integer id);
	// 查询所有课程
	@Select("select * from " + COURSETABLE + " ")
	List<Course> selectAll();

	// 根据id删除课程
	@Delete(" delete from " + COURSETABLE + " where id = #{id} ")
	void deleteById(Integer id);

	// 插入课程
	@Insert("insert into " + COURSETABLE + " (cname) values(" + "#{cname})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void save(Course course);

	// 更新课程
	@Update("update " + COURSETABLE + " SET CNAME=#{cname} WHERE id=#{id}")
	void update(Course course);

	// 动态查询
	@SelectProvider(type=CourseDynaSqlProvider.class,method="selectWhitParam")
	List<Course> selectByPage(Map<String, Object> params);
	
	// 根据参数查询课程总数
	@SelectProvider(type=CourseDynaSqlProvider.class,method="count")
	Integer count(Map<String, Object> params);
	// 根据课程名称精确查询课程
		@Select("select * from " + COURSETABLE + " where cname = #{cname}")
		List<Course> selectByCname(@Param("cname")String cname);
//

}
