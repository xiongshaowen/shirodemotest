package cn.ybzy.shirodemo.dao;

import java.util.Set;

import org.apache.ibatis.annotations.Param;

import cn.ybzy.shirodemo.model.Role;

public interface RoleDao {
	/**
	 * 通过用户id，查询该用户关联的所有的role角色字符串，不涉及权限时可用。如只需要角色，应用它所有的权限。
	 */
	public Set<String> getRolesByUid(@Param("uid") int uid);
	/**
	 * 通过用户id，查询该用户关联的所有的role对象
	 * @param uid
	 * @return
	 */
    public Set<Role> getRolesObjectByUid(@Param("uid") int uid);
}
