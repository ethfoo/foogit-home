package entity;

public class Forks {
	private Integer forkId;
	private String upstream;
	private String downstream;
	
	public Integer getForkId() {
		return forkId;
	}
	public void setForkId(Integer forkId) {
		this.forkId = forkId;
	}
	public String getUpstream() {
		return upstream;
	}
	public void setUpstream(String upstream) {
		this.upstream = upstream;
	}
	public String getDownstream() {
		return downstream;
	}
	public void setDownstream(String downstream) {
		this.downstream = downstream;
	}
	
	

}
