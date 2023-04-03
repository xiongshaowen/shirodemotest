package cn.ybzy.shirodemo.service.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import cn.ybzy.shirodemo.dao.UserDao;
import cn.ybzy.shirodemo.model.User;

//此文件是备份的，因为AuthenticatingRealm类只可用登陆认证，不可作授权认证，但我把一些登陆认证测试做了以便以后查看




//项目开发中不太可能用实现接口Realm，而是继承它的实现类来做验证
/*public class ShiroRealm implements Realm{

	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		
		return null;
	}

	@Override
	public String getName() {
		
		return null;
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		UsernamePasswordToken token2 =(UsernamePasswordToken) token;
		System.out.println(token2.getUsername());
		return false;
	}
	
}*/

public class ShiroRealm_bak extends AuthenticatingRealm{
	  @Autowired
      private UserDao userDao;
	/**
	 * 真正在做项目的时候，登录的验证工作，doGetAuthenticationInfo方法中实现的
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token1) throws AuthenticationException {
		 //1.token把参数，强转UsernamePasswordToken
        UsernamePasswordToken token = (UsernamePasswordToken) token1;
        //2.从UsernamePassworkdToken里获取表单提交过来的用户名
        String username = token.getUsername();
        //3.从数据库中查询有没有用户是username的用户记录
        User user = userDao.getUserByUsername(username);
        //4.根据user对象的具体情况，可以抛出shiro定义的异常
        if(user == null)
            throw new UnknownAccountException("没有此用户");
        if(user.getState() ==0)
            throw new LockedAccountException("用户已被 管理员禁用");
        //5.进一步的让shiro来帮我们判断用户表单传来的密码是对的
           //四个参数：1可为用户名或用户对象。2.从数据库中获取的用户密码 3.密码的盐值4.ShiroRealm类的名字
        ByteSource slat = ByteSource.Util.bytes(username);
        AuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),slat,getName());
        System.out.println("shiroRealm认证！");
        return info;   //若info不为null即为true即通过了。如果只是 return true; 则不管密码是否正确定都会认证通过
	}
	/*
	 * main方法是做加密用的，这里因为我没有做添加用户等太多功能，所以在这里做一个用户名admin 密码123456加密的再加密密文，再复制到表中做测试用的。
	   e10adc3949ba59abbe56e057f20f883e是由login.jsp中javascript用MD5加密算法给123456加了密的密文
	   这里是用于加盐值测试用的。盐值是admin即账号名。
	 */
	
	public static void main(String[] args) {
        Object rs = new SimpleHash("MD5","e10adc3949ba59abbe56e057f20f883e","admin",1024); //1024次加密
        System.out.println(rs);
    }
	
}
