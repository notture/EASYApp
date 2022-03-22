package com.example.myapplication.Bean;


import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

public class User extends BmobUser {
    String gender;

    public BmobRelation getRelation_Push() {
        return relation_Push;
    }

    public void setRelation_Push(BmobRelation relation_Push) {
        this.relation_Push = relation_Push;
    }

    BmobRelation relation_Push;



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

