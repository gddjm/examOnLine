package com.wwz.exam.redis;

public class QuestionKey extends BasePrefix{

	public static final int EXAM_EXPIRE = 3600*3;
	private QuestionKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	public static QuestionKey getById = new QuestionKey(EXAM_EXPIRE, "id");
	public static QuestionKey getByTextId = new QuestionKey(EXAM_EXPIRE, "text_id");

}
