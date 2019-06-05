package com.wwz.exam.vo;

import com.wwz.exam.domain.Student;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class RegisterVo {
    private Student student;
    private String validateCode;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    @Override
    public String toString() {
        return "RegisterVo{" +
                "student=" + student +
                ", validateCode='" + validateCode + '\'' +
                '}';
    }
}
