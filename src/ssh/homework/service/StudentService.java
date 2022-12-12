package ssh.homework.service;

import java.util.List;

import ssh.homework.domain.Student;
import ssh.homework.tag.PageModel;

public interface StudentService {

	/**
	 * 学生登录
	 * @param  loginname
	 * @param  password
	 * @return User对象
	 * */
	Student login(String loginname,String password);
	
	/**
	 * 根据id查询学生
	 * @param id
	 * @return 学生对象
	 * */
	Student findStudentById(Integer id);
	
	/**
	 * 根据班级id查询学生
	 * @param id
	 * @return 学生对象
	 * */
	List<Student> findStudentsByClazzId(Integer id);
	/**
	 * 获得所有学生
	 * @return User对象的List集合
	 * */
	List<Student> findStudent(Student student,PageModel pageModel);
	
	/**
	 * 根据id删除学生
	 * @param id
	 * */
	void removeStudentById(Integer id);
	
	/**
	 * 修改学生
	 * @param Student 学生对象
	 * */
	void modifyStudent(Student student);
	
	/**
	 * 添加学生
	 * @param Student 学生对象
	 * */
	void addStudent(Student student);
	
}
