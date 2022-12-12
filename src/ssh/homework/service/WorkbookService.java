package ssh.homework.service;

import java.util.List;

import ssh.homework.domain.Workbook;
import ssh.homework.tag.PageModel;

public interface WorkbookService {
	/**
	 * 根据id查询作业
	 * @param id
	 * @return 作业对象
	 * */
	Workbook findWorkbookById(Integer id);
	
	/**
	 * 根据条件查询作业
	 * @return Workbook对象的List集合
	 * */
	List<Workbook> findWorkbook(Workbook workbook,PageModel pageModel);
	
	/**
	 * 根据id删除作业
	 * @param id
	 * */
	void removeWorkbookById(Integer id);
	
	/**
	 * 修改作业
	 * @param Workbook 作业对象
	 * */
	void modifyWorkbook(Workbook workbook);
	
	/**
	 * 添加作业
	 * @param Workbook 作业对象
	 * */
	void addWorkbook(Workbook workbook);
	//根据作业名称进行查找
	List<Workbook> findWorkbookByTitle(String title,Integer id);
	//根据教师ID,课程ID,班级ID,已批改作业和学期查询作业
	List<Workbook> findWorkbookByCourseIdAndClazzIdAndWflagAndTerm(
			Integer teacher_id,Integer course_id,Integer clazz_id,String term);
	//查询workbook表中的所有学期（去掉重复）
	List<String> findTerm();
}
