package ssh.homework.service;

import java.util.List;
import ssh.homework.domain.Teacher;
import ssh.homework.tag.PageModel;
/**   教师管理服务层接口  */
public interface TeacherService {
	/**  教师登录 */
    Teacher login(String loginname,String password);
	
	/** 根据id查询教师
	 * @param id
	 * @return 教师对象
	 * */
	Teacher findTeacherById(Integer id);
	List<Teacher> findTeacherAll();
	
	/**
	 * 教师动态查询
	 * @return Teacher对象的List集合
	 * */
	List<Teacher> findTeacher(Teacher teacher,PageModel pageModel);
	
	/**
	 * 根据id删除教师
	 * @param id
	 * */
	void removeTeacherById(Integer id);
	
	/**
	 * 修改教师
	 * */
	void modifyTeacher(Teacher teacher);
	
	/**
	 * 添加教师
	 * 
	 * */
	void addTeacher(Teacher teacher);
	/**
	 * 查询用户
	 * loginname登录用户名
	 * */
	Teacher findByLoginname(String loginname);
	
}