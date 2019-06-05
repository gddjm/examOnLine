package com.wwz.exam.vo;

import com.wwz.exam.domain.Exam;
import java.util.List;

public class TextVo {
    private String subjectType;
    private int score;
    private long textId;
    private List<Long> questionIdList;
    private List<Long> questionNumberList;

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Long> getIquestionIdList() {
        return questionIdList;
    }

    public void setIquestionIdList(List<Long> iquestionIdList) {
        this.questionIdList = iquestionIdList;
    }

    public List<Long> getQuestionNumberList() {
        return questionNumberList;
    }

    public void setQuestionNumberList(List<Long> questionNumberList) {
        this.questionNumberList = questionNumberList;
    }

    public List<Long> getQuestionIdList() {
        return questionIdList;
    }

    public void setQuestionIdList(List<Long> questionIdList) {
        this.questionIdList = questionIdList;
    }

    public long getTextId() {
        return textId;
    }

    public void setTextId(long textId) {
        this.textId = textId;
    }

    @Override
    public String toString() {
        return "TextVo{" +
                "subjectType='" + subjectType + '\'' +
                ", score=" + score +
                ", textId=" + textId +
                ", questionIdList=" + questionIdList +
                ", questionNumberList=" + questionNumberList +
                '}';
    }
}
