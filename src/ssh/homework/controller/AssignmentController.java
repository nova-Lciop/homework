package ssh.homework.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import ssh.homework.common.HomeworkConstants;
import ssh.homework.domain.Assignment;
import ssh.homework.domain.Course;
import ssh.homework.domain.Exercise;
import ssh.homework.domain.Teacher;
import ssh.homework.domain.Workbook;
import ssh.homework.service.AssignmentService;
import ssh.homework.service.ClazzService;
import ssh.homework.service.CourseService;
import ssh.homework.service.ExerciseService;
import ssh.homework.service.TeacherService;
import ssh.homework.service.WorkbookService;
import ssh.homework.tag.PageModel;
//教师布置作业批改作业 
@Controller
public class AssignmentController {
	@Autowired
	@Qualifier("assignmentService")
	AssignmentService assignmentService;
	
	@Autowired
	@Qualifier("workbookService")
	WorkbookService workbookService;
	
	@Autowired
	@Qualifier("teacherService")
	TeacherService teacherService;
	
	@Autowired
	@Qualifier("exerciseService")
	ExerciseService exerciseService;
	
	@Autowired
	@Qualifier("courseService")
	CourseService courseService;
	
	@Autowired
	@Qualifier("clazzService")
	ClazzService clazzService;
	//存放章的数组
		private static String [] chapters;
		//存放习题类型的数组
		private static Map<String,String>  kinds;
		static{
					kinds=new HashMap<String,String>();
					kinds.put("1", "选择题");
					kinds.put("2","填空题");
					kinds.put("3","简答题");
					
					//初始化chapters
					chapters= new String[20];
					for(int i=0;i<20;i++)chapters[i]=String.valueOf(i+1);
						
					
		}
		//处理布置作业的请求，处理后转到showAssignment.jsp页面
	@RequestMapping(value="/assignment/selectAssignment")
	public String selectAssignment( Integer pageIndex,
			@ModelAttribute("workbook_id") Integer workbook_id,  //当前workbook的id
			String kind,
	        String chapter,
			Model model
			) {
	
		PageModel pageModel=new PageModel();
		pageModel.setPageSize(20);
		if(pageIndex!=null)pageModel.setPageIndex(pageIndex);
		Workbook workbook=workbookService.findWorkbookById(workbook_id);

		Assignment assignment=new Assignment();
		PageModel assignmentPageModel=new PageModel();
		assignmentPageModel.setPageSize(100);
		assignment.setWorkbook(workbook);
	
		//查询当前作业的内容
		List<Assignment> assignments=assignmentService.findAssignment(assignment, assignmentPageModel);
		model.addAttribute("workbook",workbook);
		model.addAttribute("pageModel",pageModel);
		model.addAttribute("workbook_id",workbook.getId());
	//	model.addAttribute("exercises",exercises);
		model.addAttribute("chapters",chapters);
		model.addAttribute("kinds",kinds);
		model.addAttribute("assignments",assignments);
		return "assignment/showAssignment";
	
	}
	//从tb_exercise表中将选中的习题添加到表tb_assignment中
	@RequestMapping(value="/assignment/addAssignment")
	public ModelAndView addAssignment(
			Integer workbook_id,
			String ids,

			ModelAndView mv) {
		
			// 分解id字符串
			String[] idArray = ids.split(","); //idArray中存放的是要添加的习题ID,

			Workbook workbook=workbookService.findWorkbookById(workbook_id);
			for(String id1 : idArray){
				// 根据习题id添加到作业布置表tb_assignment中
				Exercise exercise=exerciseService.findExerciseById(Integer.parseInt(id1));
				//创建一个布置作业的对象
				Assignment assignment=new Assignment();
				assignment.setExercise(exercise);
				assignment.setWorkbook(workbook);
				
				//将封装好的布置作业对象添加到tb_assignment中
				assignmentService.addAssignment(assignment);
			}
			mv.setViewName("redirect:/assignment/selectAssignment");
			mv.addObject("workbook_id",workbook_id);
		return mv;
	}
	@RequestMapping(value="/assignment/delAssignment")
	public ModelAndView delAssignment(
			Integer workbook_id,
			String ids,  //要删除的习题的ID
			String grades, //每个习题的分值
			ModelAndView mv) {
		
			// 分解id字符串
			String[] idArray = ids.split(",");

			for(String id : idArray){
				assignmentService.removeAssignmentById(Integer.parseInt(id));

		}		
			mv.setViewName("redirect:/assignment/selectAssignment");
			mv.addObject("workbook_id",workbook_id);
		return mv;
	}
	//根据在assginment.jsp页面上选择的章和题型对tb_exercise表进行查询
	@RequestMapping(value="/assignment/selectExercise")
		 public String selectExercise(Integer pageIndex,
				 Integer course_id,
				 Integer teacher_id,
				 Integer workbook_id,
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
	
			//查询某门课程还没有布置在作业中的所有习题
			
			/*String isNotIn="not in";
			Workbook workbook=workbookService.findWorkbookById(workbook_id);
			
			List<Exercise> exercises=exerciseService.findByNotInWorkbookIdAndByCourseId(workbook, pageModel,isNotIn);
			//按章和题型进行筛选
			modifyExerciseListForKindAndChapter(exercises,kind,chapter);*/
			//习题中的章
			//List<String> chapters=new ArrayList<String>();

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
			model.addAttribute("workbook_id",workbook_id);
			
			// 返回习题页面
			return "assignment/addExercise";
			
		}

