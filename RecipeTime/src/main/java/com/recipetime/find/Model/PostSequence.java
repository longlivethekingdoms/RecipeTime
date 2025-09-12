package com.recipetime.find.Model;

import java.util.List;

public class PostSequence {
	private int recipestepid;
    private int recipeid;
    private int recipestep;
    private String explain;
    private String recipevidlink;

    private int ingactivate;
    private int toolactivate;
    private int fireactivate;
    private int tipactivate;

    private String ingexp;
    private String toolexp;
    private String fireexp;
    private String tipexp;

    private List<Attachment> attachments; // 단계별 이미지/영상

    // getter / setter
    
    public int getRecipestepid() {
		return recipestepid;
	}

	public void setRecipestepid(int recipestepid) {
		this.recipestepid = recipestepid;
	}

	public int getRecipeid() {
		return recipeid;
	}

	public void setRecipeid(int recipeid) {
		this.recipeid = recipeid;
	}

	public int getRecipestep() {
		return recipestep;
	}

	public void setRecipestep(int recipestep) {
		this.recipestep = recipestep;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getRecipevidlink() {
		return recipevidlink;
	}

	public void setRecipevidlink(String recipevidlink) {
		this.recipevidlink = recipevidlink;
	}

	public int getIngactivate() {
		return ingactivate;
	}

	public void setIngactivate(int ingactivate) {
		this.ingactivate = ingactivate;
	}

	public int getToolactivate() {
		return toolactivate;
	}

	public void setToolactivate(int toolactivate) {
		this.toolactivate = toolactivate;
	}

	public int getFireactivate() {
		return fireactivate;
	}

	public void setFireactivate(int fireactivate) {
		this.fireactivate = fireactivate;
	}

	public int getTipactivate() {
		return tipactivate;
	}

	public void setTipactivate(int tipactivate) {
		this.tipactivate = tipactivate;
	}

	public String getIngexp() {
		return ingexp;
	}

	public void setIngexp(String ingexp) {
		this.ingexp = ingexp;
	}

	public String getToolexp() {
		return toolexp;
	}

	public void setToolexp(String toolexp) {
		this.toolexp = toolexp;
	}

	public String getFireexp() {
		return fireexp;
	}

	public void setFireexp(String fireexp) {
		this.fireexp = fireexp;
	}

	public String getTipexp() {
		return tipexp;
	}

	public void setTipexp(String tipexp) {
		this.tipexp = tipexp;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
}
