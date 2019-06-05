package com.wwz.exam.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CandidateDao {

    @Select("select text_id from candidate where student_id = #{studentId} and exam_id = #{examId}")
    long getTextId(@Param("studentId")long studentId, @Param("examId")long examId);

    @Select("select exam_id from candidate where student_id = #{studentId} and text_id = #{textId}")
    long getExamId(@Param("studentId") long studentId, @Param("textId") long textId);

    @Insert("insert into candidate(student_id,exam_id,text_id) values(#{studentId},#{examId},#{textId})")
    void insert(@Param("studentId")long studentId, @Param("examId")long examId, @Param("textId")long textId);

    @Select("select count(*) from candidate where student_id=#{studentId} and exam_id=#{examId} and text_id=#{textId}")
    long getById(@Param("studentId")long studentId, @Param("examId")long examId, @Param("textId")long textId);

//    @Select("select exam_id from candidate where student_id = #{studentId}")
//    long getExamId(@Param("studentId")long studentId);
}
