package ssh.homework.service;

import java.util.List;

import ssh.homework.domain.Course;
import ssh.homework.tag.PageModel;

public interface CourseService {
    //添加课程
	void addCourse(Course course);
	//根据id删除课程
	void removeCourseById(Integer id);
	//修改课程
	void modifyCourse(Course course);
	//根据id查询课程
	Course findCourseById(Integer id);
	//根据course对象中属性的值动态查询课程表
	List<Course> findCourse(Course course,PageModel pageModel);
	//查询课程表中所有课程 
	List<Course> findAllCourse();
}
