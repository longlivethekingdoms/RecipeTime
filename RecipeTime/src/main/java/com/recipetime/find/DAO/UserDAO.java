package com.recipetime.find.DAO;

import com.recipetime.find.Model.Users;

public interface UserDAO {

	int duplicateIDCheck(String userid);

	int duplicateNickname(String nickname);

	int duplicateEmail(String useremail);

	void insertJoin(Users users);

}
