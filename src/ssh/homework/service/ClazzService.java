package ssh.homework.service;

import java.util.List;

import ssh.homework.domain.Clazz;
import ssh.homework.tag.PageModel;

public interface ClazzService {
	//添加班级
	void addClazz(Clazz clazz);
	//根据id删除班级
	void removeClazzById(Integer id);
	//修改班级
	void modifyClazz(Clazz clazz);
	//根据id查询班级，返回的是一个Clazz对象
	Clazz findClazzById(Integer id);
	//根据clazz对象中属性的值进行动态查询，返回值是一个List
	List<Clazz> findClazz(Clazz clazz,PageModel pagemodel);
	//查询班级表中的所记录，返回值是一个List
	List<Clazz> findAllClazz();
	//这是一对多查询，查询班级，同时查询班级中的所有学生信息
	Clazz findOneToManyByClazzId(Integer id);
	

}
