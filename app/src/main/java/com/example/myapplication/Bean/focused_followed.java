package com.example.myapplication.Bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;

public class focused_followed extends BmobObject {
    String author;
    BmobRelation focused;
    BmobRelation followed;
    Integer focused_sum;
    Integer followed_sum;

    public int getFocused_sum() {
        return focused_sum;
    }

    public void setFocused_sum(int focused_sum) {
        this.focused_sum = focused_sum;
    }

    public int getFollowed_sum() {
        return followed_sum;
    }

    public void setFollowed_sum(int followed_sum) {
        this.followed_sum = followed_sum;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BmobRelation getFocused() {
        return focused;
    }

    public void setFocused(BmobRelation focused) {
        this.focused = focused;
    }

    public BmobRelation getFollowed() {
        return followed;
    }

    public void setFollowed(BmobRelation followed) {
        this.followed = followed;
    }


}
