package com.wwz.exam.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class LoginVo {
    @NotNull
    private String account;
    @NotNull
    @Length(min=32)
    private String password;
    @NotNull
    private String power;
    @NotNull
    private String validateCode;

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", power='" + power + '\'' +
                ", validateCode='" + validateCode + '\'' +
                '}';
    }
}
