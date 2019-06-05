package com.wwz.exam.domain;

public class StudentAnswer {
    private long textId;
    private long studentId;
    private long questionNumber;
    private String questionType;
    private String answer;

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

    public long getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(long questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "StudentAnswer{" +
                "textId=" + textId +
                ", studentId=" + studentId +
                ", questionNumber=" + questionNumber +
                ", questionType='" + questionType + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
