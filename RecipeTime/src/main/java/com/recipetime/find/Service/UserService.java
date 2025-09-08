package com.recipetime.find.Service;

import com.recipetime.find.Model.Users;

public interface UserService {

	void insertUserAdmin(Users users);
	
	String validateUser(Users users);

	void insertJoin(Users users);
}
