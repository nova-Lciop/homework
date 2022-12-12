package ssh.homework.domain;

import java.io.Serializable;

public class Exercise implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;//习题id
	private String kind;//题型	
	private String content;//习题内容
	private String answer;// 习题答案
	private String chapter;//章节
	private Course course;//习题所在的课程，这是多对一的关联
	private Teacher teacher ;//出此习题的教师，这是多对一的关联
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getChapter() {
		return chapter;
	}
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}
	
	
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	

}
