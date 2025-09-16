package com.recipetime.find.Model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Post {
	private int recipeid;
	private String recipetitle;
	private String recipecontent;
	private String recipemainvidlink;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate recipewritedate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate recipefixdate;
	private int recipeviews;
	private int typeid;
	private int situationid;
	private int methodid;
	private int peopleid;
	private int timeid;
	private int difficultyid;
	private int recipedeactivate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate recipedeactivatedate;
	private int isreport;
	private String userid;
	
	private List<PostSequence> sequences;
	private List<Ingredients> ingredients;
	private List<Attachment> attachments;
	private List<Tag> tags;
	
	public int getRecipeid() {
		return recipeid;
	}
	public void setRecipeid(int recipeid) {
		this.recipeid = recipeid;
	}
	public String getRecipetitle() {
		return recipetitle;
	}
	public void setRecipetitle(String recipetitle) {
		this.recipetitle = recipetitle;
	}
	public String getRecipecontent() {
		return recipecontent;
	}
	public void setRecipecontent(String recipecontent) {
		this.recipecontent = recipecontent;
	}
	public String getRecipemainvidlink() {
		return recipemainvidlink;
	}
	public void setRecipemainvidlink(String recipemainvidlink) {
		this.recipemainvidlink = recipemainvidlink;
	}
	public LocalDate getRecipewritedate() {
		return recipewritedate;
	}
	public void setRecipewritedate(LocalDate recipewritedate) {
		this.recipewritedate = recipewritedate;
	}
	public LocalDate getRecipefixdate() {
		return recipefixdate;
	}
	public void setRecipefixdate(LocalDate recipefixdate) {
		this.recipefixdate = recipefixdate;
	}
	public int getRecipeviews() {
		return recipeviews;
	}
	public void setRecipeviews(int recipeviews) {
		this.recipeviews = recipeviews;
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
	public int getRecipedeactivate() {
		return recipedeactivate;
	}
	public void setRecipedeactivate(int recipedeactivate) {
		this.recipedeactivate = recipedeactivate;
	}
	public LocalDate getRecipedeactivatedate() {
		return recipedeactivatedate;
	}
	public void setRecipedeactivatedate(LocalDate recipedeactivatedate) {
		this.recipedeactivatedate = recipedeactivatedate;
	}
	public int getIsreport() {
		return isreport;
	}
	public void setIsreport(int isreport) {
		this.isreport = isreport;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public List<PostSequence> getSequences() {
		return sequences;
	}
	public void setSequences(List<PostSequence> sequences) {
		this.sequences = sequences;
	}
	public List<Ingredients> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<Ingredients> ingredients) {
		this.ingredients = ingredients;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	
}
