package com.example.expensetracker;

public class Income {
    private String Description;
    private String Date;
    private int income;


    public Income(String Description, String Date, int income)
    {
        this.Description = Description;
        this.income = income;
        this.Date = Date;
    }

    public Income()
    {

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

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }
}
