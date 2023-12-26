package com.jnu.student;

import java.io.Serializable;

public class Task implements Serializable {
    private String title;
    private String label;
    private String times;
    private String frequency;
    private int achievement;
    private boolean isCheck;
    public Task(String title, int achievement,String label,String times,String frequency,boolean isCheck) {
        this.achievement = achievement;
        this.title = title;
        this.label = label;
        this.times = times;
        this.frequency = frequency;
        this.isCheck = isCheck;
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
    public String getTimes() {
        return times;
    }

    public String getFrequency(){return frequency;}
    public boolean getIsCheck(){return isCheck;}


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
    public void setTimes(String times) {
        this.times = times;
    }

    public void setFrequency(String frequency){this.frequency = frequency;}
    public void setIsCheck(boolean isCheck){this.isCheck = isCheck;}
}
