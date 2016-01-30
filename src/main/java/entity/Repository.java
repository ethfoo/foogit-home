package entity;

public class Repository {
	
	private Integer repoId;
	private Integer userId;
	private String repoName;
	private String repoUrl;
	private boolean isPrivate;
	
	public Integer getRepoId() {
		return repoId;
	}
	public void setRepoId(Integer repoId) {
		this.repoId = repoId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getRepoName() {
		return repoName;
	}
	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}
	public String getRepoUrl() {
		return repoUrl;
	}
	public void setRepoUrl(String repoUrl) {
		this.repoUrl = repoUrl;
	}
	public boolean isPrivate() {
		return isPrivate;
	}
	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
	

}
