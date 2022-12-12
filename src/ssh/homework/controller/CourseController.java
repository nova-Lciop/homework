package ssh.homework.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ssh.homework.domain.Course;
import ssh.homework.service.CourseService;
import ssh.homework.tag.PageModel;
//有关课程请求的处理器类
@Controller
public class CourseController {
	
	@Autowired
	@Qualifier("courseService")
	//课程业务逻辑服务类，由Ioc容器实例化并注入其对象
	private CourseService courseService;
	//处理查询课程的请求，查询条件由参数course中的属性决定
	@RequestMapping(value="/course/selectCourse")	
	 public String selectCourse(Integer pageIndex,
			 @ModelAttribute Course course,
			 Model model){
		PageModel pageModel = new PageModel();
		if(pageIndex != null){
			pageModel.setPageIndex(pageIndex);
		}
		/** 查询用户信息     */
		List<Course> courses = courseService.findCourse(course, pageModel);
		model.addAttribute("courses", courses);
		model.addAttribute("pageModel", pageModel);
		return "course/course";
		
	}
	//添加课程的请求处理
	@RequestMapping(value="/course/addCourse")
	 public ModelAndView addCourse(
			 String flag,
			 @ModelAttribute Course course,
			 ModelAndView mv){
		if(flag.equals("1")){
			// 设置跳转到添加页面
			mv.setViewName("course/showAddCourse");
		}else{
			// 执行添加操作
			courseService.addCourse(course);
			// 设置客户端跳转到查询请求
			mv.setViewName("redirect:/course/selectCourse");
		}
		// 返回
		return mv;
	}
	//删除课程的请求处理
	@RequestMapping(value="/course/removeCourse")
	 public ModelAndView removeCourse(String ids,ModelAndView mv){
		// 分解id字符串
		String[] idArray = ids.split(",");
		try {
		for(String id : idArray){
			// 根据id删除员工
			courseService.removeCourseById(Integer.parseInt(id));
		}
		}catch(Exception ex) {
			mv.addObject("error","课程不能删除！");
			mv.setViewName("error/error");
			return mv;
		}
		// 设置客户端跳转到查询请求
		mv.setViewName("redirect:/course/selectCourse");
		// 返回ModelAndView
		return mv;
	}
	//更新课程的请求处理
	@RequestMapping(value="/course/updateCourse")
	 public ModelAndView updateCourse(
			 String flag,
			 @ModelAttribute Course course,
			 ModelAndView mv){
		if(flag.equals("1")){
			// 根据id查询用户
			Course target = courseService.findCourseById(course.getId());
			// 设置Model数据
			mv.addObject("course", target);
			// 返回修改员工页面
			mv.setViewName("course/showUpdateCourse");
		}else{
			// 执行修改操作
			courseService.modifyCourse(course);
			// 设置客户端跳转到查询请求
			mv.setViewName("redirect:/course/selectCourse");
		}
		// 返回
		return mv;
	}


}
