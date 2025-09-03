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
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserPW() {
		return userPW;
	}
	public void setUserPW(String userPW) {
		this.userPW = userPW;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public LocalDate getUserBirth() {
		return userBirth;
	}
	public void setUserBirth(LocalDate userBirth) {
		this.userBirth = userBirth;
	}
	public String getAccesslevel() {
		return accesslevel;
	}
	public void setAccesslevel(String accesslevel) {
		this.accesslevel = accesslevel;
	}
	public LocalDate getRecentlogindate() {
		return recentlogindate;
	}
	public void setRecentlogindate(LocalDate recentlogindate) {
		this.recentlogindate = recentlogindate;
	}
	public LocalDate getChangepwdate() {
		return changepwdate;
	}
	public void setChangepwdate(LocalDate changepwdate) {
		this.changepwdate = changepwdate;
	}
	public LocalDate getSignupdate() {
		return signupdate;
	}
	public void setSignupdate(LocalDate signupdate) {
		this.signupdate = signupdate;
	}
	public short getUserdeactivate() {
		return userdeactivate;
	}
	public void setUserdeactivate(short userdeactivate) {
		this.userdeactivate = userdeactivate;
	}
	public LocalDate getUserdeactivatedate() {
		return userdeactivatedate;
	}
	public void setUserdeactivatedate(LocalDate userdeactivatedate) {
		this.userdeactivatedate = userdeactivatedate;
	}
	
	
}
