package com.example.myapplication.Bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class Push_info extends BmobObject {
    private User author;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    private String username;
    private String info;
    private String gender;
    private String title;



    private String UserOnlyId;

    public String getUserOnlyId() {
        return UserOnlyId;
    }

    public void setUserOnlyId(String userOnlyId) {
        UserOnlyId = userOnlyId;
    }

    private BmobRelation relation;
    private String isrelated;


    public BmobRelation getRelation() {
        return relation;
    }

    public void setRelation(BmobRelation relation) {
        this.relation = relation;
    }

    public String getIsrelated() {
        return isrelated;
    }

    public void setIsrelated(String isrelated) {
        this.isrelated = isrelated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


}
