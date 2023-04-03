package cn.ybzy.shirodemo.service.shiro;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.ybzy.shirodemo.dao.PermissionDao;
import cn.ybzy.shirodemo.model.Permission;

public class FilterChainDefinitionMapFactory {
	@Autowired
	private PermissionDao permissionDao;
	public Map<String,String> getfilterChainDefinitionMap(){
	    //从数据库中获取数据，构造出一个Map<String,String>对象
		List<Permission> list= permissionDao.getAllPermissions();
		System.out.println(list);
		LinkedHashMap<String,String> permissionMap = new LinkedHashMap<>();
		for(Permission perm:list) {
			if(perm.getPname().contains("p:")) {
				//构造成 perms[userlist]
				String p = perm.getPname().replace("p:", ""); //删除数据表中获取的panme---p:userlist的p:.
				permissionMap.put(perm.getUrl(),"perms["+p+"]");
			}else {
				permissionMap.put(perm.getUrl(),perm.getPname());
			}
		}
		System.out.println(permissionMap); //{/login.html=anon, /logout.html=logout, /admin/userlist.html=perms[userlist], /admin/adduser.html=perms[adduser], /admin/**=authc, /**=anon}
		return permissionMap;
	}

}
