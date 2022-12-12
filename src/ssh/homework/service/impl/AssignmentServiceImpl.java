package ssh.homework.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssh.homework.dao.AssignmentDao;
import ssh.homework.domain.Assignment;
import ssh.homework.domain.Workbook;
import ssh.homework.service.AssignmentService;
import ssh.homework.tag.PageModel;


@Service("assignmentService")
public class AssignmentServiceImpl implements AssignmentService {
	@Autowired
	AssignmentDao assignmentDao;

	@Override
	public Assignment findAssignmentById(Integer id) {
		
		return assignmentDao.selectById(id);
	}
	@Override
	public List<Assignment> findAssignment(Assignment assignment, PageModel pageModel) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("assignment",assignment);
		int recordCount=assignmentDao.count(params);
		pageModel.setRecordCount(recordCount);
		if(recordCount>0) {
		
			params.put("pageModel",pageModel);
		}
		List<Assignment> assignments=assignmentDao.selectByPage(params);
		return assignments;
	}

	@Override
	public void removeAssignmentById(Integer id) {
		assignmentDao.deleteById(id);
		
	}

	@Override
	public void modifyAssignment(Assignment assignment) {
		assignmentDao.update(assignment);
		
	}

	@Override
	public void addAssignment(Assignment assignment) {
		assignmentDao.save(assignment);
		
	}
	/**
	 *在assignment表中生成与fromWorkbookId的习题完全相同的记录，但是表中workbookId是toWorkbookId
	 * @param fromWorkbookId是原作业的Id，toWorkbookId是要复制的作业Id
	 * */
	@Override
	public void replaceAssignmentsWithWorkbookId(Integer fromWorkbookId, Integer toWorkbookId,PageModel pageModel) {
		//根据fromWorkbookId查询tb_assignment表中的所有记录
		Assignment fromAssignment=new Assignment();
		Workbook fromWorkbook=new Workbook();//创建一个Workbook对象
		fromWorkbook.setId(fromWorkbookId);
		fromAssignment.setWorkbook(fromWorkbook);
		List<Assignment> fromAssignments=findAssignment(fromAssignment, pageModel);
		int n=fromAssignments.size();
		Assignment assignment=null;		
		
		if(n>0) {
			//根据toWorkbookId查询tb_assignment表中的所有记录
			Assignment toAssignment=new Assignment();
			Workbook toWorkbook=new Workbook();//创建一个Workbook对象
			toWorkbook.setId(toWorkbookId);
			toAssignment.setWorkbook(toWorkbook);
			List<Assignment> toAssignemnts=findAssignment(toAssignment, pageModel);
			int m=toAssignemnts.size();
			
			if(m>0) {//如果被复制的作业在assignment表中有习题，先清空已布置的作业习题
				for(int j=0;j<m;j++) {
					//删除assignment表中所有toWorkbookId对应的记录
					assignmentDao.deleteById(toAssignemnts.get(j).getId());
				}
			}
			for(int i=0;i<n;i++) {
				assignment=fromAssignments.get(i);				
				assignment.setWorkbook(toWorkbook);
				assignmentDao.save(assignment);//将替换后的assignment对象保存到数据库中
			}
			
		}
		
	}


}
