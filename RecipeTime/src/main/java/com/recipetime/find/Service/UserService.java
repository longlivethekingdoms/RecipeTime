package com.recipetime.find.Service;

import com.recipetime.find.Model.Users;

public interface UserService {

	String validateUser(Users users);

	void insertJoin(Users users);
	
}
