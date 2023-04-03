package cn.ybzy.shirodemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ybzy.shirodemo.dao.UserDao;
import cn.ybzy.shirodemo.model.User;
@Service("userService")   //放进ioc中
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
	@Override
	public User getUserByUsername(String username) {
		
		return userDao.getUserByUsername(username);
	}

}
