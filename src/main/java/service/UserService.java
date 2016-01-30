package service;

import org.springframework.stereotype.Service;

import entity.User;


public interface UserService {

	User getUserByName(String userName);
	User getUserByNameAndPassword(String userName, String password);
	boolean registerUser(User user);
	
}
