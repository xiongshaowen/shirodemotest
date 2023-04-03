package cn.ybzy.shirodemo.model;

import java.io.Serializable;

public class Permission implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
       private String pname;
       private String url;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Permission [id=" + id + ", pname=" + pname + ", url=" + url + "]";
	}
	
       
}
