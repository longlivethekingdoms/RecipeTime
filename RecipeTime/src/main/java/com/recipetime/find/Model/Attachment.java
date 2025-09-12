package com.recipetime.find.Model;

public class Attachment {
    private int attachmentid;
    private int fileorder;
    private String filename;
    private String fileuuid;
    private String fileext;
    private boolean ismain; // 대표 이미지 체크
    private int recipeid;
    private Integer recipestepid; // null이면 게시글 첨부
	
    // getter / setter
    
    public int getAttachmentid() {
		return attachmentid;
	}
	public void setAttachmentid(int attachmentid) {
		this.attachmentid = attachmentid;
	}
	public int getFileorder() {
		return fileorder;
	}
	public void setFileorder(int fileorder) {
		this.fileorder = fileorder;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFileuuid() {
		return fileuuid;
	}
	public void setFileuuid(String fileuuid) {
		this.fileuuid = fileuuid;
	}
	public String getFileext() {
		return fileext;
	}
	public void setFileext(String fileext) {
		this.fileext = fileext;
	}
	public boolean isIsmain() {
		return ismain;
	}
	public void setIsmain(boolean ismain) {
		this.ismain = ismain;
	}
	public int getRecipeid() {
		return recipeid;
	}
	public void setRecipeid(int recipeid) {
		this.recipeid = recipeid;
	}
	public Integer getRecipestepid() {
		return recipestepid;
	}
	public void setRecipestepid(Integer recipestepid) {
		this.recipestepid = recipestepid;
	}

}
