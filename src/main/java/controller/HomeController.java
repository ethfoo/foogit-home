package controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import entity.User;
import service.UserService;
import utils.Const;


@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(){
		return "index";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginGet(){
		return "login";
	}
	
	//登录表单submmit
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginPost(HttpSession session,HttpServletRequest request, HttpServletResponse response, @RequestParam String userName, @RequestParam String password) throws IOException{
	
		User user = userService.getUserByNameAndPassword(userName, password);
		if(user != null){
			
			session.setAttribute(Const.SESSION_USER, user);
			
			//登录成功，进入用户主页
			response.sendRedirect(request.getContextPath()+"/user/"+userName);
			return null;
		} else {
			//TODO 登录错误，返回错误提示
			return "login";
		}
		
		
	}
	
	
	//用户注册
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(User user, HttpSession session, HttpServletResponse response, HttpServletRequest request, @RequestParam String userName,@RequestParam String password,@RequestParam String email) throws IOException{
		System.out.println("register");
		
		user.setUserName(userName);
		user.setPassword(password);
		user.setEmail(email);
		
		boolean isSuccess = userService.registerUser(user);
		System.out.println(isSuccess);
		if(isSuccess){
			session.setAttribute(Const.SESSION_USER, user);
			response.sendRedirect(request.getContextPath()+"/user/"+userName);
			return null;
		}else {
			//TODO 注册错误，返回错误提示			
			return "login";
		}
		
		
	}
	
	

}
