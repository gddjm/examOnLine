package com.wwz.exam.dao;

import com.wwz.exam.domain.Score;
import com.wwz.exam.domain.ScoreInformation;
import com.wwz.exam.domain.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScoreDao {
    @Select("select id,subject_type,exam_start_time,score,exam.exam_id,#{id} as student_id,#{name} as student_name" +
            " from exam join score on exam.exam_id = score.exam_id where student_id = #{id}  order by exam_start_time desc")
    List<ScoreInformation> getByStudent(Student student);

    @Insert("insert into score (student_id,score,exam_id) values (#{studentId},#{score},#{examId})")
    void insert(Score score);

    @Select("select score.id as id,subject_type,exam_start_time,score,exam.exam_id,student_id,student.name as student_name" +
            " from (score join exam on exam.exam_id = score.exam_id) join student on student.id = score.student_id")
    List<ScoreInformation> getAll();

    @Delete("delete from score where id = #{scoreId}")
    void deleteById(@Param("scoreId") long scoreId);

    @Select("select * from score where id = #{scoreId}")
    Score getById(long scoreId);

    @Update("update score set student_id = #{studentId},exam_id = #{examId},score = #{score} where id = #{id}")
    void update(Score score);
}
