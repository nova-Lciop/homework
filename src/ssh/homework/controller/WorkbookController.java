package ssh.homework.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ssh.homework.common.HomeworkConstants;
import ssh.homework.domain.Assignment;
import ssh.homework.domain.Clazz;
import ssh.homework.domain.Course;
import ssh.homework.domain.Exercise;
import ssh.homework.domain.Student;
import ssh.homework.domain.StudentInfo;
import ssh.homework.domain.StudentScore;
import ssh.homework.domain.Teacher;
import ssh.homework.domain.Workbook;
import ssh.homework.service.AssignmentService;
import ssh.homework.service.ClazzService;
import ssh.homework.service.CourseService;
import ssh.homework.service.ExcelService;
import ssh.homework.service.ExerciseService;
import ssh.homework.service.StudentService;
import ssh.homework.service.StudentWorkbookService;
import ssh.homework.service.TeacherService;
import ssh.homework.service.WorkbookService;
import ssh.homework.tag.PageModel;

@Controller
public class WorkbookController {
	@Autowired
	@Qualifier("workbookService")
	WorkbookService workbookService;
	@Autowired
	@Qualifier("clazzService")
	ClazzService clazzService;
	@Autowired
	@Qualifier("teacherService")
	TeacherService teacherService;
	@Autowired
	@Qualifier("courseService")
	CourseService courseService;
	@Autowired
	@Qualifier("assignmentService")
	AssignmentService assignmentService;
	@Autowired
	@Qualifier("excelService")
	ExcelService excelService;	
	@Autowired
	@Qualifier("exerciseService")
	ExerciseService exerciseService;
	@Autowired
	@Qualifier("studentService")
	StudentService studentService;
	@Autowired
	@Qualifier(value="studentWorkbookService")
	StudentWorkbookService studentWorkbookService;
	//存放作业状态的map
		private static Map<String,String> wflags;
		private static List<String> terms;
		static {
			wflags=new HashMap<String,String>();
			wflags.put("0", "未发布");
			wflags.put("1", "发布");
			wflags.put("2", "批改");
			terms=new ArrayList<String>();
			terms.add("2019-2020-1");
			terms.add("2019-2020-2");
			terms.add("2020-2021-1");
			terms.add("2020-2021-2");
			terms.add("2021-2022-1");
			terms.add("2021-2022-2");
			terms.add("2022-2023-1");
			terms.add("2022-2023-2");
			terms.add("2023-2024-1");
			terms.add("2023-2024-2");
		}
		
	/**
	 * 处理添加作业请求
	 * @param String flag 标记， 1表示跳转到添加页面，2表示执行添加操作
	
	 * @param Workbook workbook 接收添加参数
	 * @param ModelAndView mv 
	 * */
	//添加作业
	@RequestMapping(value="/workbook/addWorkbook")
	 public ModelAndView addWorkbook(
			 String flag,
			 @ModelAttribute Workbook workbook,
			 HttpSession session,
			 ModelAndView mv){
		if(flag.equals("1")){
			// 查询班级信息
			List<Clazz> clazzs = clazzService.findAllClazz();
			
			List<Course>  courses=courseService.findAllCourse();
			
			// 设置Model数据
			mv.addObject("clazzs", clazzs);
			
			mv.addObject("courses", courses);
			
	
			// 返回添加作业页面
			mv.setViewName("workbook/showAddWorkbook");
		}else{
			// 添加操作
			Teacher teacher=(Teacher)session.getAttribute(HomeworkConstants.TEACHER_SESSION);
			workbook.setTeacher(teacher);
			workbook.setCreateDate(new Date());
			workbookService.addWorkbook(workbook);
			
			// 设置客户端跳转到查询请求
			mv.setViewName("redirect:/workbook/selectWorkbook");
		}
		mv.addObject("terms",terms);
		// 返回
		return mv;
		
	}
	
