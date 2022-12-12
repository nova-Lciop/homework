package ssh.homework.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssh.homework.dao.ClazzDao;
import ssh.homework.domain.Clazz;
import ssh.homework.service.ClazzService;
import ssh.homework.tag.PageModel;


@Service("clazzService")
//处理班级相关业务逻辑的服务类
public class ClazzServiceImpl implements ClazzService {
	@Autowired
	ClazzDao clazzDao;
//添加班级
	@Override
	public void addClazz(Clazz clazz) {
		if (clazzDao.selectByCname(clazz.getCname()).size() <= 0)// 班级名称不能重
			clazzDao.save(clazz);
	}
//根据id删除班级
	@Override
	public void removeClazzById(Integer id) {
		clazzDao.deleteById(id);
	}
//修改班级
	@Override
	public void modifyClazz(Clazz clazz) {
		clazzDao.update(clazz);

	}
//根据id查询班级，返回一个Clazz对象
	@Override
	public Clazz findClazzById(Integer id) {

		return clazzDao.selectById(id);
	}
//根据clazz对象中属性的值进行动态查询
	@Override
	public List<Clazz> findClazz(Clazz clazz, PageModel pageModel) {
		/** 当前需要分页的总数据条数 */
		Map<String, Object> params = new HashMap<>();
		params.put("clazz", clazz);
		int recordCount = clazzDao.count(params);
		pageModel.setRecordCount(recordCount);
		if (recordCount > 0) {
			/** 开始分页查询数据：查询第几页的数据 */
			params.put("pageModel", pageModel);
		}
		List<Clazz> clazzs = clazzDao.selectByPage(params);

		return clazzs;
	}
//返回班级的所有对象，即tb_clazz表中所有记录
	@Override	
	public List<Clazz> findAllClazz() {
		// TODO Auto-generated method stub
		return clazzDao.selectAll();
	}

	// 根据班级查询关联的对象，即查询班级及班级的所有学生信息
	@Override
	public Clazz findOneToManyByClazzId(Integer id) {

		return clazzDao.selectClazzByIdToMany(id);
	}

}
