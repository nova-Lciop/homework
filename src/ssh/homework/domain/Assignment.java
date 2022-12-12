package ssh.homework.domain;

import java.io.Serializable;

public class Assignment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer id;//发布作业id
	private Workbook workbook;//与发布作业id对应的作业对象
	private Exercise exercise; //与发布作业id对应的题库对象
	private int grade;//当前作业题的分值
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Workbook getWorkbook() {
		return workbook;
	}
	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}
	public Exercise getExercise() {
		return exercise;
	}
	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}
	

}