	/**
	 * 处理查询请求
	 * @param pageIndex 请求的是第几页
	 * @param workbook 模糊查询参数
	 * @param Model model
	 * @return String
	 * */
	@RequestMapping(value="/workbook/selectWorkbook")
	 public String selectWorkbook(Integer pageIndex,
		     HttpSession session,
			 @ModelAttribute Workbook workbook,
			 PageModel pageModel,
			 Model model){
	
		// 创建分页对象
		Teacher teacher=(Teacher)session.getAttribute(HomeworkConstants.TEACHER_SESSION);
		pageModel.setPageSize(10);
		// 如果参数pageIndex不为null，设置pageIndex，即显示第几页
		if(pageIndex != null){
			pageModel.setPageIndex(pageIndex);
		}
		// 查询班级信息，用于模糊查询
		List<Clazz> clazzs = clazzService.findAllClazz();
		List<Teacher> teachers=new ArrayList<Teacher>();
		
		List<Course>  courses=courseService.findAllCourse();
		//只有系统管理员查看所有作业，其他人只能查看自己作业，
		if(!teacher.getRole().equals("1")) {
			teachers.add(teacher);
			workbook.setTeacher(teacher);
		}
		else
			teachers=teacherService.findTeacherAll();
			
		// 查询作业信息    
		
		List<Workbook> workbooks = workbookService.findWorkbook(workbook,pageModel);
		// 设置Model数据
		model.addAttribute("workbooks", workbooks);
		model.addAttribute("clazzs", clazzs);
		model.addAttribute("teachers", teachers);
		model.addAttribute("courses", courses);
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("terms",terms);
		model.addAttribute("workbook",workbook);
		model.addAttribute("wflags",wflags);
		// 返回作业页面
		return "workbook/workbook";
		
	}
	/**
	 * 处理删除作业请求
	 * @param String ids 需要删除的id字符串
	 * @param ModelAndView mv
	 * */
	@RequestMapping(value="/workbook/removeWorkbook")
	public ModelAndView removeWorkbook(String ids,ModelAndView mv) {
		String[] idArray = ids.split(",");
		for(String id : idArray){
			try {
			// 根据id删除作业
			workbookService.removeWorkbookById(Integer.parseInt(id));
			}catch(Exception ep) {
				mv.addObject("error","作业不能删除！");
				mv.setViewName("error/error");
				return mv;
			}
		}
		// 设置客户端跳转到查询请求
		mv.setViewName("redirect:/workbook/selectWorkbook");
		
		return mv;
		
	}
	/**
	 * 处理修改作业请求
	 * @param String flag 标记，1表示跳转到修改页面，2表示执行修改操作
	 * */
	
