package com.example.myapplication.Bean;


import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

public class User extends BmobUser {
    String gender;
    Integer focusId_sum;
    Integer followId_sum;
    BmobRelation relation;


    public Integer getFollowId_sum() {
        return followId_sum;
    }

    public void setFollowId_sum(Integer followId_sum) {
        this.followId_sum = followId_sum;
    }


    public Integer getFocusId_sum() {
        return focusId_sum;
    }

    public void setFocusId_sum(Integer focusId_sum) {
        this.focusId_sum = focusId_sum;
    }

    public BmobRelation getRelation() {
        return relation;
    }

    public void setRelation(BmobRelation relation) {
        this.relation = relation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

