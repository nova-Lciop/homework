package ssh.homework.service;

import java.util.List;

import ssh.homework.domain.StudentInfo;
import ssh.homework.domain.StudentWorkbook;
import ssh.homework.tag.PageModel;
//处理学生作业相关业务逻辑服务接口
public interface StudentWorkbookService {
	/**
	 * 根据id查询学生作业
	 * @param id
	 * @return 学生作业对象
	 * */
	StudentWorkbook findStudentWorkbookById(Integer id);
	
	/**
	 * 获得所有学生作业
	 * @return User对象的List集合
	 * */
	List<StudentWorkbook> findStudentWorkbook(StudentWorkbook studentWorkbook,PageModel pageModel);
	
	/**
	 * 根据id删除学生作业
	 * @param id
	 * */
	void removeStudentWorkbookById(Integer id);
	
	/**
	 * 根据workbook_id和student_id删除学生作业
	 * @param workbook_id
	 * */
	void removeStudentWorkbookByWorkbookIdAndStudentId(Integer workbook_id,Integer student_id);
	/**
	 * 修改学生作业
	 * @param StudentWorkbook 学生作业对象
	 * */
	void modifyStudentWorkbook(StudentWorkbook studentWorkbook);
	
	/**
	 * 添加学生作业
	 * @param StudentWorkbook 学生作业对象
	 * */
	void addStudentWorkbook(StudentWorkbook studentWorkbook);
	//	
	/**
	 * 根据workbook_id按student_id统计每学生的成绩，最大查重率和习题数
	 * @param workbook_id
	 * @return StudentInfo对象的集合List
	 * */
	List<StudentInfo> findStudentInfoGroupByStudentIdByWorkbookId(Integer workbook_id);
	
}
