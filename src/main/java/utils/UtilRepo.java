package utils;

public class UtilRepo {
	
	/*
	 * @subUrl:  {userName}/{repoName}
	 */
	public static final String getFullGitPath(String subUrl){
		return Const.GIT_REPO_PRE_PATH + subUrl + "/.git";
	}

}
