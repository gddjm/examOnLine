package com.wwz.exam.dao;

import com.wwz.exam.domain.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherDao {

	@Select("select * from teacher where id = #{id}")
	Teacher getById(@Param("id") long id);

	@Update("update teacher set password = #{password} where id = #{id}")
	void update(Teacher toBeUpdate);
	@Insert("insert into teacher(id,name,sex,age,phone,QQ,email,password,salt) values(#{id},#{name},#{sex},#{age},#{phone},#{qq},#{email},#{password},#{salt})")
	void insert(Teacher teacher);
	@Update("update teacher set name = #{name},sex=#{sex},age=#{age},phone=#{phone},QQ=#{qq},email=#{email} where id = #{id}")
	void updateInformation(Teacher teacher);


	@Select("select * from teacher")
	List<Teacher> getAll();

	@Delete("delete from teacher where id = #{teacherId}")
	void deleteById(@Param("teacherId") long teacherId);

	@Update("update teacher set password = #{password} where id = #{id}")
	void updatePassword(Teacher toBeUpdate);
}
