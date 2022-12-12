package ssh.homework.controller;

import static ssh.homework.common.HomeworkConstants.STUDENT_SESSION;

import java.io.File;
import java.util.Iterator;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ssh.homework.domain.Assignment;
import ssh.homework.domain.Clazz;
import ssh.homework.domain.SaveStudentWorkbook;
import ssh.homework.domain.Student;
import ssh.homework.domain.StudentWorkbook;
import ssh.homework.domain.Workbook;
import ssh.homework.service.AssignmentService;
import ssh.homework.service.ClazzService;
import ssh.homework.service.StudentService;
import ssh.homework.service.StudentWorkbookService;
import ssh.homework.service.UtilService;
import ssh.homework.service.WorkbookService;
import ssh.homework.tag.PageModel;

@Controller
public class StudentWorkbookController {
	
	@Autowired
	@Qualifier(value="studentWorkbookService")
	StudentWorkbookService studentWorkbookService;
	
	@Autowired
	@Qualifier(value="studentService")
	StudentService studentService;
	
	@Autowired
	@Qualifier(value="workbookService")
	WorkbookService workbookService;
	
	@Autowired
	@Qualifier(value="clazzService")
	ClazzService clazzService;
	
	@Autowired
	@Qualifier(value="utilService")
	UtilService utilService;
	
	@Autowired
	@Qualifier(value="assignmentService")
	AssignmentService assignmentService;
	//Student student;
	//显示当前学生的所有作业
	@RequestMapping(value="/studentWorkbook/selectStudentWorkbook")
	public String selectStudentWorkbookByPage(
			Integer pageIndex,
		    String isFinish,  //1表示显示已发布但未批改的作业，2表示已批改的作业
			HttpSession session,
			Model model) {
		//从SESSION中得到登录学生的信息
		Student student=(Student)session.getAttribute(STUDENT_SESSION);
		//重新查询Student,得到Student关联对象的信息
		student=studentService.findStudentById(student.getId());
		//得到 学生所在班级
		Clazz clazz=student.getClazz();
		//查询关联的workbook
		clazz=clazzService.findOneToManyByClazzId(clazz.getId());
	
		Workbook workbook=new Workbook();
		workbook.setClazz(clazz);
		if(isFinish.equals("1"))//发布的作业
		workbook.setWflag("1");
		else
			workbook.setWflag("2");// 在批改的作业

		PageModel pageModel=new PageModel();
		if(pageIndex!=null)pageModel.setPageIndex(pageIndex);

		//得到当前学生的所有作业列表
	
		List<Workbook> workbooks=workbookService.findWorkbook(workbook, pageModel);
		pageModel.setPageSize(workbooks.size());

		model.addAttribute("workbooks",workbooks);
		model.addAttribute("pageModel",pageModel);
		model.addAttribute("isFinish",isFinish);//1表示显示已发布但未批改的作业，2表示已批改的作业
		
		return "studentWorkbook/studentWorkbook";
		
	}
	//学生完成作业
	@RequestMapping(value="/studentWorkbook/doStudentWorkbook")
	public ModelAndView doStudentWorkbook(
			String flag,
			Integer workbook_id,
			@ModelAttribute("isFinish")String isFinish, //1表示显示已发布但未批改的作业，2表示已批改的作业
			@ModelAttribute("saveStudentWork") SaveStudentWorkbook saveStudentWork,
			HttpSession session,
			ModelAndView mv) {
		//提示信息
		Student student=(Student)session.getAttribute(STUDENT_SESSION);
		if(flag.equals("1")) {//进入完成作业的页面
			PageModel pageModel=new PageModel();
			Workbook workbook=workbookService.findWorkbookById(workbook_id);
			pageModel.setPageSize(100);
			Assignment assignment=new Assignment();
			assignment.setWorkbook(workbook);
			//根据workbook,student在student_workbook表中查找学生作业
			List<StudentWorkbook> sw=findStudentWorkbook(workbook,student,pageModel);
			if(sw.size()>0) {//说明studentworkbook表中已经有记录，这个学生已经交过这个作业了，将查询得到的作业内容保存到saveStudentWork中

				saveStudentWork.setStudentWorkbooks(sw);
			}
			else {
			//这个学生是第一次提交作业，根据当前作业查找相应的习题添加到saveStudentWork中
			List<Assignment> assignments=assignmentService.findAssignment(assignment, pageModel);
			for(int i=0;i<assignments.size();i++) {
				StudentWorkbook studentWorkbook=new StudentWorkbook();
				studentWorkbook.setExercise(assignments.get(i).getExercise());
				studentWorkbook.setGrade(assignments.get(i).getGrade());
				studentWorkbook.setWorkbook(workbook);
				studentWorkbook.setStudent(student);
				saveStudentWork.getStudentWorkbooks().add(studentWorkbook);
				
			}
			}
			//对学生作业按习题类别排序
			utilService.sortStudentWorkbook(saveStudentWork.getStudentWorkbooks());

			mv.addObject("saveStudentWork",saveStudentWork);

			mv.addObject("workbook",workbook);
			if(isFinish.equals("1"))
			mv.setViewName("studentWorkbook/doStudentWorkbook");//isFinish为1表示完成作业
			else
				mv.setViewName("studentWorkbook/viewStudentWorkbook");//isFinish为2表示查看作业
			
		}else //如果flag不等于1，执行保存操作，将作业内容提交student_workbook表
		{
		List<StudentWorkbook> studentWorkbooks=saveStudentWork.getStudentWorkbooks();
		Iterator<StudentWorkbook> it=studentWorkbooks.iterator();
		while(it.hasNext()) {
			StudentWorkbook st=it.next();
			//只要不是简答题判断习题答案与学生的答案是否一样
		
			if(st.getExercise().getAnswer().trim().toLowerCase()
					.equals(st.getStudentAnswer().trim().toLowerCase())&&!st.getExercise().getKind().toLowerCase().equals("3")) 
				st.setScore(st.getGrade());    //如果学生回答正确将成绩添加到studentWorkbook的score字段中
			studentWorkbookService.addStudentWorkbook(st);//对student_workbook表执行添加操作
					
		}
			mv.setViewName("redirect:/studentWorkbook/selectStudentWorkbook");
			}
		mv.addObject("isFinish",isFinish);
		
		return mv;
	}
	/**
	 * 处理修改学生密码的请求
	 * @param String flag 标记，1表示跳转到修改页面，2表示执行修改操作
	 * @param String clazz_id 班级ID号
	 * @param Student student  要修改学生的对象
	 * @param ModelAndView mv
	 * */
	@RequestMapping(value="/studentWorkbook/modifyPassword")
	public ModelAndView modifyPassword(
			String flag,
			@ModelAttribute Student student,
			HttpSession session,
			ModelAndView mv) {
		if(flag.equals("1")) {
			//从SESSION中得到登录学生的信息
			student=(Student)session.getAttribute(STUDENT_SESSION);
			//重新查询Student,得到Student关联对象的信息
			student=studentService.findStudentById(student.getId());

			
			mv.addObject("student",student);
			mv.setViewName("/studentWorkbook/modifyPassword");
			
		}
		else
		{ 
			//执行更新
			studentService.modifyStudent(student);
			mv.addObject("isFinish","1");

			mv.setViewName("redirect:/studentWorkbook/selectStudentWorkbook");
		}
		
		return mv;
	}
	
