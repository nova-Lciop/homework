package ssh.homework.controller;

import static ssh.homework.common.HomeworkConstants.TEACHER_SESSION;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ssh.homework.common.HomeworkConstants;

import ssh.homework.domain.Teacher;
import ssh.homework.service.TeacherService;
import ssh.homework.tag.PageModel;
/** 有关教师请求的处理类*/
@Controller
public class TeacherController {
	//由Ioc容器自动注入teacherService对象
	@Autowired
	@Qualifier("teacherService")
	private TeacherService teacherService;
	/** 处理教师登录请求 */
	@RequestMapping(value="/login")
	 public ModelAndView login(
			 @RequestParam("loginname") String loginname,
			 @RequestParam("password") String password,
			 HttpSession session,
			 @RequestParam("loginType") String loginType,
			 ModelAndView mv){
		//判断是学生还是教师
		if(loginType.equals("2")) {
			mv.addObject("loginname",loginname);
			mv.addObject("password",password);
			mv.setViewName("forward:/student/studentLogin");
			return mv;
		}
		// 调用业务逻辑组件判断用户是否可以登录
		Teacher teacher = teacherService.login(loginname, password);
		if(teacher != null){
			// 将用户保存到HttpSession当中
			session.setAttribute(HomeworkConstants.TEACHER_SESSION, teacher);
			// 客户端跳转到main页面
			mv.setViewName("redirect:/main");
		}else{
			// 设置登录失败提示信息
			mv.addObject("message", "登录名或密码错误!请重新输入");
			// 服务器内部跳转到登录页面
			mv.setViewName("forward:/loginForm");
		}
		return mv;
		
	}
	//添加教师方法
	@RequestMapping(value="/teacher/addTeacher")
	 public ModelAndView addTeacher(
			 String flag,
			 @ModelAttribute Teacher teacher,
			 ModelAndView mv){
		if(flag.equals("1")){
			// 设置跳转到添加页面
			mv.setViewName("teacher/addTeacher");
		}else{
			// 执行添加操作
			teacherService.addTeacher(teacher);
			// 设置客户端跳转到查询请求
			mv.setViewName("redirect:/teacher/selectTeacher");
		}
		// 返回
		return mv;
	}
	//查询教师方法 ，查询条件根据teacher对象中属性的值动态生成SQL语句
	@RequestMapping(value="/teacher/selectTeacher")
	
	 public String selectTeacher(Integer pageIndex,
			 Teacher teacher,
			 Model model){
		PageModel pageModel = new PageModel();
		if(pageIndex != null){
			pageModel.setPageIndex(pageIndex);
		}
		/** 查询用户信息     */
		List<Teacher> teachers = teacherService.findTeacher(teacher, pageModel);
		model.addAttribute("teachers", teachers);
		model.addAttribute("pageModel", pageModel);
		return "teacher/teacher";
		
	}
	//根据id删除老师
	@RequestMapping(value="/teacher/removeTeacher")
	 public ModelAndView removeUser(String ids,
			 ModelAndView mv){
		// 分解id字符串
		String[] idArray = ids.split(",");
		
		for(String id : idArray){
			try {
			// 根据id删除员工
			teacherService.removeTeacherById(Integer.parseInt(id));
			}catch(Exception ep) {
				mv.addObject("error","教师不能删除！");
				mv.setViewName("error/error");
				return mv;
			}
		}
		// 设置客户端跳转到查询请求
		mv.setViewName("redirect:/teacher/selectTeacher");
		// 返回ModelAndView
		return mv;
	}
	//根据teacher对象中属性的值生成动态更新sql语句
	@RequestMapping(value="/teacher/updateTeacher")
	 public ModelAndView updateTeacher(
			 String flag,
			 @ModelAttribute Teacher teacher,
			 ModelAndView mv){
		if(flag.equals("1")){
			// 根据id查询用户
			Teacher target = teacherService.findTeacherById(teacher.getId());
			// 设置Model数据
			mv.addObject("teacher", target);
			// 返回修改员工页面
			mv.setViewName("teacher/updateTeacher");
		}else{
			// 执行修改操作
			teacherService.modifyTeacher(teacher);
			// 设置客户端跳转到查询请求
			mv.setViewName("redirect:/teacher/selectTeacher");
		}
		// 返回
		return mv;
	}
	//注销方法 
	@RequestMapping(value="/logout")
	 public ModelAndView logout(
			 ModelAndView mv,
			 HttpSession session) {
		// 注销session
		session.invalidate();
		// 跳转到登录页面
		mv.setViewName("redirect:/loginForm");
		return mv;
	}
	//修改教师密码
	@RequestMapping(value="/teacher/modifyPassword")
	public ModelAndView modifyPassword(
			String flag,
			 String oldPassword,
			 String password,
			HttpSession session,
			ModelAndView mv) {
		if(flag.equals("1")) {
			
			mv.setViewName("/teacher/modifyPassword");
			
		}
		else
		{ 
			//执行更新
			//从SESSION中得到登录学生的信息
			Teacher teacher=(Teacher)session.getAttribute(TEACHER_SESSION);
			//重新查询Teacher,得到Teacher关联对象的信息
			teacher=teacherService.findTeacherById(teacher.getId());
			if(oldPassword.equals(teacher.getPassword())) {
				teacher.setPassword(password);
				teacherService.modifyTeacher(teacher);
				mv.addObject("message","修改成功！");
				mv.setViewName("success");
			}
			else
			{
				mv.addObject("error","原密码不对！");
				mv.setViewName("error/error");

			}
		}
		
		return mv;
	}

}
