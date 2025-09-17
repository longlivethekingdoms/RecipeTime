package com.recipetime.find.Model;

public class Ingredients {
    private int recipeid;
    private String ingname;
    private int ingorder;
    private Integer ingquantity;
    private String unit;
    private String exp;

    public int getRecipeid() { return recipeid; }
    public void setRecipeid(int recipeid) { this.recipeid = recipeid; }

    public String getIngname() { return ingname; }
    public void setIngname(String ingname) { this.ingname = ingname; }

    public int getIngorder() { return ingorder; }
    public void setIngorder(int ingorder) { this.ingorder = ingorder; }

    public Integer getIngquantity() { return ingquantity; }
    public void setIngquantity(Integer ingquantity) { this.ingquantity = ingquantity; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getExp() { return exp; }
    public void setExp(String exp) { this.exp = exp; }
}
