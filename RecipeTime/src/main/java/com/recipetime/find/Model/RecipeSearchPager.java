package com.recipetime.find.Model;

import com.recipetime.find.pager.Pager;

public class RecipeSearchPager extends Pager {
	
	private String loginUserId;   // 로그인한 사용자 ID
    private String accessLevel;   // "manager" or "normal"
	
	private String keyword;
	private int typeid;
	private int situationid;
	private int methodid;
	private int peopleid;
	private int timeid;
	private int difficultyid;
	
	public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getTypeid() {
		return typeid;
	}
	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}
	public int getSituationid() {
		return situationid;
	}
	public void setSituationid(int situationid) {
		this.situationid = situationid;
	}
	public int getMethodid() {
		return methodid;
	}
	public void setMethodid(int methodid) {
		this.methodid = methodid;
	}
	public int getPeopleid() {
		return peopleid;
	}
	public void setPeopleid(int peopleid) {
		this.peopleid = peopleid;
	}
	public int getTimeid() {
		return timeid;
	}
	public void setTimeid(int timeid) {
		this.timeid = timeid;
	}
	public int getDifficultyid() {
		return difficultyid;
	}
	public void setDifficultyid(int difficultyid) {
		this.difficultyid = difficultyid;
	}
	
}