package com.wwz.exam.redis;

public class AdminKey extends BasePrefix{

	public static final int TOKEN_EXPIRE = 3600*24 * 2;
	private AdminKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	public static AdminKey token = new AdminKey(TOKEN_EXPIRE, "tk");
	public static AdminKey getById = new AdminKey(0, "id");
	public static AdminKey getByhome = new AdminKey(0, "home");

}
