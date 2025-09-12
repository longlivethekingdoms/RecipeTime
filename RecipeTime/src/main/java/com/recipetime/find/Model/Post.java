package com.recipetime.find.Model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Post {
	private int recipeid;
    private String recipetitle;
    private String recipecontent;
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate recipewritedate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate recipefixdate;
    private int recipeviews;
    private String recipemainvidlink;
    private int ishotdisplay;
    private int isbestdisplay;
    private int isprivate;
    private int recipedeactivate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate recipedeactivatedate;
    private int isreport;
    private String userid;
    private int maincategoryid;
    private int subcategoryid;
    private String subcategoryname;

    private List<PostSequence> sequences; // 단계 목록
    private List<Attachment> attachments;   // 이미지/대표 이미지
    private CategoryType category; //카테고리
    private List<Tag> tags;  // 태그
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
	public String getRecipemainvidlink() {
		return recipemainvidlink;
	}
	public void setRecipemainvidlink(String recipemainvidlink) {
		this.recipemainvidlink = recipemainvidlink;
	}
	public int getIshotdisplay() {
		return ishotdisplay;
	}
	public void setIshotdisplay(int ishotdisplay) {
		this.ishotdisplay = ishotdisplay;
	}
	public int getIsbestdisplay() {
		return isbestdisplay;
	}
	public void setIsbestdisplay(int isbestdisplay) {
		this.isbestdisplay = isbestdisplay;
	}
	public int getIsprivate() {
		return isprivate;
	}
	public void setIsprivate(int isprivate) {
		this.isprivate = isprivate;
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
	public int getMaincategoryid() {
		return maincategoryid;
	}
	public void setMaincategoryid(int maincategoryid) {
		this.maincategoryid = maincategoryid;
	}
	public int getSubcategoryid() {
		return subcategoryid;
	}
	public void setSubcategoryid(int subcategoryid) {
		this.subcategoryid = subcategoryid;
	}
	public String getSubcategoryname() {
		return subcategoryname;
	}
	public void setSubcategoryname(String subcategoryname) {
		this.subcategoryname = subcategoryname;
	}
	public List<PostSequence> getSequences() {
		return sequences;
	}
	public void setSequences(List<PostSequence> sequences) {
		this.sequences = sequences;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	public CategoryType getCategory() {
		return category;
	}
	public void setCategory(CategoryType category) {
		this.category = category;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
    
    
}
