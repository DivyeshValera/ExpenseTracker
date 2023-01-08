package com.example.expensetracker;

public class MemberIncome {
    private String memberIncome;
    private String memberDes;
    private String memberDate;

    @Override
    public String toString() {
        return "MemberIncome{" +
                "memberIncome='" + memberIncome + '\'' +
                ", memberDes='" + memberDes + '\'' +
                ", memberDate='" + memberDate + '\'' +
                '}';
    }

    public MemberIncome() {
    }

    public String getMemberIncome() {
        return memberIncome;
    }

    public void setMemberIncome(String memberIncome) {
        this.memberIncome = memberIncome;
    }

    public String getMemberDes() {
        return memberDes;
    }

    public void setMemberDes(String memberDes) {
        this.memberDes = memberDes;
    }

    public String getMemberDate() {
        return memberDate;
    }

    public void setMemberDate(String memberDate) {
        this.memberDate = memberDate;
    }
}
