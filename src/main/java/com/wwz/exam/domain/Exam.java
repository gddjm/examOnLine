package com.wwz.exam.domain;

import java.util.Date;

public class Exam {
    private long examId;
    private Date examStartTime;
    private long textId;
    private Date examEndTime;
    private String subjectType;

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public Date getExamStartTime() {
        return examStartTime;
    }

    public void setExamStartTime(Date examStartTime) {
        this.examStartTime = examStartTime;
    }

    public long getTextId() {
        return textId;
    }

    public void setTextId(long textId) {
        this.textId = textId;
    }

    public Date getExamEndTime() {
        return examEndTime;
    }

    public void setExamEndTime(Date examEndTime) {
        this.examEndTime = examEndTime;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "examId=" + examId +
                ", examStartTime=" + examStartTime +
                ", textId=" + textId +
                ", examEndTime=" + examEndTime +
                ", subjectType='" + subjectType + '\'' +
                '}';
    }
}
