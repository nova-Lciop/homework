package ssh.homework.domain;

import java.util.ArrayList;
import java.util.List;
//用于生成学生作业成绩所用的一个过渡类
public class StudentScore {
	private Student student;
	private List<Float> scores;
	public StudentScore() {
		scores=new ArrayList<Float>();
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public List<Float> getScores() {
		return scores;
	}
	public void setScores(List<Float> scores) {
		this.scores = scores;
	}

}
