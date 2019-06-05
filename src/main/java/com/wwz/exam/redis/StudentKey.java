package com.wwz.exam.redis;

public class StudentKey extends BasePrefix{

	public static final int TOKEN_EXPIRE = 3600*24 * 2;
	private StudentKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	public static StudentKey token = new StudentKey(TOKEN_EXPIRE, "tk");
	public static StudentKey getById = new StudentKey(0, "id");
}