	//学生在作业管理时上传文件
	@RequestMapping(value = "/studentWorkbook/uploadFile")
	public ModelAndView uploadFile(String flag, @ModelAttribute MultipartFile file, Integer id, // Workbook的id
			ModelAndView mv, HttpSession session) throws Exception {
		if (flag.equals("1")) {
			mv.addObject("id", id);// 将Workbook的id保存到request中
			mv.setViewName("studentWorkbook/showStudentUploadFile");
		} else {
			String fileName1 = file.getOriginalFilename();

			// 上传文件路径
			String path = session.getServletContext().getRealPath("/upload/");
			// 从SESSION中得到登录学生的信息
			Student student = (Student) session.getAttribute(STUDENT_SESSION);
			// 重新查询Student,得到Student关联对象的信息
			student = studentService.findStudentById(student.getId());
			Workbook workbook = workbookService.findWorkbookById(id);// 得到当前ID对应workbook对象
			StudentWorkbook studentWorkbook = new StudentWorkbook();
			// 根据作业和学生得到所有的作业中的习题
			List<StudentWorkbook> studentWorkbooks = findStudentWorkbook(workbook, student, new PageModel());
			// 如果studentWorkbooks.size()==0说明此学生还没有提交当前作业，不能上传文件
			if (studentWorkbooks.size() == 0) {
				mv.addObject("error", "没有提交作业前不能上传文件");
				mv.setViewName("error/error");
				return mv;
			}
			// 如果文件有后叕查找后叕,并在文件名后加上后叕否则不加
			String fileName;
			if (fileName1.indexOf(".") > 0) {
				String suffix = fileName1.substring(fileName1.lastIndexOf("."));
				// 上传文件名由作业名称+学生学号+学生姓名组成
				fileName = workbook.getTitle().trim() + student.getLoginname().trim() + student.getUsername().trim() + suffix;

			} else
				fileName = workbook.getTitle().trim() + student.getLoginname().trim() + student.getUsername().trim();

			// 将上传文件保存到一个目标文件当中
			if (file.getSize() > 1048576)// 如果上传文件超出文件大小限制(这里是１m)则返回error.jsp
			{
				mv.addObject("error", "文件上传失败，可能是文件太大（文件大小不超过１Mb）,请将lib中的jar包删除！");
				mv.setViewName("/error/error");
				return mv;
			}
			file.transferTo(new File(path + File.separator + fileName));
			// 根据作业和学生查询更新数据库
			Iterator<StudentWorkbook> it = studentWorkbooks.iterator();
			while (it.hasNext()) {

				studentWorkbook = it.next();
				studentWorkbook.setFileName(fileName);
				// 更新数据库
				studentWorkbookService.modifyStudentWorkbook(studentWorkbook);
			}
			// 返回
			mv.setViewName("redirect:/studentWorkbook/selectStudentWorkbook");
			mv.addObject("isFinish", "1");
		}
		// 返回
		return mv;
	}

	// 学生下载作业文件
	
	@RequestMapping(value = "/studentWorkbook/downloadFile")
	public ResponseEntity<byte[]> downLoad(Integer id, // 作业workbook的ID
			HttpSession session) throws Exception {
		Workbook workbook = workbookService.findWorkbookById(id);// 得到当前ID对应workbook对象
		String fileName =workbook.getFileName();
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
	//根据workbook和student在student_workbook中查找此学生是否有当前的作业
	public List<StudentWorkbook> findStudentWorkbook(Workbook workbook,Student student,PageModel pageModel){
		StudentWorkbook studentWorkbook=new StudentWorkbook();
		studentWorkbook.setStudent(student);
		studentWorkbook.setWorkbook(workbook);
		List<StudentWorkbook> sw=studentWorkbookService.findStudentWorkbook(studentWorkbook, pageModel);
		return sw;
	}

}
