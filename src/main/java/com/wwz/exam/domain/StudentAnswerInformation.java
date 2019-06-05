package com.wwz.exam.domain;

import java.util.Date;

public class StudentAnswerInformation {
    private long id;
    private long textId;
    private long studentId;
    private String subjectType;
    private Date examStartTime;
    private boolean isMark;

    public boolean getIsMark() {
        return isMark;
    }

    public void setIsMark(boolean mark) {
        isMark = mark;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTextId() {
        return textId;
    }

    public void setTextId(long textId) {
        this.textId = textId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }



    public Date getExamStartTime() {
        return examStartTime;
    }

    public void setExamStartTime(Date examStartTime) {
        this.examStartTime = examStartTime;
    }

    @Override
    public String toString() {
        return "StudentAnswerInformation{" +
                "id=" + id +
                ", textId=" + textId +
                ", studentId=" + studentId +
                ", subjectType='" + subjectType + '\'' +
                ", examStartTime=" + examStartTime +
                ", isMark=" + isMark +
                '}';
    }
}
