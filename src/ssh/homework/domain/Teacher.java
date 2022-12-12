package ssh.homework.domain;

import java.io.Serializable;

public class Teacher implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer id; //教师id
	private String loginname; //教师登录名
	private String password;//密码
	private String username;//教师姓名
	private String role;//教师角色
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	
}
