package com.wwz.exam.dao;

import com.wwz.exam.domain.Admin;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminDao {

	@Select("select * from admin where id = #{id}")
	Admin getById(@Param("id") long id);

	@Update("update admin set password = #{password} where id = #{id}")
	void update(Admin toBeUpdate);

	@Insert("insert into admin(id,name,sex,age,phone,QQ,email,password,salt) values(#{id},#{name},#{sex},#{age},#{phone},#{qq},#{email},#{password},#{salt})")
	void insert(Admin admin);

	@Update("update admin set name = #{name},sex=#{sex},age=#{age},phone=#{phone},QQ=#{qq},email=#{email} where id = #{id}")
	void updateInformation(Admin admin);

}
