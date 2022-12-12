package ssh.homework.service;

import java.util.List;

import ssh.homework.domain.Assignment;
import ssh.homework.tag.PageModel;

public interface AssignmentService {

	/**
	 * 根据id查询作业
	 * @param id
	 * @return 作业对象
	 * */
	Assignment findAssignmentById(Integer id);
	
	/**
	 * 获得所有作业
	 * @return User对象的List集合
	 * */
	List<Assignment> findAssignment(Assignment assignment,PageModel pageModel);
	
	/**
	 * 根据id删除作业
	 * @param id
	 * */
	void removeAssignmentById(Integer id);
	
	/**
	 * 修改作业
	 * @param Assignment 作业对象
	 * */
	void modifyAssignment(Assignment assignment);
	
	/**
	 * 添加作业
	 * @param Assignment 作业对象
	 * */
	void addAssignment(Assignment assignment);
	/**
	 *在assignment表中生成与fromWorkbookId的习题完全相同的记录，但是表中workbookId是toWorkbookId
	 * @param fromWorkbookId是原作业的Id，toWorkbookId是要复制的作业Id
	 * */
	void replaceAssignmentsWithWorkbookId(Integer fromWorkbookId,Integer toWorkbookId,PageModel pageModel);
	
}
