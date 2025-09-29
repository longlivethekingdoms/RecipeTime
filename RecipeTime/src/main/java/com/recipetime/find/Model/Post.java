package com.recipetime.find.Model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Post {
    private int recipeid;
    private String recipetitle;
    private String recipecontent;
    private String recipeMainVidLink;
    private LocalDate recipewritedate;
    private LocalDate recipefixdate;
    private Integer recipeviews;
    private int ishotdisplay;
    private int isbestdisplay;
    private int isprivate;
    private int typeid;
    private int situationid;
    private int methodid;
    private int peopleid;
    private int timeid;
    private int difficultyid;
    private int recipedeactivate;
    private LocalDate recipedeactivatedate;
    private int isreport;
    private String userid;
    
    private String typename;
    private String situationname;
    private String methodname;
    private String peoplename;
    private String timename;
    private String difficultyname;

    private List<PostSequence> sequences;
    private List<Ingredients> ingredients;
    private List<Attachment> attachments;
    private List<Tag> tags;

    // getters / setters
    public int getRecipeid() { return recipeid; }
    public void setRecipeid(int recipeid) { this.recipeid = recipeid; }

    public String getRecipetitle() { return recipetitle; }
    public void setRecipetitle(String recipetitle) { this.recipetitle = recipetitle; }

    public String getRecipecontent() { return recipecontent; }
    public void setRecipecontent(String recipecontent) { this.recipecontent = recipecontent; }

    public String getRecipeMainVidLink() { return recipeMainVidLink; }
    public void setRecipeMainVidLink(String recipeMainVidLink) { this.recipeMainVidLink = recipeMainVidLink; }

    public LocalDate getRecipewritedate() { return recipewritedate; }
    public void setRecipewritedate(LocalDate recipewritedate) { this.recipewritedate = recipewritedate; }

    public LocalDate getRecipefixdate() { return recipefixdate; }
    public void setRecipefixdate(LocalDate recipefixdate) { this.recipefixdate = recipefixdate; }

    public Integer getRecipeviews() { return recipeviews; }
    public void setRecipeviews(Integer recipeviews) { this.recipeviews = recipeviews; }

    public int getIshotdisplay() { return ishotdisplay; }
    public void setIshotdisplay(int ishotdisplay) { this.ishotdisplay = ishotdisplay; }

    public int getIsbestdisplay() { return isbestdisplay; }
    public void setIsbestdisplay(int isbestdisplay) { this.isbestdisplay = isbestdisplay; }

    public int getIsprivate() { return isprivate; }
    public void setIsprivate(int isprivate) { this.isprivate = isprivate; }

    public int getTypeid() { return typeid; }
    public void setTypeid(int typeid) { this.typeid = typeid; }

    public int getSituationid() { return situationid; }
    public void setSituationid(int situationid) { this.situationid = situationid; }

    public int getMethodid() { return methodid; }
    public void setMethodid(int methodid) { this.methodid = methodid; }

    public int getPeopleid() { return peopleid; }
    public void setPeopleid(int peopleid) { this.peopleid = peopleid; }

    public int getTimeid() { return timeid; }
    public void setTimeid(int timeid) { this.timeid = timeid; }

    public int getDifficultyid() { return difficultyid; }
    public void setDifficultyid(int difficultyid) { this.difficultyid = difficultyid; }

    public int getRecipedeactivate() { return recipedeactivate; }
    public void setRecipedeactivate(int recipedeactivate) { this.recipedeactivate = recipedeactivate; }

    public LocalDate getRecipedeactivatedate() { return recipedeactivatedate; }
    public void setRecipedeactivatedate(LocalDate recipedeactivatedate) { this.recipedeactivatedate = recipedeactivatedate; }

    public int getIsreport() { return isreport; }
    public void setIsreport(int isreport) { this.isreport = isreport; }

    public String getUserid() { return userid; }
    public void setUserid(String userid) { this.userid = userid; }
    
    public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getSituationname() {
		return situationname;
	}
	public void setSituationname(String situationname) {
		this.situationname = situationname;
	}
	public String getMethodname() {
		return methodname;
	}
	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}
	public String getPeoplename() {
		return peoplename;
	}
	public void setPeoplename(String peoplename) {
		this.peoplename = peoplename;
	}
	public String getTimename() {
		return timename;
	}
	public void setTimename(String timename) {
		this.timename = timename;
	}
	public String getDifficultyname() {
		return difficultyname;
	}
	public void setDifficultyname(String difficultyname) {
		this.difficultyname = difficultyname;
	}
	
	public List<PostSequence> getSequences() { return sequences; }
    public void setSequences(List<PostSequence> sequences) { this.sequences = sequences; }

    public List<Ingredients> getIngredients() { return ingredients; }
    public void setIngredients(List<Ingredients> ingredients) { this.ingredients = ingredients; }

    public List<Attachment> getAttachments() { return attachments; }
    public void setAttachments(List<Attachment> attachments) { this.attachments = attachments; }

    public List<Tag> getTags() { return tags; }
    public void setTags(List<Tag> tags) { this.tags = tags; }
}