	public void modifyExerciseListForKindAndChapter(
			//List<String> chapters,//存放exercises中的所有不重复章的值
			List<Exercise> exercises,  //处理后的exercises
			String kind, String chapter
			) {
		// 从集合中去掉不与kind相同的元素
		if (kind != null) {
			for (int i=0;i<exercises.size();i++) {
				if (!exercises.get(i).getKind().equals(kind))
					exercises.remove(exercises.get(i--));
			}
		}
		// 从集合中去掉不与chapter相同的元素
		if (chapter != null&&chapter!="0") {
			for (int i=0;i<exercises.size();i++) {
				if (!exercises.get(i).getChapter().equals(chapter))
					exercises.remove(exercises.get(i));
			}
		}
		//将exercises中不重复的章的值存入chapters中
		/*for(Exercise ex:exercises) {
			if(!chapters.contains(ex.getChapter()))
			chapters.add(ex.getChapter());
		}			*/
	}
	
	//更新grade
	@RequestMapping(value="/assignment/udateGrade")
	public ModelAndView updateGrade(
			Integer workbook_id,
			String ids,  //要更新的习题的ID
			String grades, //每个习题的分值
			ModelAndView mv) {
		
			// 分解id字符串
			String[] idArray = ids.split(",");
			String[] igrades = grades.split(",");
			for(int i=0;i<idArray.length;i++){
				String id=idArray[i];
				String grade=igrades[i];
				
				// 根据习题id更新tb_assignment中的grade
				Assignment assignment=assignmentService.findAssignmentById(Integer.parseInt(id));
				assignment.setGrade(Integer.parseInt(grade));
				assignmentService.modifyAssignment(assignment);			
		}		
			mv.setViewName("redirect:/assignment/selectAssignment");
			mv.addObject("workbook_id",workbook_id);
		return mv;
	}
	
	//转发到replaceAssignment.jsp页面
	@RequestMapping(value="/assignment/toReplaceAssignment")
	public String toReplaceAssignment(Integer workbook_id,
			@SessionAttribute(HomeworkConstants.TEACHER_SESSION) Teacher teacher,
			Model model,Integer pageIndex) {
		//得到要复制作业的信息
		Workbook fromWorkbook=workbookService.findWorkbookById(workbook_id);
		Workbook workbook=new Workbook();
		workbook.setTeacher(teacher);		
		PageModel pageModel=new PageModel();
		pageModel.setPageSize(10);
		if(pageIndex!=null)
		pageModel.setPageIndex(pageIndex);
		//根据当前的teacher查询所有的作业
		List<Workbook> workbooks = workbookService.findWorkbook(workbook,pageModel);
		model.addAttribute("workbooks", workbooks);
		model.addAttribute("workbook_id",workbook_id);		
		model.addAttribute("fromWorkbook",fromWorkbook);
		model.addAttribute("pageModel",pageModel);
		return "assignment/replaceAssignment";
	}
	//将replaceAssignment.jsp传递过来的workbook_Id对应的作业复制给所有的workbookIds
	@RequestMapping(value="/assignment/replaceAssignment")
	public ModelAndView replaceWorkbook(
			Integer workbook_id,//源作业id
			String ids,  //要复制的作业ID			
			ModelAndView mv,
			PageModel pageModel) {		
			// 分解id字符串
			String[] idArray = ids.split(",");			
			for(int i=0;i<idArray.length;i++){
				String id=idArray[i];				
				// 对assignment表根据workbook_id对应的作业复制到所有的ids对应的中作业中
				assignmentService.replaceAssignmentsWithWorkbookId(workbook_id, Integer.parseInt(id),pageModel);		}		
			mv.setViewName("redirect:/workbook/selectWorkbook");//转发到显示作业的页面
			mv.addObject("workbook_id",workbook_id);
		return mv;
	}
}
