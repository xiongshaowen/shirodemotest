package cn.ybzy.shirodemo.model;

import java.io.Serializable;
import java.util.Set;

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String username;
	private String password;
	private Integer state;   //0表示禁用，1表示启用
	private Set<String> roles;
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", state=" + state + ", roles="
				+ roles + "]";
	}
	
    
}
