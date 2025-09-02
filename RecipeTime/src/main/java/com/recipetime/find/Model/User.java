package com.recipetime.find.Model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class User {
	String userID;
	String userPW;
	String nickname;
	String username;
	String userEmail;
	String userTel;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	LocalDate userBirth;
	String accesslevel;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	LocalDate recentlogindate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	LocalDate changepwdate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	LocalDate signupdate;
	short userdeactivate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	LocalDate userdeactivatedate;
}
