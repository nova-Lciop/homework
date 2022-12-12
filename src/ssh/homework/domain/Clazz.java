package ssh.homework.domain;

import java.io.Serializable;
import java.util.List;
//与tb_clazz表对应的实体类
public class Clazz implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id; //班级id
	private String cname;  //班级名称
	private List<Workbook> workbooks; //与班级关联的作业列表
	private List<Student> students;//与班级关系的学生列表

	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	public List<Workbook> getWorkbooks() {
		return workbooks;
	}
	public void setWorkbooks(List<Workbook> workbooks) {
		this.workbooks = workbooks;
	}

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}

}
