package com.recipetime.find.Model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserLoginState {
	private String userid;
	private int wrongnum;
	private int blocklogin;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate blockdate;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getWrongnum() {
		return wrongnum;
	}
	public void setWrongnum(int wrongnum) {
		this.wrongnum = wrongnum;
	}
	public int getBlocklogin() {
		return blocklogin;
	}
	public void setBlocklogin(int blocklogin) {
		this.blocklogin = blocklogin;
	}
	public LocalDate getBlockdate() {
		return blockdate;
	}
	public void setBlockdate(LocalDate blockdate) {
		this.blockdate = blockdate;
	}

	
}
