package com.rxjava.net.bean;

import java.util.List;


public class ArticleMessage {

    private int offset;
    private int size;
    private int total;
    private int pageCount;
    private int curPage;
    private boolean over;
    private List<ArticleItem> datas;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public List<ArticleItem> getDatas() {
        return datas;
    }

    public void setDatas(List<ArticleItem> datas) {
        this.datas = datas;
    }


}
