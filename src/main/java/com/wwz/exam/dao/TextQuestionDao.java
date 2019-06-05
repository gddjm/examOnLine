package com.wwz.exam.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TextQuestionDao {

	@Insert("insert into text_question(text_id,question_id,question_number) values " +
			"(#{textId},#{questionId},#{questionNumber})")
	void insert(@Param("textId")long textId, @Param("questionId")long questionId, @Param("questionNumber")long questionNumber);
}
