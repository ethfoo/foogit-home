package service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;

import entity.PathItem;
import entity.Repository;


public interface GitService {

	boolean createNewRepo(Repository repo);
	void gitCloneRepo(Repository srcRepo, Repository localRepo) throws Exception;
	List<Repository> listUserRepo(String userName);
	Repository getRepoByPath(String path);
	List<String> getBranchList(String gitBasePath) throws IOException,GitAPIException;
	List<PathItem> getRepoPaths(String gitBasePath, String branch, String subPath) throws IOException ;
	List<String> getSrcCode(String path, String branch, String subPath) throws IOException;
	
}
