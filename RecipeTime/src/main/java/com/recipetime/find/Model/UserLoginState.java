package com.recipetime.find.Model;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserLoginState {
	private String userid;
	private int wrongnum;
	private int blocklogin;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate blockdate;

	
}
