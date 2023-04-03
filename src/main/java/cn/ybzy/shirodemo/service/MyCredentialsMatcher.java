package cn.ybzy.shirodemo.service;

import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;

import cn.ybzy.shirodemo.utils.AesEncryptUtil;



public class MyCredentialsMatcher extends SimpleCredentialsMatcher {

	

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token1, AuthenticationInfo info) {
		//完全的由我自书已定义用户输入的密码，和数据库中的密码的比对规则
		UsernamePasswordToken token = (UsernamePasswordToken) token1;
		String pwd=new String(token.getPassword());
		Session session = SecurityUtils.getSubject().getSession();
		String key = (String) session.getAttribute("uuidsalt");
		//System.out.println(Arrays.toString(token2.getPassword()));
		try {
			pwd = AesEncryptUtil.desEncrypt(pwd, key, key);//解密
			session.removeAttribute("uuidsalt");
		}catch(Exception e1){
			throw new IncorrectCredentialsException("受到重放攻击！");
			
		}
		String formPassword = (new SimpleHash("MD5",pwd,token.getUsername(),1024)).toString();
		String accountCredentials = (String)getCredentials(info);
	    return accountCredentials.equals(formPassword);
		
		
	}
	
}
