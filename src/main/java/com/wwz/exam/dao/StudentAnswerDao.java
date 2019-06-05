package com.wwz.exam.dao;

import com.wwz.exam.domain.StudentAnswer;
import com.wwz.exam.domain.StudentAnswerInformation;
import com.wwz.exam.domain.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentAnswerDao {

	@Select("select * from stu_answer where student_id=#{studentId} and text_id=#{textId} and question_number=#{questionNumber}")
	StudentAnswer getById(@Param("studentId")long studentId, @Param("textId")long textId, @Param("questionNumber")long questionNumber);

	@Select("select * from stu_answer where student_id=#{studentId} and text_id=#{textId}")
	List<StudentAnswer> getListById(@Param("studentId")long studentId, @Param("textId")long textId);

	@Insert("insert into stu_answer(student_id,text_id,question_number,question_type,answer) values(#{studentId},#{textId},#{questionNumber},#{questionType},#{answer})")
	void insert(StudentAnswer studentAnswer);

	@Update("update stu_answer set answer=#{answer} where student_id=#{studentId} and text_id=#{textId} and question_number=#{questionNumber}")
	void update(StudentAnswer studentAnswer);

	@Select("select tt.id,tt.text_id,tt.student_id,exam.subject_type,exam.exam_start_time,tt.is_mark from teacher_text tt join exam on tt.text_id = exam.text_id where teacher_id = #{id}")
	List<StudentAnswerInformation> getStudentAnswerInformation(Teacher teacher);

}
