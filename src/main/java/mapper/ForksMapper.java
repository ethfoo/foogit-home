package mapper;

import java.util.List;

import entity.Forks;
import entity.Repository;

public interface ForksMapper {

	boolean insertForks(Forks forks);
	String getUpstreamByDownstream(String downstream);
	List selectForksByUpstream(String upstream);
}
