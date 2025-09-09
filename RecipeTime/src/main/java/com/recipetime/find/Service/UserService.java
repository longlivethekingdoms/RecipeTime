package com.recipetime.find.Service;

import com.recipetime.find.Model.Users;

public interface UserService {

	void insertUserAdmin(Users users);
	
	String validateUser(Users users);

	void insertJoin(Users users);

	boolean duplicateID(String userid);

	boolean duplicateNickname(String nickname);

	boolean duplicateEmail(String useremail);
}
