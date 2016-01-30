package mapper;

import java.util.List;

import entity.Repository;

public interface RepositoryMapper {

	boolean insertRepository(Repository repo);
	List listUserRepo(Integer userId);
	Repository selectRepoByPath(String path);
}
