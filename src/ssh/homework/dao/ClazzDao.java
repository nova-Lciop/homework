package ssh.homework.dao;

import static ssh.homework.common.HomeworkConstants.CLAZZTABLE;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.FetchType;

import ssh.homework.dao.provider.ClazzDynaSqlProvider;
import ssh.homework.domain.Clazz;

public interface ClazzDao {
	// 根据id查询班级
	@Select("select * from " + CLAZZTABLE + " where id = #{id}")

	Clazz selectById(Integer id);

	// 根据id删除班级
	@Delete(" delete from " + CLAZZTABLE + " where id = #{id} ")
	void deleteById(Integer id);

	// 插入班级
	@Insert("insert into " + CLAZZTABLE + " (cname) values(" + "#{cname})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void save(Clazz clazz);

	// 更新班级
	@Update("update " + CLAZZTABLE + " SET CNAME=#{cname} WHERE id=#{id}")
	void update(Clazz clazz);

	// 动态查询
	@SelectProvider(type = ClazzDynaSqlProvider.class, method = "selectWhitParam")
	List<Clazz> selectByPage(Map<String, Object> params);

	// 根据参数查询班级总数
	@SelectProvider(type = ClazzDynaSqlProvider.class, method = "count")
	Integer count(Map<String, Object> params);

	// 根据班级ID查询所有的workbook
	@Select("select * from " + CLAZZTABLE + " ")
	List<Clazz> selectAll();

	// 根据班级ID查询班级同时查询相应的一对多的关联对象workbook
	@Select("select * from " + CLAZZTABLE + " where id = #{id}")
	@Results({ @Result(id = true, column = "id", property = "id"), @Result(column = "cname", property = "cname"),
			@Result(column = "id", property = "workbooks", many = @Many(select = "ssh.homework.dao.WorkbookDao.selectWorkbooksByClazzId", fetchType = FetchType.LAZY)

			),
			@Result(column = "id", property = "students", many = @Many(select = "ssh.homework.dao.StudentDao.selectStudentsByClazzId", fetchType = FetchType.LAZY))

	})
	Clazz selectClazzByIdToMany(Integer id);

	// 根据班级名称cname查询班级
	@Select("select * from " + CLAZZTABLE + " where cname = #{cname}")
	List<Clazz> selectByCname(@Param("cname") String cname);
}
