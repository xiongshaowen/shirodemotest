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



public class MyCredentialsMatcher1 extends SimpleCredentialsMatcher {

	

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token1, AuthenticationInfo info) {
		//完全的由我自书已定义用户输入的密码，和数据库中的密码的比对规则
		return true;
		
		
	}
	
}
