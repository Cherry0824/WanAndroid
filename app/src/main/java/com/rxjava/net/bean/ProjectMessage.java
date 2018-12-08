package com.rxjava.net.bean;

import java.util.List;

/**
 * Created by Lenovo on 2018/12/1.
 */

public class ProjectMessage {
    private int curPage;

    private List<ProjectItem> datas ;

    private int offset;

    private boolean over;

    private int pageCount;

    private int size;

    private int total;

    public void setCurPage(int curPage){
        this.curPage = curPage;
    }
    public int getCurPage(){
        return this.curPage;
    }
    public void setDatas(List<ProjectItem> datas){
        this.datas = datas;
    }
    public List<ProjectItem> getDatas(){
        return this.datas;
    }
    public void setOffset(int offset){
        this.offset = offset;
    }
    public int getOffset(){
        return this.offset;
    }
    public void setOver(boolean over){
        this.over = over;
    }
    public boolean getOver(){
        return this.over;
    }
    public void setPageCount(int pageCount){
        this.pageCount = pageCount;
    }
    public int getPageCount(){
        return this.pageCount;
    }
    public void setSize(int size){
        this.size = size;
    }
    public int getSize(){
        return this.size;
    }
    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }
}
