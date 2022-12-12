package ssh.homework.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ssh.homework.dao.StudentWorkbookDao;

import ssh.homework.domain.StudentInfo;
import ssh.homework.domain.StudentWorkbook;
import ssh.homework.service.StudentWorkbookService;
import ssh.homework.tag.PageModel;

//StudentWorkbookService的实现类
@Service("studentWorkbookService")
public class StudentWorkbookServiceImpl implements StudentWorkbookService {
	@Autowired
	private StudentWorkbookDao studentWorkbookDao;
	@Override
	//根据学生作业id查询学生作业信息
	public StudentWorkbook findStudentWorkbookById(Integer id) {
		
		return studentWorkbookDao.selectById(id);
	}
	//根据学生ID和作业ID查询某个学生的所有作业内容
	@Override
	public List<StudentWorkbook> findStudentWorkbook(StudentWorkbook studentWorkbook, PageModel pageModel) {
		Map<String,Object> params=new HashMap<String ,Object>();
		params.put("studentWorkbook",studentWorkbook);
		
		int recordCount=studentWorkbookDao.count(params);
		pageModel.setRecordCount(recordCount);
		if(recordCount>0)
			params.put("pageModel",pageModel);
		List<StudentWorkbook> stus=studentWorkbookDao.selectByPage(params);
	
		return stus;
	}
	
	@Override
	//根据ID删除学生作业
	public void removeStudentWorkbookById(Integer id) {
		studentWorkbookDao.deleteById(id);
		
	}
    //学生重做或修改作业
	@Override
	public void modifyStudentWorkbook(StudentWorkbook studentWorkbook) {
		studentWorkbookDao.update(studentWorkbook);
		
	}

	@Override
	//第一次做作业时，执行添加作业
	public void addStudentWorkbook(StudentWorkbook studentWorkbook) {
		//查找是否有重复提交的作业,查找重复提交的条件是workbook_id,student_id,assignment_id同时不能相同
		List<StudentWorkbook> list=findStudentWorkbook(studentWorkbook,new PageModel());
		if(list.size()<=0)
		studentWorkbookDao.save(studentWorkbook);
		else
		studentWorkbookDao.update(studentWorkbook);//如果存在这条记录就执行更新操作
	
	}
	@Override
	//根据workbook_id按student_id统计每学生的成绩，最大查重率和习题数
	public List<StudentInfo> findStudentInfoGroupByStudentIdByWorkbookId(Integer workbook_id) {
		
		return studentWorkbookDao.selectStudentInfoGroupByStudentId(workbook_id);
	}

	@Override
	//根据workbook_id和student_id删除学生作业
	public void removeStudentWorkbookByWorkbookIdAndStudentId(Integer workbook_id, Integer student_id) {
		
		studentWorkbookDao.deleteByWorkbookIdAndStudentId(workbook_id, student_id);
		
	}


}
