package ssh.homework.domain;

import java.io.Serializable;

public class StudentWorkbook implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;//学生作业的id
	private String studentAnswer;  //学生当前习题作业的答案
	private int grade;  //每个题的分值
	private float score; //学生当前习题得分
	private String notes;//教师批注
	private int rate;  //设置的查重率
	//如果学生与其他同学有超标情况，此变量存放超标的查重率,中间用","分开
	private String studentRate;
	//与studentRate对应的学生的ID,中间用","分开
	private String instructions; 
	private String fileName;//学生上传的作业文件路径
	private Workbook workbook;//当前习题对应的作业对象
	private Student student;//当前学生
	private Exercise exercise;//当前作业中的习题
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
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
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	
	public Exercise getExercise() {
		return exercise;
	}
	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}
	public String getStudentAnswer() {
		return studentAnswer;
	}
	public void setStudentAnswer(String studentAnswer) {
		this.studentAnswer = studentAnswer;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public String getStudentRate() {
		return studentRate;
	}
	public void setStudentRate(String studentRate) {
		this.studentRate = studentRate;
	}
	

}
