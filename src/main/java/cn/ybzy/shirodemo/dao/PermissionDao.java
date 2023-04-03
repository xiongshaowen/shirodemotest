package cn.ybzy.shirodemo.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import cn.ybzy.shirodemo.model.Permission;

public interface PermissionDao {
/**
 * 通过UID获取与用户联的权限的set集合，因为Set集合不会重复
 */
	Set<String> getPermissionsByUid(@Param("uid") int uid);
	/**
	 * 获取所有权限标记
	 * @return
	 */
	List<Permission> getAllPermissions();   //import java.util.List;
}
