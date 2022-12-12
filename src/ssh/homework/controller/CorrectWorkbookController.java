package ssh.homework.controller;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ssh.homework.common.DynamicLoader;
import ssh.homework.common.HomeworkConstants;
import ssh.homework.common.ReadTxt;
import ssh.homework.domain.ExerciseInfo;
import ssh.homework.domain.SaveStudentWorkbook;
import ssh.homework.domain.Student;
import ssh.homework.domain.StudentInfo;
import ssh.homework.domain.StudentWorkbook;
import ssh.homework.domain.Teacher;
import ssh.homework.domain.Workbook;
import ssh.homework.service.ClazzService;
import ssh.homework.service.CourseService;
import ssh.homework.service.ExerciseService;
import ssh.homework.service.SimilarityService;
import ssh.homework.service.StudentService;
import ssh.homework.service.StudentWorkbookService;
import ssh.homework.service.TeacherService;
import ssh.homework.service.UtilService;
import ssh.homework.service.WorkbookService;
import ssh.homework.tag.PageModel;
//处理批改作业请求的控制器
@Controller
public class CorrectWorkbookController {
	@Autowired
	@Qualifier(value="studentWorkbookService")
	StudentWorkbookService studentWorkbookService;
	
	@Autowired
	@Qualifier("workbookService")
	WorkbookService workbookService;
	
	@Autowired
	@Qualifier("similarityService")
	SimilarityService similarityService;
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
	@Qualifier("studentService")
	StudentService studentService;
	
	@Autowired
	@Qualifier(value="utilService")
	UtilService utilService;
	
	@Autowired
	@Qualifier(value="exerciseService")
	ExerciseService exerciseService;
	
	//Teacher teacher;
	//显示当前教师作业列表
	@RequestMapping(value="/correctWorkbook/selectCorrectWorkbook")
	 public String selectWorkbook(Integer pageIndex,
			 String isSimilarity,//如果其值为1进入要批改的作业显示列表，如果其值为2则进入作业查重页面
		     HttpSession session,
			 @ModelAttribute Workbook workbook,
			 Model model){
	
		// 创建分页对象
		Teacher teacher=(Teacher)session.getAttribute(HomeworkConstants.TEACHER_SESSION);
		PageModel pageModel = new PageModel();
		pageModel.setPageSize(40);
		// 如果参数pageIndex不为null，设置pageIndex，即显示第几页
		if(pageIndex != null){
			pageModel.setPageIndex(pageIndex);
		}
		// 查询作业信息    
		workbook.setTeacher(teacher);
		//workbook.setWflag("2");//只查询处于批改状态的作业
		List<Workbook> workbooks = workbookService.findWorkbook(workbook,pageModel);
		// 设置Model数据
		model.addAttribute("workbooks", workbooks);
		model.addAttribute("pageModel", pageModel);
		if(isSimilarity!=null&&isSimilarity.equals("1"))
		// 返回查看作业页面
			return "correctWorkbook/correctWorkbook";
		else
			//返回到查重页面
			return "correctWorkbook/showToSimilarityWorkbook";
			
		
	}
	//根据workbook_id和clazz_id显示当前作业的所有学生作业信息
	
