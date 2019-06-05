package com.wwz.exam.redis;

public class TextKey extends BasePrefix{

	public static final int EXAM_EXPIRE = 3600*3;
	private TextKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	public static TextKey getByAdminId = new TextKey(EXAM_EXPIRE, "adminid");
}
