package com.wwz.exam.redis;

public class ExamKey extends BasePrefix{

	public static final int EXAM_EXPIRE = 3600*3;
	private ExamKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	public static ExamKey getByStudentId = new ExamKey(EXAM_EXPIRE, "stuid");
}
