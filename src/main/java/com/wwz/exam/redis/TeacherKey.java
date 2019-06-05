package com.wwz.exam.redis;

public class TeacherKey extends BasePrefix{

	public static final int TOKEN_EXPIRE = 3600*24 * 2;
	private TeacherKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	public static TeacherKey token = new TeacherKey(TOKEN_EXPIRE, "tk");
	public static TeacherKey getById = new TeacherKey(0, "id");
}
