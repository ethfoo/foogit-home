package service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.UserMapper;
import entity.User;


public class UserServiceImple implements UserService{
	private static Logger log = Logger.getLogger(UserServiceImple.class.getName());
	
	@Autowired
	private UserMapper userMapper;
	
	public User getUserByName(String userName) {
		User user = userMapper.getUserByName(userName);
		
		return user;
	}

	public User getUserByNameAndPassword(String userName, String password) {
		User user = userMapper.getUserByName(userName);
		//不存在此用户
		if( user == null){
			return null;
		}
		
		if( user.getPassword().equals(password)){
			return user;
		}
		else{ //用户密码错误
			return null;
		}
	}
	
	
	public boolean registerUser(User user){
		int count = userMapper.getCountByName(user.getUserName());
	
	
		log.debug("count="+count);
		if(count>0){
			
			return false;
		}else{
			
			userMapper.insertUser(user);
			return true;
		}
		
	
	}


	

}
