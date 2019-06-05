package com.wwz.exam.dao;

import com.wwz.exam.domain.Student;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TeacherTextDao {

	@Update("update teacher_text set is_mark = 1 where student_id = #{studentId} and text_id = #{textId}")
	void setIsMark(@Param("studentId") long studentId,@Param("textId")long textId);


}
