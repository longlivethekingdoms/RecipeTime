package com.recipetime.find.Model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Users {
	String userid;
	String userpw;
	String confirmPw;
	String nickname;
	String username;
	String useremail;
	String usertel;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
	LocalDate userbirth;
	String accesslevel;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
	LocalDate recentlogindate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
	LocalDate changepwdate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
	LocalDate signupdate;
	short userdeactivate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
	LocalDate userdeactivatedate;
	String agree01;
	String agree02;
	boolean isduplicateIDCheck;
	boolean isduplicateNickname;
	boolean isduplicateEmail;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserpw() {
		return userpw;
	}
	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}	
	public String getConfirmPw() {
		return confirmPw;
	}
	public void setConfirmPw(String confirmPw) {
		this.confirmPw = confirmPw;
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
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	public String getUsertel() {
		return usertel;
	}
	public void setUsertel(String usertel) {
		this.usertel = usertel;
	}
	
	public LocalDate getUserbirth() {
		return userbirth;
	}
	public void setUserbirth(LocalDate userbirth) {
		this.userbirth = userbirth;
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
	public String getAgree01() {
		return agree01;
	}
	public void setAgree01(String agree01) {
		this.agree01 = agree01;
	}
	public String getAgree02() {
		return agree02;
	}
	public void setAgree02(String agree02) {
		this.agree02 = agree02;
	}
	public boolean getIsduplicateIDCheck() {
		return isduplicateIDCheck;
	}
	public void setIsduplicateIDCheck(boolean isduplicateIDCheck) {
		this.isduplicateIDCheck = isduplicateIDCheck;
	}
	public boolean getIsduplicateNickname() {
		return isduplicateNickname;
	}
	public void setIsduplicateNickname(boolean isduplicateNickname) {
		this.isduplicateNickname = isduplicateNickname;
	}
	public boolean getIsduplicateEmail() {
		return isduplicateEmail;
	}
	public void setIsduplicateEmail(boolean isduplicateEmail) {
		this.isduplicateEmail = isduplicateEmail;
	}
	
	
}
