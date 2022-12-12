package ssh.homework.service;

import java.util.List;

import ssh.homework.domain.Exercise;
import ssh.homework.domain.ExerciseInfo;
import ssh.homework.domain.Workbook;
import ssh.homework.tag.PageModel;

public interface ExerciseService {
	
	
	/**
	 * 根据id查询习题
	 * @param id
	 * @return 习题对象
	 * */
	Exercise findExerciseById(Integer id);
	
	/**
	 * 根据exercise对象中属性的值动态查询习题
	 * @return Exercise对象的List集合
	 * */
	List<Exercise> findExercise(Exercise exercise,PageModel pageModel);
	
	/**
	 * 根据id删除习题
	 * @param id
	 * */
	void removeExerciseById(Integer id);
	
	/**
	 * 修改习题
	 * @param Exercise 习题对象 
	 * */
	void modifyExercise(Exercise exercise);
	
	/**
	 * 添加习题
	 * @param Exercise 习题对象
	 * */
	void addExercise(Exercise exercise);	
	/**
	 * 根据workbook查询不在当前作业中的习题
	 * @param Workbook 作业对象
	 * @return 习题对象的集合
	 * */
	List<Exercise> findByNotInWorkbookIdAndByCourseId(Workbook workbook,PageModel pageModel,String isNotIn);
	//统计同一个作业中的每个习题的错误次数
	List<ExerciseInfo> findExerciseInfoByWorkbookId(Integer Workbook_id);

	
	
}
