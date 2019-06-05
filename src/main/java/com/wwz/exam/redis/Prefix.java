package com.wwz.exam.redis;

public interface Prefix {
    int expireSeconds();
    String getPrefix();
}
