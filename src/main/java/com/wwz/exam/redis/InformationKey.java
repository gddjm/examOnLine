package com.wwz.exam.redis;

public class InformationKey extends BasePrefix{

	public static final int TOKEN_EXPIRE = 3600*24 * 2;
	private InformationKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	public static InformationKey token = new InformationKey(TOKEN_EXPIRE, "tk");
	public static InformationKey getStudentInformation = new InformationKey(0, "gsi");
}
