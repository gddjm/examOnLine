package com.wwz.exam.dao;


import com.wwz.exam.domain.Exam;
import com.wwz.exam.domain.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ExamDao {

	@Select("select * from exam where exam_id = #{textId} and text_id = #{textId}")
	Exam getById(@Param("examId") long examId,@Param("textId") long textId);

	@Select("select exam.* from  exam join candidate on exam.exam_id = candidate.exam_id where student_id = #{studentId} and " +
			"(unix_timestamp(exam_start_time)-unix_timestamp(now())) <=900 and exam_end_time >= now()")
	Exam getExamByStudentId(@Param("studentId") long studentId);

	@Select("select score from text where id = #{textId}")
	int getScore(@Param("textId") long textId);

	@Select("select * from  exam where exam_start_time >= now()")
	List<Exam> getExam();

	@Insert("insert into exam(exam_id,text_id,exam_start_time,exam_end_time,subject_type) values " +
			"(#{examId},#{textId},#{examStartTime},#{examEndTime},#{subjectType})")
    void insert(Exam exam);

	@Select("select * from  exam")
	List<Exam> getAll();


	@Select("select * from exam where exam_id = #{examId}")
	Exam getByExamId(@Param("examId") long examId);

	@Delete("delete from exam where exam_id = #{examId}")
	void deleteById(long examId);

	@Update("update exam set text_id = #{textId},exam_start_time = #{examStartTime}," +
			"exam_end_time = #{examEndTime},subject_type = #{subjectType} where exam_id = #{examId}")
	void updateExam(Exam exam);
}
