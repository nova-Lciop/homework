package ssh.homework.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ssh.homework.common.HomeworkConstants;
import ssh.homework.domain.Course;
import ssh.homework.domain.Exercise;
import ssh.homework.domain.Teacher;
import ssh.homework.service.CourseService;
import ssh.homework.service.ExerciseService;
import ssh.homework.service.TeacherService;
import ssh.homework.tag.PageModel;

@Controller
public class ExerciseCtroller {
	@Autowired
	@Qualifier("exerciseService")
	ExerciseService exerciseService;
	@Autowired
	@Qualifier("courseService")
	CourseService courseService;
	@Autowired
	@Qualifier("teacherService")
	private TeacherService teacherService;
	//存放章的数组
	private static String [] chapters;
	//存放习题类型的数组
	private static Map<String,String>  kinds;
	static{
		//初始化chapters
				chapters= new String[20];
				for(int i=0;i<20;i++)chapters[i]=String.valueOf(i+1);
				//初始化kinds
				kinds=new HashMap<String,String>();
				kinds.put("1", "选择题");
				kinds.put("2","填空题");
				kinds.put("3","简答题");				
	}
	
	/**
	 * 处理添加学生请求
	 * @param String flag 标记， 1表示跳转到添加页面，2表示执行添加操作
	 * @param Exercise exercise 接收添加参数
	 * @param ModelAndView mv 
	 * */
	@RequestMapping(value="/exercise/addExercise")
	 public ModelAndView addExercise(
			 String flag,
			 HttpSession session,
			 @ModelAttribute Exercise exercise,
			 ModelAndView mv){
		if(flag.equals("1")){
			// 查询教师和课程信息
			List<Course> courses = courseService.findAllCourse();
			List<Teacher> teachers=teacherService.findTeacherAll();
			
			// 设置Model数据
			mv.addObject("courses", courses);
			mv.addObject("teachers",teachers);
			mv.addObject("chapters",chapters);
			mv.addObject("kinds",kinds);
	
			// 返回添加习题页面
			mv.setViewName("exercise/showAddExercise");
		}else{
			// 判断是否有关联对象传递，如果有，创建关联对象
			//this.genericAssociation(course_id,teacher_id, exercise);
			// 添加操作
			Teacher teacher=(Teacher)session.getAttribute(HomeworkConstants.TEACHER_SESSION);
			exercise.setTeacher(teacher);
			exerciseService.addExercise(exercise);
			
			// 设置客户端跳转到查询请求
			mv.setViewName("redirect:/exercise/selectExercise");
		}
		// 返回
		return mv;
		
	}
	
	/**
	 * 处理查询请求
	 * @param pageIndex 请求的是第几页
	 * @param Integer course_id 课程ID
	 * @param Integer teacher_id 教师ID* 
	 * @param exercise 模糊查询参数
	 * @param Model model
	 * */
	//处理习题查询，如果查询条件为空则显示所有习题，否则根据所选条件进行查询
	@RequestMapping(value="/exercise/selectExercise")
	 public String selectExercise(Integer pageIndex,
			 Integer course_id,
			 Integer teacher_id,
			 String kind,
			 String chapter,
			 @ModelAttribute Exercise exercise,
			 Model model){
		
		// 创建分页对象
		PageModel pageModel = new PageModel();
		pageModel.setPageSize(5);
		// 如果参数pageIndex不为null，设置pageIndex，即显示第几页
		if(pageIndex != null){
			pageModel.setPageIndex(pageIndex);
		}
		
		// 查询教师和课程信息
		List<Course> courses = courseService.findAllCourse();
		List<Teacher> teachers=teacherService.findTeacherAll();
		Course course=null;
		Teacher teacher=null;
		//下面的代码是为了保存每次查询的条件，分别是课程、老师姓名、题型、章
		if(exercise.getCourse()!=null&&exercise.getCourse().getId()!=null)
			course_id=exercise.getCourse().getId();
	
		if(exercise.getTeacher()!=null&&exercise.getTeacher().getId()!=null)
			teacher_id=exercise.getTeacher().getId();
		if(course_id!=null) {
			course=courseService.findCourseById(course_id);
			exercise.setCourse(course);		
		}
		if(teacher_id!=null) {
			teacher=teacherService.findTeacherById(teacher_id);
			exercise.setTeacher(teacher);
		}
		if(kind!=null)exercise.setKind(kind);
		if(chapter!=null)exercise.setChapter(chapter);
		// 根据exercise查询习题信息    
		List<Exercise> exercises = exerciseService.findExercise(exercise,pageModel);
		// 设置Model数据

		model.addAttribute("exercises", exercises);

		model.addAttribute("courses", courses);
		model.addAttribute("teachers", teachers);
		model.addAttribute("chapters",chapters);
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("course_id", course_id);
		model.addAttribute("teacher_id", teacher_id);
		model.addAttribute("course", course);
		model.addAttribute("teacher", teacher);
		model.addAttribute("kind",kind);
		model.addAttribute("chapter",chapter);
		
		// 返回习题页面
		return "exercise/exercise";
		
	}
	
