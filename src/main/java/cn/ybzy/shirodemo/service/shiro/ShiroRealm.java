package cn.ybzy.shirodemo.service.shiro;

import java.util.HashSet;
import java.util.Set;



import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import cn.ybzy.shirodemo.dao.PermissionDao;
import cn.ybzy.shirodemo.dao.RoleDao;
import cn.ybzy.shirodemo.dao.UserDao;
import cn.ybzy.shirodemo.model.Role;
import cn.ybzy.shirodemo.model.User;
public class ShiroRealm extends AuthorizingRealm{
	  @Autowired
      private UserDao userDao;
	  @Autowired
	  private RoleDao roleDao;
	  @Autowired 
	  private PermissionDao permissionDao;
	/**
	 * 真正在做项目的时候，登录的验证与授权认证工作，doGetAuthenticationInfo方法中实现的
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
   //授权认证方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("每一次授权，会去查一下数据库表：   ");   
		//处理授权认证的这个方法里带的参数：传进来的是登陆成功后的用户信息（用户名或用户对象都行），谁传来的呢？（谁登录认证方法doGetAuthenticationInfo传来的）
		  //由于登录认证可能是多个Realm对象，所以可能传过来多个用户信息，这个参数类型本质是一个集合
		  //参数的集合里边的元素的顺序：受Realm在SPRING.XML中配置的顺序影响。
		  //System.out.println(principals);//输出看一下 adminWeiXIN,adminQQ,User [id=1, username=admin, password=c2b632dc87637a5fd03fdf9b61693d17, state=1, roles=null]
        //1.从参数principals中获取当前登陆成后的用户信息（用户名称或对象）
		User user = principals.oneByType(User.class);
		//2.根据第一步中获取到的用户信息，（用户信息中已经包含了角色/权限信息，直接取出来）没包含的话，就从
		    //数年据库中去查询这个用记关联的角色/权限信息，基于角色的权限控制，查角色信息
		Set<String> roles = roleDao.getRolesByUid(user.getId());
		//通过关联的role信息，进一步的查询关联的perssion信息--Set集合
		Set<String> permissions = permissionDao.getPermissionsByUid(user.getId());
		Set<String> newPermissions = new HashSet<>();
		for(String per:permissions) {
			if(per.contains("p:")) {
				newPermissions.add(per.replace("p:",""));
			}else {
				newPermissions.add(per);
			}
		}
	    System.out.println(newPermissions);
		//3.把第二部分中获取到的登录用户关联到role的set集合注入到AuthorizationInfo---有一个实现类SimpleAuthenticationInfo
		/*AuthorizationInfo info = new SimpleAuthorizationInfo(roles); //或写：  */
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(roles);
		info.addStringPermissions(newPermissions);
		return info;
	}
	
}
