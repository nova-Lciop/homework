package ssh.homework.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ssh.homework.dao.WorkbookDao;
import ssh.homework.domain.Workbook;
import ssh.homework.service.WorkbookService;
import ssh.homework.tag.PageModel;

//tb_workbook表相关的业务逻辑服务类
@Service("workbookService")
public class WorkbookServiceImpl implements WorkbookService {
	
    @Autowired
  //tb_workbook表的操作类对象
	WorkbookDao workbookDao;
	@Override
	//根据id查询作业信息
	public Workbook findWorkbookById(Integer id) {
		
		return workbookDao.selectById(id);
	}

	@Override
	//根据workbook对象属性的值动态查询作业信息
	public List<Workbook> findWorkbook(Workbook workbook, PageModel pageModel) {
		/** 当前需要分页的总数据条数  */
		Map<String,Object> params = new HashMap<>();
		params.put("workbook", workbook);
		int recordCount = workbookDao.count(params);
		pageModel.setRecordCount(recordCount);
		if(recordCount > 0){
	        /** 开始分页查询数据：查询第几页的数据 */
		    params.put("pageModel", pageModel);
	    }
		List<Workbook> workbooks = workbookDao.selectByPage(params);
		 
			return workbooks;
		}
	

	@Override
	//根据id删除作业信息
	public void removeWorkbookById(Integer id) {
		workbookDao.deleteById(id);
	}
		
	@Override
	//修改作业信息
	public void modifyWorkbook(Workbook workbook) {
		workbookDao.update(workbook);
		
	}

	@Override
	//添加作业信息
	public void addWorkbook(Workbook workbook) {
		List<Workbook> workbooks=findWorkbookByTitle(workbook.getTitle(),workbook.getClazz().getId());
		if(workbooks.size()<=0)
		workbookDao.save(workbook);
		
	}

	@Override
	//根据作业名称和班级id查询作业信息
	public List<Workbook> findWorkbookByTitle(String title,Integer clazz_id) {
		
		return workbookDao.selectWorkbookByTitle(title,clazz_id);
	}
	//根据教师，课程ID,班级ID,已批改作业和学期查询作业
	@Override
	public List<Workbook> findWorkbookByCourseIdAndClazzIdAndWflagAndTerm(Integer teacher_id,
			Integer course_id,
			Integer clazz_id, String term) {
		return workbookDao.selectWorkbookByCourseIdAndClazzIdAndWflagAndTerm(
				teacher_id, course_id, clazz_id, term);
	}
	//查询workbook表中的所有学期（去掉重复）
	@Override
	public List<String> findTerm() {
		return workbookDao.selectTerm();
	}

	

}
