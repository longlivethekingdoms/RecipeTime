package com.recipetime.find.Model;

public class Attachment {
    private int attachmentid;
    private Integer fileorder;
    private String filename;
    private String fileuuid;
    private String fileext;
    private int ismain;
    private Integer recipeid;
    private Integer recipestepid;
    private Integer announcementid;
    private Integer questionid;

    public int getAttachmentid() { return attachmentid; }
    public void setAttachmentid(int attachmentid) { this.attachmentid = attachmentid; }

    public Integer getFileorder() { return fileorder; }
    public void setFileorder(Integer fileorder) { this.fileorder = fileorder; }

    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }

    public String getFileuuid() { return fileuuid; }
    public void setFileuuid(String fileuuid) { this.fileuuid = fileuuid; }

    public String getFileext() { return fileext; }
    public void setFileext(String fileext) { this.fileext = fileext; }

    public int getIsmain() { return ismain; }
    public void setIsmain(int ismain) { this.ismain = ismain; }

    public Integer getRecipeid() { return recipeid; }
    public void setRecipeid(Integer recipeid) { this.recipeid = recipeid; }

    public Integer getRecipestepid() { return recipestepid; }
    public void setRecipestepid(Integer recipestepid) { this.recipestepid = recipestepid; }

    public Integer getAnnouncementid() { return announcementid; }
    public void setAnnouncementid(Integer announcementid) { this.announcementid = announcementid; }

    public Integer getQuestionid() { return questionid; }
    public void setQuestionid(Integer questionid) { this.questionid = questionid; }
}