package ssh.homework.controller;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import ssh.homework.common.HomeworkConstants;
import ssh.homework.domain.Clazz;
import ssh.homework.domain.Student;
import ssh.homework.service.ClazzService;
import ssh.homework.service.ImportExcelUtilService;
import ssh.homework.service.StudentService;
import ssh.homework.tag.PageModel;

@Controller
public class StudentController {
	@Autowired
	@Qualifier("studentService")
	StudentService studentService;
	@Autowired
	@Qualifier("clazzService")
	ClazzService clazzService;
	
	@Autowired
	@Qualifier("importExcelUtilService")
	ImportExcelUtilService importExcelUtilService;
	//学生登录
	@RequestMapping(value="/student/studentLogin")
	public ModelAndView studentLogin(
			@RequestParam("loginname") String loginname,
			 @RequestParam("password") String password,
			 HttpSession session,
			 ModelAndView mv) {
		Student student=studentService.login(loginname, password);
		if(student!=null) {
			session.setAttribute(HomeworkConstants.STUDENT_SESSION,student);
			mv.setViewName("redirect:/studentMain");
			
		}
		else {
			// 设置登录失败提示信息
			mv.addObject("message", "登录名或密码错误!请重新输入");
			// 服务器内部跳转到登录页面
			mv.setViewName("forward:/loginForm");}
		return mv;
	}
	
	/**
	 * 处理添加学生请求
	 * @param String flag 标记， 1表示跳转到添加页面，2表示执行添加操作
	 * @param String clazz_id 班级ID
	 * @param Student student 接收添加参数
	 * @param ModelAndView mv 
	 * */
	@RequestMapping(value="/student/addStudent")
	 public ModelAndView addStudent(
			 String flag,
			 Integer clazz_id,
			 @ModelAttribute Student student,
			 ModelAndView mv){
		if(flag.equals("1")){
			// 查询班级信息
			List<Clazz> clazzs = clazzService.findAllClazz();
			
			// 设置Model数据
			mv.addObject("clazzs", clazzs);
	
			// 返回添加员工页面
			mv.setViewName("student/showAddStudent");
		}else{
			// 判断是否有关联对象传递，如果有，创建关联对象
			this.genericAssociation(clazz_id, student);
			// 添加操作
			studentService.addStudent(student);
			
			// 设置客户端跳转到查询请求
			mv.setViewName("redirect:/student/selectStudent");
		}
		// 返回
		return mv;
		
	}
	
	/**
	 * 处理查询请求
	 * @param pageIndex 请求的是第几页
	 * @param String clazz_id 班级ID

	 * @param student 模糊查询参数
	 * @param Model model
	 * */
	@RequestMapping(value="/student/selectStudent")
	 public String selectStudent(Integer pageIndex,
			 Integer clazz_id,
			 @ModelAttribute Student student,
			 Model model){
		// 模糊查询时判断是否有关联对象传递，如果有，创建并封装关联对象
		this.genericAssociation(clazz_id, student);
		// 创建分页对象
		PageModel pageModel = new PageModel();
		pageModel.setPageSize(50);
		// 如果参数pageIndex不为null，设置pageIndex，即显示第几页
		if(pageIndex != null){
			pageModel.setPageIndex(pageIndex);
		}
		// 查询班级信息，用于模糊查询
		List<Clazz> clazzs = clazzService.findAllClazz();
	
		// 查询员工信息    
		List<Student> students = studentService.findStudent(student,pageModel);
		// 设置Model数据
		model.addAttribute("students", students);
		model.addAttribute("clazzs", clazzs);
	
		model.addAttribute("pageModel", pageModel);
		// 返回员工页面
		return "student/student";
		
	}
	/**
	 * 处理删除学生请求
	 * @param String ids 需要删除的id字符串
	 * @param ModelAndView mv
	 * */
	@RequestMapping(value="/student/removeStudent")
	public ModelAndView removeStudent(String ids,ModelAndView mv) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			try {
			// 根据id删除学生
			studentService.removeStudentById(Integer.parseInt(id));
			}catch(Exception ep) {
				mv.addObject("error","学生不能删除！");
				mv.setViewName("error/error");
				return mv;
			}
		}
		// 设置客户端跳转到查询请求
		mv.setViewName("redirect:/student/selectStudent");
		
		return mv;
		
	}
	/**
	 * 处理修改学生请求
	 * @param String flag 标记，1表示跳转到修改页面，2表示执行修改操作
	 * @param String clazz_id 班级ID号
	 * @param Student student  要修改学生的对象
	 * @param ModelAndView mv
	 * */
	@RequestMapping(value="/student/updateStudent")
	public ModelAndView updateStudent(
			String flag,
			Integer clazz_id,	
			@ModelAttribute Student student,
			ModelAndView mv) {
		if(flag.equals("1")) {
			//根据ID查找要修改的学生
			Student target=studentService.findStudentById(student.getId());
			//查询所有班级
			List<Clazz> clazzs=clazzService.findAllClazz();

			mv.addObject("student",target);
			mv.addObject("clazzs",clazzs);
			mv.setViewName("student/showUpdateStudent");
			
		}
		else
		{ //封装关联对象
			this.genericAssociation(clazz_id, student);
			//执行更新
			studentService.modifyStudent(student);
			
			mv.setViewName("redirect:/student/selectStudent");
		}
		
		return mv;
	}
	/**
	 * 由于班级在Student中是对象关联映射，
	 * 所以不能直接接收参数，需要创建Clazz对象
	 * */
	private void genericAssociation(Integer clazz_id,Student student){
		if(clazz_id != null){
			Clazz clazz=new Clazz();
			clazz.setId(clazz_id);
			student.setClazz(clazz);
		}
		
	}
	//通过EXCEL导入学生信息
	@RequestMapping(value="/student/leadStudentExcel")
	public  ModelAndView leadStudentExcel(String flag,
			HttpServletRequest request,
			Integer clazz_id,
			ModelAndView mv) throws Exception 
	{
		
		if(flag.equals("1")) {
			// 查询班级信息，用于选择班级
			List<Clazz> clazzs = clazzService.findAllClazz();
			mv.addObject("clazzs",clazzs);
			mv.setViewName("student/leadStudentExcel");
		}
		else 
		{
	     MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
	       
	     InputStream in =null;  
	     List<List<Object>> listob = null;  
	     MultipartFile file = multipartRequest.getFile("upfile");  
	    
	     if(file.isEmpty()){  
	         throw new Exception("文件不存在！");  
	     }  
	     in = file.getInputStream();  
	     listob = importExcelUtilService.getBankListByExcel(in,file.getOriginalFilename());
	   //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出  
	     Clazz clazz=new Clazz();
	     clazz.setId(clazz_id);
	     for (int i = 0; i < listob.size(); i++) {  
	         List<Object> lo = listob.get(i);  
	         if(lo.get(0)==null||lo.get(0).equals("")||lo.get(1)==null||lo.get(1).equals(""))break;
	         Student student=new Student();
	         student.setLoginname((String)lo.get(0));
	         student.setPassword((String)lo.get(0));
	         student.setUsername((String)lo.get(1));
	         student.setClazz(clazz);
	         studentService.addStudent(student);
	     }
		
	     in.close();  
	     mv.setViewName("student/success");
		}
	    return mv;
	}

	}
