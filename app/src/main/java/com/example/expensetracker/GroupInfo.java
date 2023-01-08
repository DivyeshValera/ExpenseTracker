package com.example.expensetracker;

import java.util.Map;

public class GroupInfo {
    private String grpId;
    private String groupName;
    private String adminName;
    private Map<String,Object> memberList;
    private String memberLength;


    public GroupInfo() {
    }

    public String getGrpId() {
        return grpId;
    }

    public void setGrpId(String grpId) {
        this.grpId = grpId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Map<String, Object> getMemberList() {
        return memberList;
    }

    public void setMemberList(Map<String, Object> memberList) {
        this.memberList = memberList;
    }

    public String getMemberLength() {
        return memberLength;
    }

    public void setMemberLength(String memberLength) {
        this.memberLength = memberLength;
    }
}
