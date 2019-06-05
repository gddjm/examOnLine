package com.wwz.exam.dao;

import com.wwz.exam.domain.Question;
import com.wwz.exam.domain.Question;
import com.wwz.exam.domain.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionDao {

	@Select("select text_id from candidate where student_id = #{studentId} and exam_id in" +
			"select exam_id from exam where now()>exam_start_time and now()<exam_end_time")
	Long getTextIdByStudentId(long studentId);

	@Select("select q.*,question_number as question_id from question q left join text_question tq on q.question_id = tq.question_id where text_id = #{textId}")
	List<Question> getQuestionsByTextId(long textId);

	@Delete("delete from question where question_id = #{questionId}")
	void deleteById(long questionId);
	
	@Select("select * from question")
	List<Question> getAll();

	@Select("select * from question where question_id = #{questionId}")
	Question getById(@Param("questionId") long questionId);

	@Update("update question set question = #{question},question_type = #{questionType}," +
			"option1 = #{option1},option2 = #{option2},option3 = #{option3},option4 = #{option4}," +
			"score = #{score},answer = #{answer} where question_id = #{questionId}")
	void updateQuestion(Question question);

	@Insert("insert into question(question,question_type,option1,option2," +
			"option3,option4,score,answer) values(#{question},#{questionType}," +
			"#{option1},#{option2},#{option3},#{option4},#{score},#{answer})")
	void addQuestion(Question question);
}
