package com.example.myapplication.Bean;

import java.util.List;
import java.util.ListIterator;

import cn.bmob.v3.BmobObject;


public class HisRecord extends BmobObject {
    private List<String> hisre;
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getHisre() {
        return hisre;
    }

    public void setHisre(List<String> hisre) {
        this.hisre = hisre;
    }
}
