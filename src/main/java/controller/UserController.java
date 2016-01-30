package controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import entity.Repository;
import entity.User;
import service.GitService;
import service.UserService;
import utils.Const;



@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GitService gitService;
	
	//用户的主页
	@RequestMapping(value="/user/{userName}", method=RequestMethod.GET)
	public String userProjectHome(Map<String, Object> map, @PathVariable String userName, HttpSession session,
			RedirectAttributes attr){
		
		User pageUser = userService.getUserByName(userName);
		//此用户不存在
		if( pageUser == null){
			attr.addFlashAttribute(Const.ERROR_MSG, "该用户不存在");
			return "redirect:/error";
		}
		
		User sessionUser = (User) session.getAttribute(Const.SESSION_USER);
		if(sessionUser == null){
			return "redirect:/login";
		}
		//此页面是本次登陆的用户页面
		if(sessionUser.equals(pageUser)){
			map.put(Const.IS_SESSION_USER_SELF, "true");
		
		}else{
			map.put(Const.IS_SESSION_USER_SELF, "false");
		}	
		
		//获取此用户的repository列表	
		List<Repository> repoList = gitService.listUserRepo(userName);
		map.put("repoList", repoList);
		
		map.put("user", pageUser);
		return "user-home";
	}
	
	//创建一个新的repository
	@RequestMapping(value="/new")
	public String createNewProject(HttpSession session, User user, Map<String, Object> map){
		user = (User) session.getAttribute(Const.SESSION_USER);
		map.put("user", user);
		return "new-repository";
	}
	
	

}
