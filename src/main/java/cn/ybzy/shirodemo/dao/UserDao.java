package cn.ybzy.shirodemo.dao;

import org.apache.ibatis.annotations.Param;

import cn.ybzy.shirodemo.model.User;

public interface UserDao{
   public User getUserByUsername(@Param("username") String username);
}