	/**
	 * 处理删除学生请求
	 * @param String ids 需要删除的id字符串
	 * @param ModelAndView mv
	 * */
	@RequestMapping(value="/exercise/removeExercise")
	public ModelAndView removeExercise(String ids,ModelAndView mv,HttpSession session) {
		String[] idArray = ids.split(",");
		Teacher teacher=(Teacher)session.getAttribute(HomeworkConstants.TEACHER_SESSION);
		for(String id : idArray){
			try {
			// 根据id删除习题
				Exercise exercise=exerciseService.findExerciseById(Integer.parseInt(id));
				//判断当前要删除的试题是不是当前教师所出
				if(!teacher.getId().equals(exercise.getTeacher().getId()))
				{
					mv.addObject("error","不是自己出的习题不能删除！");
					mv.setViewName("error/error");
					return mv;
				}
				exerciseService.removeExerciseById(Integer.parseInt(id));
			}catch(Exception ep) {
				mv.addObject("error","习题不能删除！");
				mv.setViewName("error/error");
				return mv;
			}
		}
		// 设置客户端跳转到查询请求

		mv.setViewName("redirect:/exercise/selectExercise");
		
		return mv;
		
	}
	/**
	 * 处理修改学生请求
	 * @param String flag 标记，1表示跳转到修改页面，2表示执行修改操作
	 * @param Integer course_id 课程ID
	 * @Param Integer teacher_id 教师ID
	 * @param Exercise exercise  要修改学生的对象
	 * @param ModelAndView mv
	 * */
	@RequestMapping(value="/exercise/updateExercise")
	public ModelAndView updateExercise(
			String flag,
			Integer course_id,
			Integer teacher_id,
			HttpSession session,
			@ModelAttribute Exercise exercise,
			ModelAndView mv) {
		if(flag.equals("1")) {
			//根据ID查找要修改的学生
			Exercise target=exerciseService.findExerciseById(exercise.getId());
			// 查询教师和课程信息
			List<Course> courses = courseService.findAllCourse();
			List<Teacher> teachers=teacherService.findTeacherAll();
			
			mv.addObject("exercise",target);
			mv.addObject("courses",courses);
			mv.addObject("teachers",teachers);
			mv.addObject("chapters",chapters);
			mv.addObject("kinds",kinds);
			mv.setViewName("exercise/showUpdateExercise");
			
		}
		else
		{ 
			Teacher teacher=(Teacher)session.getAttribute(HomeworkConstants.TEACHER_SESSION);
			if(!teacher.getId().equals(teacher_id))
			{
				mv.addObject("error","不是自己出的习题不能修改！");
				mv.setViewName("error/error");
				return mv;
			}
			//封装关联对象

			this.genericAssociation(course_id,teacher_id, exercise);
			//执行更新
			exerciseService.modifyExercise(exercise);
			
			mv.setViewName("redirect:/exercise/selectExercise");
		}
		
		return mv;
	}
	/**
	 *将exercise对象通过couser_id、teacher_id与Course、Teacher对象之间建立关联
	 * */
	private void genericAssociation(Integer course_id,Integer teacher_id,Exercise exercise){
		if(course_id != null){
			Course course=new Course();
			course.setId(course_id);
			exercise.setCourse(course);
		}
		if(teacher_id!=null)
		{
			Teacher teacher=new Teacher();
			teacher.setId(teacher_id);
			
			exercise.setTeacher(teacher);
		}
		
	}
	
}
