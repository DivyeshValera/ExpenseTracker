package com.example.expensetracker;

public class Transfer
{
    private String Description;
    private String Date;
    private int transfer;
    private String targetaccount;
    private String sourceaccount;


    public Transfer(String description, String date, int income, String targetaccount, String sourceaccount) {
        this.Description = description;
        this.Date = date;
        this.transfer = income;
        this.targetaccount = targetaccount;
        this.sourceaccount = sourceaccount;
    }

    public Transfer() {
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

//    public int getIncome() {
//        return transfer;
//    }
//
//    public void setIncome(int amount) {
//        this.transfer = amount;
//    }


    public int getTransfer() {
        return transfer;
    }

    public void setTransfer(int transfer) {
        this.transfer = transfer;
    }

    public String getTargetaccount() {
        return targetaccount;
    }

    public void setTargetaccount(String targetaccount) {
        this.targetaccount = targetaccount;
    }

    public String getSourceaccount() {
        return sourceaccount;
    }

    public void setSourceaccount(String sourceaccount) {
        this.sourceaccount = sourceaccount;
    }
}
