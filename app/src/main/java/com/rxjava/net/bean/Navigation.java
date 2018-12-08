package com.rxjava.net.bean;

import java.util.List;

/**
 * Created by Lenovo on 2018/12/1.
 */

public class Navigation {
    private List<NavigationContent> articles;

    private int cid;

    private String name;

    public void setArticles(List<NavigationContent> articles) {
        this.articles = articles;
    }

    public List<NavigationContent> getArticles() {
        return this.articles;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getCid() {
        return this.cid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
