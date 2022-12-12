package ssh.homework.domain;

import java.io.Serializable;

public class Course  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer id;//课程id
	private String cname;//课程名称
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
