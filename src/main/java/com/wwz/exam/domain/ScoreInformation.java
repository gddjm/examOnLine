package com.wwz.exam.domain;

import java.util.Date;

public class ScoreInformation {
    private Long id;
    private Long studentId;
    private String studentName;
    private String subjectType;
    private Date examStartTime;
    private int score;
    private long examId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    @Override
    public String toString() {
        return "ScoreInformation{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", subjectType='" + subjectType + '\'' +
                ", examStartTime=" + examStartTime +
                ", score=" + score +
                ", examId=" + examId +
                '}';
    }
}
