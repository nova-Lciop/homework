package ssh.homework.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ssh.homework.dao.StudentDao;

import ssh.homework.domain.Student;
import ssh.homework.service.StudentService;
import ssh.homework.tag.PageModel;
//StudentService接口实现类，功能是操作tb_student表
@Service("studentService")
public class StudentServiceImpl implements StudentService {
	@Autowired
	StudentDao studentDao;
	@Override
	//处理学生登录
	public Student login(String loginname, String password) {
		Student student=studentDao.selectByLoginnameAndPassword(loginname,password);
		return  student;
	}
@Override
//根据学生id查询学生对象
	public Student findStudentById(Integer id) {
		// TODO Auto-generated method stub
		return studentDao.selectById(id);
	}
@Override
//根据student对象查询tb_student表
	public List<Student> findStudent(Student student, PageModel pageModel) {
	/** 当前需要分页的总数据条数  */
	Map<String,Object> params = new HashMap<>();
	params.put("student", student);
	int recordCount = studentDao.count(params);
	pageModel.setRecordCount(recordCount);
	if(recordCount > 0){
        /** 开始分页查询数据：查询第几页的数据 */
	    params.put("pageModel", pageModel);
    }
	List<Student> students = studentDao.selectByPage(params);
	 
		return students;
	}
@Override
//根据学生id删除学生
	public void removeStudentById(Integer id) {
		studentDao.deleteById(id);
		
	}
@Override
//根据student对象修改tb_student表请求
	public void modifyStudent(Student student) {
		studentDao.update(student);
		
	}
@Override
//添加学生到tb_student表
	public void addStudent(Student student) {
	
	    if(studentDao.selectByLoginname(student.getLoginname())==null)
		studentDao.save(student);
		
	}
//根据班级ID查询学生
@Override
public List<Student> findStudentsByClazzId(Integer id) {
	
	return studentDao.selectStudentsByClazzId(id);
	}
}
