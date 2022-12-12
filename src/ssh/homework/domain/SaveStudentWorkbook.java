package ssh.homework.domain;

import java.util.ArrayList;
import java.util.List;
//这个类是在添加作业时的一个封装类，作用是能将一个集合从JSP页面带回Controller
public class SaveStudentWorkbook {
	List<StudentWorkbook> studentWorkbooks=new ArrayList<StudentWorkbook>();

	public List<StudentWorkbook> getStudentWorkbooks() {
		return studentWorkbooks;
	}

	public void setStudentWorkbooks(List<StudentWorkbook> studentWorkbooks) {
		this.studentWorkbooks = studentWorkbooks;
	}



}