	@RequestMapping(value="/workbook/updateWorkbook")
	public ModelAndView updateWorkbook(
			String flag,
			HttpSession session,
			@ModelAttribute Workbook workbook,
			ModelAndView mv) {
		mv.addObject("terms",terms);
		if(flag.equals("1")) {
			//根据ID查找要修改的学生
			Workbook target=workbookService.findWorkbookById(workbook.getId());
			//查询所有班级
			List<Clazz> clazzs = clazzService.findAllClazz();
			//List<Teacher> teachers=teacherService.findTeacherAll();
			List<Course>  courses=courseService.findAllCourse();

			mv.addObject("workbook",target);
			mv.addObject("clazzs", clazzs);
			//mv.addObject("teachers", teachers);
			mv.addObject("courses", courses);
			mv.addObject("wflags",wflags);
			mv.setViewName("workbook/showUpdateWorkbook");
			
		}
		else
		{ 
			Teacher teacher=(Teacher)session.getAttribute(HomeworkConstants.TEACHER_SESSION);
			workbook.setTeacher(teacher);
			Assignment assignment=new Assignment();
			assignment.setWorkbook(workbook);
			List<Assignment> assignments=assignmentService.findAssignment(assignment,new PageModel());
			Iterator<Assignment> it=assignments.iterator();
			//如果修改作业状态为“发布”，要判断作业题是否有分值
			if(workbook.getWflag().equals("1")) {
		//判断已存在的作业中的习题是否已经有了分值 
			if(assignments.size()<=0) {
				mv.addObject("error","没有为作业题布置习题！")	;				
				mv.setViewName("/error/error");
				return mv;
			}
			
			
			while(it.hasNext()) {//判断作业题是否有分值
				if(it.next().getGrade()==0) {
					mv.addObject("error","作业题没有分值，请给每个题提供分值")	;				
					mv.setViewName("/error/error");
					return mv;
				}
			}
			
			}
			workbookService.modifyWorkbook(workbook);
			
			mv.setViewName("redirect:/workbook/selectWorkbook");
			
		}
		
		return mv;
	}
//教师在作业管理时上传文件
	@RequestMapping(value="/workbook/uploadFile")
	 public ModelAndView uploadFile(
			 String flag,
			 @ModelAttribute MultipartFile file,
			 Integer id, //workbook的id
			 ModelAndView mv,
			 HttpSession session)throws Exception{
		if(flag.equals("1")){
			mv.addObject("id",id);//将workbook的id保存到request中
			mv.setViewName("workbook/showUploadFile");
		}else{
			// 上传文件路径
			String path = session.getServletContext().getRealPath(
	                "/upload/");
			System.out.println(path);
			// 上传文件名
			String fileName =file.getOriginalFilename();
//			if(file.getSize()>10485760)//如果上传文件超出文件大小限制(10m)则返回error.jsp
//			{
//				mv.addObject("error","文件上传失败，可能是文件太大（文件大小不超过１Mb）,请将lib中的jar包删除！");
//				mv.setViewName("/error/error");
//				return mv;
//			}	
				 // 将上传文件保存到一个目标文件当中
				file.transferTo(new File(path+File.separator+ fileName));					
			// 插入数据库
			Workbook workbook=workbookService.findWorkbookById(id);
			workbook.setFileName(fileName);
				// 插入数据库
			workbookService.modifyWorkbook(workbook);
			// 返回
			mv.setViewName("redirect:/workbook/selectWorkbook");
		}
		// 返回
		return mv;
	}
	//将作业输出到EXCEL文件
	@RequestMapping(value="/workbook/exportExcel")
	public @ResponseBody String exportExcel(HttpServletResponse response,
			 Integer id,
			ModelAndView mv)
	{
	     response.setContentType("application/binary;charset=UTF-8");
	     Workbook workbook=workbookService.findWorkbookById(id);
	     
	              try{
	                  ServletOutputStream out=response.getOutputStream();
	                  try {
	                      //设置文件头：最后一个参数是设置下载文件名
	                      response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(workbook.getTitle()+".xls", "UTF-8"));
	                  } catch (UnsupportedEncodingException e1) {
	                      e1.printStackTrace();
	                     // mv.setViewName("redirect:/error/error");
		                  return "error";
	                  }
	                  String isNotIn="in";
	                  //从tb_exercise表中查找当前作业的习题
	                  PageModel pagemodel=new PageModel();
	                  pagemodel.setPageSize(50);
	                  List<Exercise> exercises=exerciseService.findByNotInWorkbookIdAndByCourseId(workbook,  pagemodel, isNotIn);
	                  //将exercises输出到EXCEL表中
	                  excelService.export(exercises, out);      
	                  //mv.setViewName("redirect:/workbook/selectWorkbook");
	                  return "success";
	              } catch(Exception e){
	                  e.printStackTrace();
	                 // mv.setViewName("redirect:/error/error");
	                  return "error";
	              }
	              
	          }
	
	//显示生成作业成绩的页面
	@RequestMapping(value="/workbook/showScoreSelect")
	public ModelAndView showScoreSelect(ModelAndView mv,
			@SessionAttribute(HomeworkConstants.TEACHER_SESSION) Teacher teacher) {
		Set<Clazz> clazzs=new HashSet<Clazz>();//当前教师所教的班级
		Set<Course> courses=new HashSet<Course>();//当前教师所教的课程
		Set<String> terms=new HashSet<String>();//当前教师所涉及的学期
		initialize(clazzs, courses, terms, teacher);
		mv.addObject("clazzs",clazzs);
		mv.addObject("courses",courses);
		mv.addObject("terms",terms);
		mv.setViewName("workbook/searchStudentWorkbookScore");
		return mv;
		
	}

	// 将某班当前学期作业成绩输出到浏览器
	@RequestMapping(value = "/workbook/searchStudentScore")
	public ModelAndView searchStudentScore(HttpServletResponse response, 
			@RequestParam Integer clazz_id, @RequestParam Integer course_id, @RequestParam String term,
			@SessionAttribute(HomeworkConstants.TEACHER_SESSION) Teacher teacher, ModelAndView mv) {
		response.setContentType("application/binary;charset=UTF-8");
		Course course=courseService.findCourseById(course_id);
	    Clazz clazz=clazzService.findClazzById(clazz_id);
	    String workbookInfo=clazz.getCname()+course.getCname()+"作业成绩";
		// 从student_workbook表中查找当前班级所学生当前课程作业成绩并添入map中
		Map<Integer, StudentScore> map = addWorkbookSearch(teacher, clazz_id, course_id, term);
		Collection<StudentScore> studentScores=map.values();
		Iterator<StudentScore> it=studentScores.iterator();
		int scoreCount=0;
		if(it.hasNext()) {
			scoreCount=it.next().getScores().size();//作业数
		}
		String scoreTitles[]=new String[scoreCount];//列表表头
		int i;
		for(i=0;i<scoreCount-1;i++)scoreTitles[i]="作业"+(i+1);
		scoreTitles[i]="平均成绩";
		Set<Clazz> clazzs=new HashSet<Clazz>();//当前教师所教的班级
		Set<Course> courses=new HashSet<Course>();//当前教师所教的课程
		Set<String> terms=new HashSet<String>();//当前教师所涉及的学期
		initialize(clazzs, courses, terms, teacher);
		mv.addObject("clazzs",clazzs);
		mv.addObject("courses",courses);
		mv.addObject("terms",terms);
		mv.addObject("clazz",clazz);
		mv.addObject("course",course);
		mv.addObject("term",term);
		mv.addObject("scoreTitles",scoreTitles);
		mv.addObject("workbookInfo",workbookInfo);
		mv.addObject("studentScores",studentScores);
		
		mv.setViewName("workbook/searchStudentWorkbookScore");
		return mv;

	}

	//将某班当前学期作业成绩输出到EXCEL文件
		@RequestMapping(value="/workbook/exportToScoreExcel")
		public @ResponseBody String exportScoreToExcel(HttpServletResponse response,
				 Integer clazz_id,Integer course_id,String term,
				 @SessionAttribute(HomeworkConstants.TEACHER_SESSION) Teacher teacher,
				ModelAndView mv)
		{
		     response.setContentType("application/binary;charset=UTF-8");
		     Course course=courseService.findCourseById(course_id);
		     Clazz clazz=clazzService.findClazzById(clazz_id);
		     String workbookInfo=clazz.getCname()+course.getCname()+"作业成绩";
		     
		              try{
		                  ServletOutputStream out=response.getOutputStream();
		                  try {
		                      //设置文件头：最后一个参数是设置下载文件名
		                      response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(workbookInfo+".xls", "UTF-8"));
		                  } catch (UnsupportedEncodingException e1) {
		                      e1.printStackTrace();
		                     // mv.setViewName("redirect:/error/error");
			                  return "error";
		                  }
		                  //从student_workbook表中查找当前班级所学生当前课程作业成绩并添入map中
		                 
		                  Map<Integer,StudentScore> map=addWorkbookSearch(teacher,clazz_id,course_id,term);
		                  //将map输出到EXCEL表中
		                  excelService.export(map, out,workbookInfo);      
		                  //mv.setViewName("redirect:/workbook/selectWorkbook");
		                  return "success";
		              } catch(Exception e){
		                  e.printStackTrace();
		                 // mv.setViewName("redirect:/error/error");
		                  return "error";
		              }
		              
		          }
		//根据查询条件得到学生成绩的MAP
		public Map<Integer,StudentScore> addWorkbookSearch(Teacher teacher,Integer clazz_id,Integer course_id,String term) {
			//查询当前教师、所选班级、所选课程和所选学期对应的workbook
			List<Workbook> workbooks=workbookService.findWorkbookByCourseIdAndClazzIdAndWflagAndTerm(
					teacher.getId(), course_id, clazz_id, term);
			Iterator<Workbook> it=workbooks.iterator();
			//map的键是学生的ID，值是学生每次作业的成绩　
			Map<Integer,StudentScore> studentScores=new HashMap<Integer,StudentScore>();
			//所选班级所有学生
			List<Student> students=studentService.findStudentsByClazzId(clazz_id);
			Iterator<Student> stu_it=students.iterator();
			//利用students对studentScores进行初始化
			while(stu_it.hasNext()) {
				Student student=stu_it.next();
				List<Float> scores=new ArrayList<Float>();//某个学生所有作业的成绩
				StudentScore studentScore=new StudentScore();
				studentScore.setStudent(student);
				studentScore.setScores(scores);
				studentScores.put(student.getId(), studentScore);
			}
			//遍历workbooks
			while(it.hasNext()) {
				Integer id=it.next().getId();//作业ID
				studentWorkbookScoreToMap(id,studentScores);//每个作业成绩添加map中
				
			}countAvg(studentScores);//计算每个学生的平均成绩
			return studentScores;	
			
		}
		//将workbook_id对应的作业成绩写到studentScores中
		public void studentWorkbookScoreToMap(Integer workbook_id,Map<Integer,StudentScore> studentScores) {
	    	//根据workbook_id从studentWorkbook表中查找所有做此作业的学生，并将查询结果封装到StudentInfo对象中
	    	List<StudentInfo> studentInfos=studentWorkbookService.findStudentInfoGroupByStudentIdByWorkbookId(workbook_id); 
	    	Set<Integer> set=studentScores.keySet();
	    	Iterator<Integer> it=set.iterator();
	    	//对studentScores进行遍历
	    	while(it.hasNext()) {
	    		Integer studentId=it.next();
   				List<Float> list=studentScores.get(studentId).getScores();//得到当前学生作业成绩列表
	    		Iterator<StudentInfo> sit=studentInfos.iterator();
	    		StudentScore studentScore=new StudentScore();
    			int flag=0;//标识学生是否有作业成绩
	    		//对studentInfos进行遍历查找当前student对应的成绩如果找到将当前作业成绩添加入studentScores中
	    		while(sit.hasNext()) {
	    			StudentInfo studentInfo=sit.next();

	    			if(studentInfo.getStudent().getId().equals(studentId)) {//找到当前学生对应的成绩添加到当前学生的成绩列表中
	 	    				list.add(studentInfo.getScore());
	 	    				studentScore.setStudent(studentInfo.getStudent());
	 	    				studentScore.setScores(list);
	    				flag=1;
	    				break;
	    			}
	    			
	    		}
	    		if(flag==0) {//该生当前作业没有成绩将成绩０添入
    				list.add(0.0f);	    				
    				Student student=studentService.findStudentById(studentId);//此学生没有完成这次作业，重新查询学生信息
    				studentScore.setStudent(student);
    				studentScore.setScores(list);
			}
			
			studentScores.put(studentId, studentScore);// 将成绩写入map
	    	}
		}
		//计算studentScores中每个学生的平均成绩
		public void countAvg(Map<Integer,StudentScore> studentScores) {
			Set<Integer> set=studentScores.keySet();
	    	Iterator<Integer> it=set.iterator();
	    	while(it.hasNext()) {
	    		Integer studentId=it.next();
   				List<Float> list=studentScores.get(studentId).getScores();//得到当前学生作业成绩列表
			float sum = 0;
			int count = list.size();
			for (int n = 0; n < count; n++) {
				sum += list.get(n);// 每个学生所有作业成绩之和
			}
			float x=(int)Math.ceil(sum / count);//四舍五入取整
			list.add(x);// 学生作业平均成绩添加到list 中
			Student student=studentService.findStudentById(studentId);
			StudentScore studentScore=new StudentScore();//构造StudentScore
			studentScore.setStudent(student);
			studentScore.setScores(list);
			// 将更新后的studentScore重新写studentScores中
			studentScores.put(studentId, studentScore);
	    	}
		}
		//初始化clazzs,courses,terms
		public void initialize(Set<Clazz> clazzs,Set<Course> courses,Set<String> terms,Teacher teacher) {
			Workbook workbook=new Workbook();
			workbook.setTeacher(teacher);
			PageModel pageModel=new PageModel();
			pageModel.setPageSize(100);
			List<Workbook> workbooks=workbookService.findWorkbook(workbook, pageModel);
			Iterator<Workbook> it=workbooks.iterator();
			while(it.hasNext()) {
				Workbook wb=it.next();
				clazzs.add(wb.getClazz());
				courses.add(wb.getCourse());
				terms.add(wb.getTerm());
			}
		}


}
