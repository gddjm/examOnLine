package com.wwz.exam.dao;


import com.wwz.exam.domain.Exam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TextDao {

	@Insert("insert into text(id,text_type,score) value(#{text_id},#{type},#{score})")
	void insert(@Param("text_id")long text_id,@Param("type")String type, @Param("score")int score);
}
