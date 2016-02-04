package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import entity.PathItem;
import entity.Repository;
import entity.User;
import service.GitService;
import service.UserService;
import utils.Const;
import utils.UtilRepo;

@Controller
//@RequestMapping("/user/{userName}/{repositoryName}")
public class RepositoryController {
	@Autowired
	private GitService gitService;
	
	@Autowired
	private UserService userService;
	
	
	//创建repo的表单提交
	@RequestMapping(value="/user/{userName}/{repositoryName}",method=RequestMethod.POST)
	public String newEmptyRepository(@PathVariable String userName, @PathVariable String repositoryName,
			@RequestParam String isPrivate,	HttpSession session, Repository repo, RedirectAttributes attr){

		//不重名，则创建新repository	
		String subUrl =  userName + "/" + repositoryName;
		repo.setRepoUrl(subUrl);
		User user = (User)session.getAttribute(Const.SESSION_USER);
		repo.setUserId(user.getUserId());
		repo.setRepoName(repositoryName);
		boolean pri = isPrivate.equals("false")?false:true;
		repo.setPrivate(pri);
		try{
			gitService.createNewRepo(repo);
			
		}catch(Exception e){
			e.printStackTrace();
			//对应userName下已有repositoryName
			attr.addFlashAttribute(Const.ERROR_MSG, "该repository已经存在");
			return "redirect:/error";
		}
		
		return "redirect:/user/{userName}/{repositoryName}";
	}
	
	@RequestMapping(value="/user/{userName}/{repositoryName}",method=RequestMethod.GET)
	public String repositoryHome(Map<String,Object> map, HttpSession session, @PathVariable String userName,
			@PathVariable String repositoryName,  RedirectAttributes attr){
		//判断是否有此用户，该repo是否存在
		Repository repo = gitService.getRepoByPath(userName + "/" + repositoryName);
		if( repo == null){
			attr.addFlashAttribute(Const.ERROR_MSG, "没有此用户或者该repository不存在");
			return "redirect:/error";
		}
		
		//判断是否是用户自己的repo
		User sessionUser = (User)session.getAttribute(Const.SESSION_USER);
		if( sessionUser == null){
			return "redirect:/error";
		}
		if(sessionUser.getUserName().equals(userName)){//not own repo
			map.put(Const.IS_SESSION_USER_SELF, "true");
		}else{
			map.put(Const.IS_SESSION_USER_SELF, "false");
		}
		
		
		try {
			List<String> branchList = gitService.getBranchList(UtilRepo.getFullGitPath(repo.getRepoUrl()));
			map.put("branchList", branchList);
		} catch (IOException | GitAPIException e1) {
			e1.printStackTrace();
		}	
		
		
		//显示path或file
		try {
			List<PathItem> pathItemList = gitService.getRepoPaths(UtilRepo.getFullGitPath(repo.getRepoUrl()), "master", "/");
			if( pathItemList == null){
				map.put(Const.IS_REPO_EMPTY, "true");
			}else{
				map.put(Const.IS_REPO_EMPTY, "false");
			}
	
			map.put("pathItemList", pathItemList);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		map.put("urlMiddle", "/user/"+userName+"/"+repositoryName);
		return "repository";
	}
	
	@RequestMapping(value="/user/{userName}/{repositoryName}/blob/{branch}/**", method=RequestMethod.GET)
	public String repositorySourceCode(Map<String, Object> map, HttpSession session, HttpServletRequest request, 
			@PathVariable String userName, @PathVariable String repositoryName, @PathVariable String branch, 
		    RedirectAttributes attr){
		//TODO 权限控制
		
		
		String requestUri = request.getRequestURI();
		//System.out.println("requestUri-->"+requestUri);
		String pre = "/foogit/user/"+userName+repositoryName+"/blob"+branch+"/";
		String filePath = requestUri.substring(pre.length()+2);
		//System.out.println("filePath-->"+filePath);
		List<String> blobList = null;
		try {
			blobList = gitService.getSrcCode(Const.GIT_REPO_PRE_PATH+userName+"/"+repositoryName+"/.git", branch, filePath);
		
		} catch (Exception e) {
			e.printStackTrace();
			attr.addFlashAttribute(Const.ERROR_MSG, "没有找到对应的文件");
			return "redirect:/error";
		}
		
		for( String line : blobList){
			System.out.println(line);
		}
		
		map.put("blobList", blobList);
		return "repo-blob";
	}
	

	
	@RequestMapping(value="/user/{userName}/{repositoryName}/tree/{branch}/**", method=RequestMethod.GET)
	public String repositoryDir(Map<String, Object> map, @PathVariable String userName,HttpServletRequest request, 
			@PathVariable String repositoryName, @PathVariable String branch,
			RedirectAttributes attr ){
		//TODO 权限控制
		
		String requestUri = request.getRequestURI();
		//System.out.println("requestUri-->"+requestUri);
		String pre = "/foogit/user/"+userName+"/"+repositoryName+"/tree/"+branch;
		String path = requestUri.substring(pre.length());
		//System.out.println("pre-->"+pre);
		//System.out.println("path-->"+path);
		
		if( path.equals("")){
			path = "/";
		}else{
			path=path.substring(1);
		}
		
		try {
			System.out.println("path-->"+path);
			List<PathItem> pathItemList = gitService.getRepoPaths(Const.GIT_REPO_PRE_PATH+userName+"/"+repositoryName+"/.git", branch, path);
	
			map.put("pathItemList", pathItemList);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		map.put("branch", branch);
		map.put("urlMiddle", "/user/"+userName+"/"+repositoryName);
		return "repo-dir";
	}
	
	@RequestMapping(value="/user/{userName}/{repositoryName}/fork")
	public String fork(HttpSession session, @PathVariable(value="userName") String srcUserName, 
			@PathVariable String repositoryName, RedirectAttributes attr){
		
		Repository srcRepo = gitService.getRepoByPath(srcUserName + "/" + repositoryName);
		
		User user = (User)session.getAttribute(Const.SESSION_USER);
		
		Repository localRepo = new Repository();
		localRepo.setRepoName(repositoryName);
		localRepo.setRepoUrl(user.getUserName()+"/"+repositoryName);
		localRepo.setUserId(user.getUserId());
		try {
			gitService.gitCloneRepo(srcRepo, localRepo);
		} catch (Exception e) {
			e.printStackTrace();
			attr.addFlashAttribute(Const.ERROR_MSG, "已经fork");
			return "redirect:/error";
		}
		
		
		
		return "redirect:/user/"+user.getUserName()+"/{repositoryName}";
	}
	
	@RequestMapping(value="/user/{userName}/{repositoryName}/compare")
	public String compare(Map<String, Object> map, @PathVariable String userName, @PathVariable String repositoryName){
		
		String downstream = userName + "/" + repositoryName;
		String upstream = gitService.getUpstream(downstream);
		
		List<String> downstreamBranches = null;
		List<String> upstreamBranches = null;
		try {
			downstreamBranches = gitService.getBranchList(UtilRepo.getFullGitPath(downstream));
			upstreamBranches = gitService.getBranchList(UtilRepo.getFullGitPath(upstream));
		} catch (IOException | GitAPIException e) {
			e.printStackTrace();
		}
		
		List<String> upAndDown = new ArrayList<>();
		if( upstream != null){
			upAndDown.add(downstream);
			upAndDown.add(upstream);
			map.put("upstreamBranches", upstreamBranches);
		}else{
			upAndDown.add(downstream);
		}
		map.put("upAndDown", upAndDown);
		map.put("downstreamBranches", downstreamBranches);

		
		return "repo-compare";
	}
	
}
