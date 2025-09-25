package com.recipetime.find.Model;

import java.util.List;

public class CategoryItem {
	private int itemid;
	private String itemname;
	private String itemparam;
	private List<CategoryOption> optionList;
	
	public int getItemid() {
		return itemid;
	}
	public void setItemid(int itemid) {
		this.itemid = itemid;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}	
	public String getItemparam() {
		return itemparam;
	}
	public void setItemparam(String itemparam) {
		this.itemparam = itemparam;
	}
	public List<CategoryOption> getOptionList() {
		return optionList;
	}
	public void setOptionList(List<CategoryOption> optionList) {
		this.optionList = optionList;
	}
	
	
}
