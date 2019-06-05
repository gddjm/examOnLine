package com.wwz.exam.dao;

import com.wwz.exam.domain.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentDao {

	@Select("select * from student where id = #{id}")
	Student getById(@Param("id") long id);

	@Update("update student set password = #{password} where id = #{id}")
	void updatePassword(Student toBeUpdate);

	@Insert("insert into student(id,name,sex,age,phone,qq,email,password,salt) values(#{id},#{name},#{sex},#{age},#{phone},#{qq},#{email},#{password},#{salt})")
	void insert(Student student);

	@Update("update student set name = #{name},sex=#{sex},age=#{age},phone=#{phone},qq=#{qq},email=#{email} where id = #{id}")
	void updateInformation(Student student);

	@Select("select * from student")
	List<Student> getAll();

	@Delete("delete from student where id = #{studentId}")
    void deleteById(@Param("studentId") long studentId);
}
