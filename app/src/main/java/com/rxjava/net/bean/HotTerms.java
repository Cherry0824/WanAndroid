package com.rxjava.net.bean;

/**
 * Created by Lenovo on 2018/12/1.
 */

public class HotTerms {
    private int id;

    private String link;

    private String name;

    private int order;

    private int visible;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setLink(String link){
        this.link = link;
    }
    public String getLink(){
        return this.link;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setOrder(int order){
        this.order = order;
    }
    public int getOrder(){
        return this.order;
    }
    public void setVisible(int visible){
        this.visible = visible;
    }
    public int getVisible(){
        return this.visible;
    }
}
