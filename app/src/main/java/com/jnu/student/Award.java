package com.jnu.student;

import java.io.Serializable;

public class Award implements Serializable {
    // 私有字段，用于存储图书的标题和成就点数
    private String title;
    private String label;
    private int achievement;
    public Award(String title, int achievement,String label) {
        this.achievement = achievement;
        this.title = title;
        this.label = label;
    }

    // 获取图书标题的方法
    public String getTitle() {
        return title;
    }

    // 获取成就点数
    public int getAchievement() {
        return achievement;
    }

    //获取标签
    public String getLabel() {
        return label;
    }


    // 设置图书标题的方法
    public void setTitle(String title) {
        this.title = title;
    }

    // 设置成就点数
    public void setAchievement(int achievement) {
        this.achievement = achievement;
    }

    //设置标签
    public void setLabel(String label){
        this.label = label;
    }

}
