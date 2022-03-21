package com.example.myapplication.Bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobPointer;

public class Review extends BmobObject {
    String username;
    String useronly_Id;
    String info;
    User author;
    Push_info Push;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Push_info getPush() {
        return Push;
    }

    public void setPush(Push_info push) {
        Push = push;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseronly_Id() {
        return useronly_Id;
    }

    public void setUseronly_Id(String useronly_Id) {
        this.useronly_Id = useronly_Id;
    }



    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
