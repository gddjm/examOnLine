package com.wwz.exam.domain;

public class Score {
    private long id;
    private long studentId;
    private int score;
    private long examId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
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
        return "Score{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", score=" + score +
                ", examId=" + examId +
                '}';
    }
}