	 @RequestMapping("/correctWorkbook/findStudentByWorkbookId")
     public ModelAndView findStudentByWorkbookId(
    		 Integer clazz_id,
    		 Integer workbook_id,
    		 Integer pageIndex,
    		 ModelAndView mv) { 
    	//根据workbook_id从studentWorkbook表中查找所有做此作业的学生，并将查询结果封装到StudentInfo对象中
    	List<StudentInfo> studentInfos1=studentWorkbookService.findStudentInfoGroupByStudentIdByWorkbookId(workbook_id); 
       //得到当前班的所有学生
    	List<Student> students=studentService.findStudentsByClazzId(clazz_id);
    	//为了能显示当前作业班级的所有学生（不管是否已提交了作业），构造对应的StudentInfo数组
        List<StudentInfo> studentInfos=new ArrayList<StudentInfo>();
        for(int i=0;i<students.size();i++) {
        	int j;
        	for( j=0;j<studentInfos1.size();j++) {
        		StudentInfo stInfo=studentInfos1.get(j);
        		//如果是已做作业的学生就添加到当前列表中
            	if(stInfo.getStudent().getId().equals(students.get(i).getId())) {
            		studentInfos.add(stInfo);
            		j=-1;
             		break;
              	}
            }
        	if(j!=-1) {
        	StudentInfo studentInfo=new StudentInfo();
        	studentInfo.setStudent(students.get(i));
        	studentInfos.add(studentInfo);
        	}
        }
        
    	//对studentInfos按学号进行排序
    	sortStudentInfos(studentInfos);
    	PageModel pageModel=new PageModel();
    	pageModel.setPageSize(studentInfos.size());
    	
    	pageModel.setRecordCount(studentInfos.size());
    	if(pageIndex != null){
			pageModel.setPageIndex(pageIndex);
		}
		Workbook workbook=workbookService.findWorkbookById(workbook_id);
		mv.addObject("pageModel",pageModel);
		mv.addObject("workbook",workbook);
    	mv.addObject("workbook_id",workbook_id);
		mv.addObject("studentInfos",studentInfos);
		mv.setViewName("correctWorkbook/showStudent");
		return mv;
	}
	 
	 //根据workbook_id和student_id从studentworkbook表中删除某一个学生的作业
	 @RequestMapping("/correctWorkbook/removeStudentWorkbook")
	 public ModelAndView removeStudentWorkbook(
			 Integer workbook_id,
			 String ids,
			 ModelAndView mv
			 ) {
		 String student_ids[]=ids.split(",");//得到要删除的学生作业的所有学生的ID
		 for(String student_id:student_ids) {
			 studentWorkbookService.removeStudentWorkbookByWorkbookIdAndStudentId(workbook_id, Integer.parseInt(student_id));
		 }
		 mv.addObject("workbook_id",workbook_id);
	     mv.setViewName("redirect:/correctWorkbook/findStudentByWorkbookId");
		 return mv;
		 
	 }
	 //批改作业，并将结果保存到数据库student_workbook表中
	@RequestMapping("/correctWorkbook/correctWorkbook")
	public ModelAndView correctWorkbook(String flag, // 标识flag为1表示显示批改作业页面，为2表示保存数据到student_workbook表中
			Integer workbook_id, Integer student_id,
			HttpSession session,
			@ModelAttribute("saveStudentWork") SaveStudentWorkbook saveStudentWorkbook, ModelAndView mv) {
		Teacher teacher=(Teacher)session.getAttribute(HomeworkConstants.TEACHER_SESSION);
		String teacherName=teacher.getLoginname();
		// 根据workbook_id和student_id从studentWorkbook表中查找所有做此作业的学生，并将查询结果封装到StudentInfo对象中
		Student student = studentService.findStudentById(student_id);
		if (flag.equals("1")) {
			Workbook workbook = workbookService.findWorkbookById(workbook_id);
			
			// 根据workbook_id和student_id查询StudentWorkbook
			List<StudentWorkbook> studentWorkbooks = findStudentWorkbookByWorkbookIdAndStudentId(workbook_id,
					student_id);
			utilService.sortStudentWorkbook(studentWorkbooks);// 对学生作业按习题类别排序
			// 运行学生的编程程序，并将运行结果存入学生编程程序的最后面
//			for (int i = 0; i < studentWorkbooks.size(); i++) {
//				StudentWorkbook st = studentWorkbooks.get(i);
//				if (st.getExercise().getKind().equals("3")) {
//					String runResult1 = compileSrc(st.getStudentAnswer(), teacherName);// 编译运行学生的程序
//					runResult1 = st.getStudentAnswer() + "程序运行结果：" + runResult1;
//					st.setStudentAnswer(runResult1);// 将程序运行的结果添加到学生程序的后面
//					studentWorkbooks.set(i, st);// 更新当前元素
//				}
//			}
			saveStudentWorkbook.setStudentWorkbooks(studentWorkbooks);
			mv.addObject("workbook", workbook);
			mv.addObject("student", student);
			mv.addObject("workbook_id", workbook_id);
			mv.addObject("student_id", student_id);
			mv.addObject("saveStudentWorkbook", saveStudentWorkbook);
			mv.setViewName("correctWorkbook/correctStudentWorkbook");
		} else {
			// 得到批改作业前的studentWorkbook列表
			List<StudentWorkbook> studentWorkbooks = findStudentWorkbookByWorkbookIdAndStudentId(workbook_id,
					student_id);
			Iterator<StudentWorkbook> it = studentWorkbooks.iterator();
			// 得到批改作业后的studentWorkbook列表
			Iterator<StudentWorkbook> it1 = saveStudentWorkbook.getStudentWorkbooks().iterator();
			// 只对批改作业前的studentWorkbook列表中的grade和notes属性进行更新，其余属性不变

			while (it.hasNext()) {
				StudentWorkbook sw1 = it.next();
				StudentWorkbook sw2 = it1.next();
				sw1.setScore(sw2.getScore());
				sw1.setNotes(sw2.getNotes());
			}
			// 对studentWorkbook表进行更新
			it = studentWorkbooks.iterator();
			StudentWorkbook studentWorkbook;
			while (it.hasNext()) {
				studentWorkbook = it.next();
				studentWorkbookService.addStudentWorkbook(studentWorkbook);
				workbook_id = studentWorkbook.getWorkbook().getId();
			}
			mv.addObject("clazz_id",student.getClazz().getId());
			mv.addObject("workbook_id", workbook_id);
			mv.setViewName("redirect:/correctWorkbook/findStudentByWorkbookId");
		}
		return mv;
	}
	//查询学生作业代码是否有抄袭
		
