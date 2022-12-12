package ssh.homework.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ssh.homework.dao.TeacherDao;
import ssh.homework.domain.Teacher;
import ssh.homework.service.TeacherService;
import ssh.homework.tag.PageModel;

/**  教师管理模块服务层接口TeacherService实现类    */

@Service("teacherService")
public class TeacherServiceImpl implements TeacherService{

	/**
	 * 自动注入持久层Dao对象
	 * */
	@Autowired
	private TeacherDao teacherDao;	

	/**
	 * TeacherService接口login方法实现
	 * */

	@Override
	public Teacher login(String loginname, String password) {
//		System.out.println("TeacherServiceImpl login -- >>");
		return teacherDao.selectByLoginnameAndPassword(loginname, password);
	}

	/**  根据tea对象中属性值动态查询  */

	@Override
	public List<Teacher> findTeacher(Teacher tea,PageModel pageModel) {
		/** 当前需要分页的总数据条数  */
		Map<String,Object> params = new HashMap<>();
		params.put("teacher", tea);
		int recordCount =teacherDao.count(params);
		pageModel.setRecordCount(recordCount);
		if(recordCount > 0){
	        /** 开始分页查询数据：查询第几页的数据 */
		    params.put("pageModel", pageModel);
	    }
		List<Teacher> teas = teacherDao.selectByPage(params);
		 
		return teas;
	}
	
	/**根据id查询教师  */

	@Override
	public Teacher findTeacherById(Integer id) {
		return teacherDao.selectById(id);
	}
	
	/**
	 * TeacherService接口removeUserById方法实现
	 * @see { TeacherService }
	 * */
	@Override
	public void removeTeacherById(Integer id) {
		teacherDao.deleteById(id);
		
	}	
	/**
	 * TeacherService接口addTeacher方法实现
	 * @see { TeachermService }
	 * */
	@Override
	public void addTeacher(Teacher tea) {
		//判断数据库中是否存在相同的loginname的值，如果不相同执行插入操作
		if(findByLoginname(tea.getLoginname())==null)
		teacherDao.save(tea);
		
	}
	/**
	 * TeacherService接口modifyTeacher方法实现
	 * @see { TeachermService }
	 * */
	@Override
	public void modifyTeacher(Teacher tea) {
		teacherDao.update(tea);		
	}
	/**
	 * TeacherService接口findTeacherAll方法实现
	 * @see { TeachermService }
	 * */
	@Override
	public List<Teacher> findTeacherAll() {		
		return teacherDao.selectAll();
	}
	/**
	 * TeacherService接口findByLoginname方法实现
	 * @see { TeachermService }
	 * */
	@Override
	public Teacher findByLoginname(String loginname) {
		
		return teacherDao.selectByLoginname(loginname);
	}
	
}
