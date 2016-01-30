package mapper;

import entity.User;

public interface UserMapper {

	User getUserByName(String userName);
	void insertUser(User user);
	int getCountByName(String userName);
}