		public ModelAndView queryRep(
				String flag,
				@ModelAttribute Student student,
				HttpSession session,
				ModelAndView mv) {
			
			
			return null;
		}
		
		//下载学生作业文件
		@RequestMapping(value = "/correctWorkbook/downloadFile")
		public ResponseEntity<byte[]> downLoad(Integer workbook_id, // 作业workbook的ID
				Integer student_id,  //学生student的ID
				HttpSession session) throws Exception {
			//得到workbook对象
			Workbook workbook = workbookService.findWorkbookById(workbook_id);// 得到当前ID对应workbook对象
			//得到 Student对象
			Student student=studentService.findStudentById(student_id);
			//创建StudentWorkbook对象，将workbook和student对象SET进StudentWorkbook对象
			StudentWorkbook studentWorkbook=new StudentWorkbook();
			studentWorkbook.setStudent(student);
			studentWorkbook.setWorkbook(workbook);
			//根据StudentWorkbook对象进行查询
			List<StudentWorkbook> studentWorkbooks=studentWorkbookService.findStudentWorkbook(studentWorkbook, new PageModel());
			Iterator<StudentWorkbook> it=studentWorkbooks.iterator();
			String fileName=new String();
			if(it.hasNext())
			    fileName =it.next().getFileName();
			// 下载文件路径
			String path = session.getServletContext().getRealPath(
	                "/upload/");
		
			File file = new File(path + File.separator + fileName);
			// 创建springframework的HttpHeaders对象
			HttpHeaders headers = new HttpHeaders();
			// 下载显示的文件名，解决中文名称乱码问题
			String downloadFielName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			// 通知浏览器以attachment（下载方式）打开图片
			headers.setContentDispositionFormData("attachment", downloadFielName);
			// application/octet-stream ： 二进制流数据（最常见的文件下载）。
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			// 201 HttpStatus.CREATED
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		}
		//根据选中的班级对学生作业进行查重
		@RequestMapping(value="/correctWorkbook/runSimilarity")
		public ModelAndView runSimilarity(String ids,//存放要查重的班级ID
				float checkRate,
			ModelAndView mv) {
			String[] idArray = ids.split(",");//得到所选要查重的作业的workbook的id
			//将所选的班级所有学生作业信息放到下面的数组中
			List<StudentWorkbook> studentWorkbooks=new ArrayList<StudentWorkbook>();
		for (int i = 0; i < idArray.length; i++) {
			Workbook workbook=new Workbook();
			workbook.setId(Integer.parseInt(idArray[i]));

			StudentWorkbook studentWorkbook = new StudentWorkbook();
			studentWorkbook.setWorkbook(workbook);
			PageModel pageModel=new PageModel();
			pageModel.setPageSize(1000);
			List<StudentWorkbook> sw = studentWorkbookService.findStudentWorkbook(studentWorkbook,pageModel);
			studentWorkbooks.addAll(sw);
		}
		//对所选学生进行查重并更新student_workbook
		similarityService.studentWorkbookSimilarity(studentWorkbooks,checkRate);
		
		mv.addObject("isSimilarity", "2");
		mv.setViewName("redirect:/correctWorkbook/selectCorrectWorkbook");

		return mv;
		}
	    //查询学生作业习题错误的情况
	    @RequestMapping(value="/correctWorkbook/findExerciseInfoByWorkbookId")    	
	    public ModelAndView findExerciseInfoByWorkbookId(Integer workbook_id,
	    		ModelAndView mv) { 
	    	Workbook workbook=workbookService.findWorkbookById(workbook_id);
	    	List<ExerciseInfo> exerciseInfos=exerciseService.findExerciseInfoByWorkbookId(workbook_id);
	    	mv.addObject("exerciseInfos",exerciseInfos);
	    	mv.addObject("workbook",workbook);
	    	mv.setViewName("/correctWorkbook/showWorkbookErrorCount");
	    	return mv;
	    }

		
		//根据workbook_id和student_id查找studentWorkbook
		public List<StudentWorkbook> findStudentWorkbookByWorkbookIdAndStudentId(Integer workbook_id,Integer student_id){
			
			 StudentWorkbook studentWorkbook=new StudentWorkbook();
				
			 Workbook workbook=workbookService.findWorkbookById(workbook_id);
				Student student=studentService.findStudentById(student_id);
				studentWorkbook.setStudent(student);
				studentWorkbook.setWorkbook(workbook);
				if(workbook==null||student==null)return null;
				PageModel pageModel=new PageModel();
				pageModel.setPageSize(50);
		    	List<StudentWorkbook> studentWorkbooks=studentWorkbookService.
		    			findStudentWorkbook(studentWorkbook,pageModel); 
		    	return studentWorkbooks;
		}
		//对查询的student_workbook根据student_loginname（学号）进行排序
	    public void sortStudentInfos(List<StudentInfo> studentInfos) {
	    	 Collections.sort(studentInfos, new Comparator<StudentInfo>(){
	    /*
	              * int compare(StudentInfo p1, StudentInfo p2) 返回一个基本类型的整型，
	              * 返回负数表示：p1 小于p2，
	              * 返回0 表示：p1和p2相等，
	              * 返回正数表示：p1大于p2
	              */
	             public int compare(StudentInfo p1, StudentInfo p2) {
	                 //按照Person的年龄进行升序排列
	                 if(p1.getStudent().getLoginname().compareTo( p2.getStudent().getLoginname())>0){
	                     return 1;
	                 }
	                 if(p1.getStudent().getLoginname().compareTo( p2.getStudent().getLoginname())==0){
	                     return 0;
	                 }
	                 return -1;
	             }

				
	         });
	    }
	/**在线编译运行程序
 * javaSrc是要编译的程序源码
 * fileName是存放程序运行结果的文件
	**/
	@SuppressWarnings({ "resource", "finally" })
	public String compileSrc(String javaSrc,String fileName)
			{
		String result="error";
		DynamicLoader.MemoryClassLoader classLoader;
		@SuppressWarnings("unused")
		Object object;

		Pattern pattern = Pattern.compile("public\\s+class\\s+(\\w+)");
		Matcher matcher = pattern.matcher(javaSrc);
		String className;
		if (!matcher.find())
			return result;
		className = matcher.group(1);
		Map<String, byte[]> bytecode = DynamicLoader.compile(className + ".java", javaSrc);
		
try {
		classLoader = new DynamicLoader.MemoryClassLoader(bytecode);
		Class<?> clazz = classLoader.loadClass(className);
		object = clazz.newInstance();
		Method method = clazz.getMethod("main", String[].class);
		//将程序运行结果存入文件中
		System.setOut(new PrintStream(new File(fileName+".txt")));//将运行结果写入文件中
		method.invoke(null, (Object) new String[] {});
		result=ReadTxt.readTxtFile(fileName+".txt");//从文件中读出程序运行结果
}catch(Exception e) {
	result="error";

}finally{
	
	return result;//返回文件内容
}
		//返回文件内容


			
			
	    }
}
