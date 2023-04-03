package cn.ybzy.shirodemo.model;

import java.io.Serializable;
import java.util.Set;

public class Role implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String rname;
	private String rcode;     //主要用于判断超级管理员中用到，当code=“admin"时为超级管理员
	private Set<Permission> permissions;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public String getRcode() {
		return rcode;
	}
	public void setRcode(String rcode) {
		this.rcode = rcode;
	}
	public Set<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	
	
	

}
