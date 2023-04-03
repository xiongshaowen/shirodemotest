package cn.ybzy.shirodemo.service.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;

public class QQRealm extends AuthenticatingRealm{

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("这个QQRealm类里的doGetAuthenticationInfo,就是专门用来实现QQ第三方登录!");
		AuthenticationInfo info = new SimpleAuthenticationInfo("adminQQ","123456",null,getName());
		return info;
	}

}
