package ssh.homework.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ssh.homework.dao.CourseDao;

import ssh.homework.domain.Course;
import ssh.homework.service.CourseService;
import ssh.homework.tag.PageModel;

@Service("courseService")
public class CourseServiceImpl implements CourseService {
	@Autowired
	CourseDao courseDao;
	//添加课程
	@Override
	public void addCourse(Course course) {
		if(courseDao.selectByCname(course.getCname()).size()<=0)
		courseDao.save(course);
		
	}
	//根据id删除课程
	@Override
	public void removeCourseById(Integer id) {
		courseDao.deleteById(id);
		
	}
	//修改课程
	@Override
	public void modifyCourse(Course course) {
		courseDao.update(course);
		
	}
	//根据id查询课程
	@Override
	public Course findCourseById(Integer id) {
		
		return courseDao.selectById(id);
	}
	//根据course对象中属性的值动态查询课程表
	@Override
	public List<Course> findCourse(Course course, PageModel pageModel) {
		/** 当前需要分页的总数据条数  */
		Map<String,Object> params = new HashMap<>();
		params.put("course", course);
		int recordCount = courseDao.count(params);
		pageModel.setRecordCount(recordCount);
		if(recordCount > 0){
	        /** 开始分页查询数据：查询第几页的数据 */
		    params.put("pageModel", pageModel);
	    }
		List<Course> clazzs = courseDao.selectByPage(params);
		
		return clazzs;
	}
	//查询课程表中所有课程 
	@Override
	public List<Course> findAllCourse() {
		// TODO Auto-generated method stub
		return courseDao.selectAll();
	}

}
