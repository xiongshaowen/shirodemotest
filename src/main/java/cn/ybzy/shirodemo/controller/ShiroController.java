package cn.ybzy.shirodemo.controller;



import static org.junit.Assert.assertArrayEquals;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import cn.ybzy.shirodemo.model.User;
import cn.ybzy.shirodemo.utils.AesEncryptUtil;

@Controller
public class ShiroController {
  // @RequiresGuest        //shiro安会框注解，游客可访问的资源。即所有客户都可访问，因为这是一个网站的入口
   @GetMapping(value={"/login.html","/"})
   public String login(Model model,HttpSession session) {
	   //生成一组16位随机数,用于重放攻击处理中。
	   int hashcodeV = UUID.randomUUID().hashCode();        //每次执行都会产生不同的值，绝对不相同
	   if(hashcodeV < 0) hashcodeV = -hashcodeV;            //让随机数取正值
	   String uuidsalt = String.format("%016d", hashcodeV); //让盐值取uuid随机数所16位十进制表示
	   //把盐值，同时保存到前后端
	   model.addAttribute("uuidsalt",uuidsalt);             //前端 ,每次刷新页面都会产生一个随机数
	   session.setAttribute("uuidsalt", uuidsalt);          //后端
	  // System.out.println(uuidsalt);
	   return "login";
   }
  // @RequiresAuthentication        //shiro注解，要登录成功后才可访问。
   @GetMapping("/admin/main.html")
   public String main() {
	   return "/admin/main";
   }
   @PostMapping("/login.html")
   public String login(String username,String password,Integer rememberme, HttpSession session) throws Exception {
	   //System.out.println("表单传来的用用户信息： "+user);
	   //实现我们的登陆验证，使用shiro
	   //1认证的核心组件，subject,获取Subject对象
	   Subject subject = SecurityUtils.getSubject();
	     //从服务器端取出盐值（一进入localhost/shirodemotest/login.html就保存的值，见上一个方法)
	 /*   String key = (String) session.getAttribute("uuidsalt");
	     //封装入token之前，将密进行一次解密成明文
	   password = AesEncryptUtil.desEncrypt(password, key, key);
	   session.removeAttribute("uuidsalt");  */                                //解密成功，立马把后端的随机盐值删除
	   //2.登录验证的第二步，将表单提交过来的用户名和密码封装到token对象
	   UsernamePasswordToken token = new UsernamePasswordToken(username,password);
	   //3.调用subject对象里的login方法，进行登陆验证。
	   
	   //开启记住我功能
	   if(rememberme!=null&& rememberme == 1) {
		   token.setRememberMe(true);
		   
	   }
	  try { 
		  subject.login(token);  //让shiro框架来帮我们验证登陆
		  
	  }catch(Exception e){
		  e.printStackTrace();
		  return "loginError";
	  }
	   return "redirect:/admin/main.html";
   }
   
   @RequiresPermissions(value= {"adduser","aaa","bbb"},logical=Logical.OR)
   @GetMapping("/admin/adduser.html")
   public String adduser() {
	   return "/admin/adduser";
   }
   
   @RequiresPermissions(value= {"userlist","aaa","bbb"},logical=Logical.OR)
   @GetMapping("/admin/userlist.html")
   public String userlist() {
	   return "/admin/userlist";
   }
   @GetMapping("/unauthorized.html")
   public String unauthorized() {
	   return "unauthorized";
   }
   
   @GetMapping("/test.html")
   public String test() {
	   return "test";
   }
   
   @GetMapping("/logout.html")
   public String logout() {
	   SecurityUtils.getSubject().logout();
	   return "redirect:/login.html";
   }
}
