package ssh.homework.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssh.homework.dao.ExerciseDao;
import ssh.homework.domain.Exercise;
import ssh.homework.domain.ExerciseInfo;
import ssh.homework.domain.Workbook;
import ssh.homework.service.ExerciseService;
import ssh.homework.tag.PageModel;
//ExerciseService服务接口的实现类
@Service("exerciseService")
public class ExerciseServiceImpl implements ExerciseService {
	//tb_exercise表的操作接口实例，由Ioc注入
	@Autowired
	ExerciseDao exerciseDao;
	/**
	 * 根据id查询习题
	 * @param id
	 * @return 习题对象
	 * */
	@Override
	public Exercise findExerciseById(Integer id) {
		
		return exerciseDao.selectById(id);
	}
	/**
	 * 获得所有习题
	 * @return Exercise对象的List集合
	 * */
	@Override
	public List<Exercise> findExercise(Exercise exercise, PageModel pageModel) {
		/** 当前需要分页的总数据条数  */
		Map<String,Object> params = new HashMap<>();
		params.put("exercise", exercise);
		int recordCount = exerciseDao.count(params);
		pageModel.setRecordCount(recordCount);
		if(recordCount > 0){
	        /** 开始分页查询数据：查询第几页的数据 */
		    params.put("pageModel", pageModel);
	    }
		List<Exercise> exercises = exerciseDao.selectByPage(params);
		return exercises;
	}
//根据id删除习题表tb_exercise中数据
	@Override
	public void removeExerciseById(Integer id) {
		exerciseDao.deleteById(id);
	}
//根据exercise对象中属性值更新习题表
	@Override
	public void modifyExercise(Exercise exercise) {
		exerciseDao.update(exercise);
		
	}
//添加习题
	@Override
	public void addExercise(Exercise exercise) {
		exerciseDao.save(exercise);
		
	} 
//查询习题表中习题，查询条件为只列出不在当前作业中的所有习题
	@Override
	public List<Exercise> findByNotInWorkbookIdAndByCourseId(Workbook workbook,PageModel pageModel,String isNotIn) {
		Map<String ,Object> map=new HashMap<String ,Object>();
		map.put("workbook",workbook);
		map.put("isNotIn", isNotIn);//此参数表示根据 exercise的id查询还是根据不在 exercise的id范围内查询
		List<Exercise> exercises=new ArrayList<Exercise>();
		Integer recordCount=exerciseDao.countByWorkbook(map);
		if(recordCount>0) {
			pageModel.setRecordCount(recordCount);
			map.put("pageModel", pageModel);
		exercises=exerciseDao.selectNotInWorkbookIdAndByCourseId(map);
		}
		
		return exercises;
	}
//根据某个作业统计每道题出错的次数，用于分析学生做作业的情况
	@Override
	public List<ExerciseInfo> findExerciseInfoByWorkbookId(Integer Workbook_id) {
		
		return exerciseDao.selectExerciseInfoByWorkbookId(Workbook_id);
	}


}
