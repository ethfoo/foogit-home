package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import mapper.RepositoryMapper;
import mapper.UserMapper;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.DepthWalk.Commit;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.springframework.beans.factory.annotation.Autowired;

import entity.PathItem;
import entity.User;
import utils.Const;

public class GitServiceImple implements GitService {
	
	@Autowired
	private RepositoryMapper repoMapper;
	
	@Autowired
	private UserMapper userMapper;

	public boolean createNewRepo(entity.Repository repo) {
		String path = repo.getRepoUrl();
		//创建git文件
		try {
			File f = new File(path +"/.git");
			Repository newlyCreatedRepo = FileRepositoryBuilder.create(f);
			newlyCreatedRepo.create();
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;				
		}
		
		//插入数据库Repository
		repoMapper.insertRepository(repo);
		return true;
	}

	public List listUserRepo(String userName) {
		User user = userMapper.getUserByName(userName);
		
		return repoMapper.listUserRepo(user.getUserId());
	}

	public entity.Repository getRepoByPath(String path) {
		
		return repoMapper.selectRepoByPath(path);
	}


	
	public List<PathItem> getRepoPaths(String gitBasePath, String branch, String subPath) throws IOException {
		List<PathItem> list = new ArrayList<>();
		Repository repository = new FileRepositoryBuilder()
	    .setGitDir(new File(gitBasePath))
	    .build();	
		
		Ref head = repository.getRef(branch);

		if( head == null){
			return null;
		}
       
        RevWalk walk = new RevWalk(repository);

        RevCommit commit = walk.parseCommit(head.getObjectId());
        RevTree tree = commit.getTree();
        
        if( ! (subPath.equals("/")) ){
        	//System.out.println("!");
        	TreeWalk treeWalk = TreeWalk.forPath(repository, subPath, tree);
        	treeWalk.enterSubtree();
        	while (treeWalk.next()) {
        		//System.out.println("found: " + treeWalk.getPathString());
        		PathItem pi = new PathItem();
        		pi.setName(treeWalk.getNameString());
        		pi.setPath(treeWalk.getPathString());
        		pi.setFile(treeWalk.isSubtree()?"false":"true");
        		list.add(pi);
        		//list.add(e);
        	}
        	
        	treeWalk.close();
        }else{
        	//System.out.println("--");
        	TreeWalk treeWalk = new TreeWalk(repository);
        	treeWalk.addTree(tree);
        	while(treeWalk.next()){
        	//	System.out.println("found: " + treeWalk.getPathString());
        		PathItem pi = new PathItem();
        		pi.setName(treeWalk.getNameString());
        		pi.setPath(treeWalk.getPathString());
        		pi.setFile(treeWalk.isSubtree()?"false":"true");
        		list.add(pi);
        	}
        	treeWalk.close();
        }
     
        walk.close();
        
		return list;
	}
	
	/*
	 * 根据branch和src file的路径得到文件内容
	 */
	public List<String> getSrcCode(String gitBasePath, String branch, String subPath) throws IOException {
		List<String> list = new ArrayList<>();
		
		Repository repository = new FileRepositoryBuilder()
	    .setGitDir(new File(gitBasePath))
	    .build();	
		
		Ref head = repository.getRef(branch);


        RevWalk walk = new RevWalk(repository);

        RevCommit commit = walk.parseCommit(head.getObjectId());
        RevTree tree = commit.getTree();
      
        TreeWalk treeWalk = TreeWalk.forPath(repository, subPath, tree);
        
        ObjectId id = treeWalk.getObjectId(0);
        //repository.open(id).copyTo(out);
        InputStream input = repository.open(id).openStream();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        while( (line = reader.readLine()) != null ){
        	list.add(line);
        }
        
        treeWalk.close();
        walk.close();

		
		return list;
	}

	
	@Override
	public List<String> getBranchList(String gitBasePath) throws IOException, GitAPIException {
		List<String> branchList = new ArrayList<>();
		
		Repository repository = new FileRepositoryBuilder()
	    .setGitDir(new File(gitBasePath))
	    .build();	
		
		  try (Git git = new Git(repository)) {
	          List<Ref> call = git.branchList().call();
	          for (Ref ref : call) {
	            //  System.out.println("Branch: " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());
	        	  String prefix = "refs/heads/";
	        	  String name = ref.getName().substring(prefix.length());
	        	  branchList.add(name);
	          }
		  }
		
		return branchList;
	}

	

	

	
	
}
