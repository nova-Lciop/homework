package ssh.homework.domain;

import java.io.Serializable;
import java.util.Date;
public class Workbook implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer id;//作业id
	private String title; //作业标题
	private String wflag;//是否发布作业
	//private String sflag;//作业是否已批改
	private String term;//第几学期的第几次作业
	private Date createDate;//创建日期
	private Teacher teacher;//teacher表的外键
	private Course  course;//course表的外键
	private Clazz   clazz;//clazz表的外键	
	private String fileName;//教师上传文件的路径
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
//	public String getSflag() {
//		return sflag;
//	}
//	public void setSflag(String sflag) {
//		this.sflag = sflag;
//	}
	public String getWflag() {
		return wflag;
	}
	public void setWflag(String wflag) {
		this.wflag = wflag;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Clazz getClazz() {
		return clazz;
	}
	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}
	
}
