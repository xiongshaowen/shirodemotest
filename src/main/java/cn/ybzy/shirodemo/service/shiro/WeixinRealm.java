package cn.ybzy.shirodemo.service.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;

public class WeixinRealm extends AuthenticatingRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("这个WeixinRealm类里的doGetAuthenticationInfo,就是专门用来实现微信第三方登录!");
		AuthenticationInfo info = new SimpleAuthenticationInfo("adminWeiXIN","1234567",null,getName());
		return info;
	}

}
