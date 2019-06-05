package com.wwz.exam.service;

import com.wwz.exam.dao.TeacherDao;
import com.wwz.exam.domain.Teacher;
import com.wwz.exam.exception.GlobalException;
import com.wwz.exam.redis.RedisService;
import com.wwz.exam.redis.TeacherKey;
import com.wwz.exam.result.CodeMsg;
import com.wwz.exam.util.CookieUtil;
import com.wwz.exam.util.MD5Util;
import com.wwz.exam.util.UUIDUtil;
import com.wwz.exam.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class TeacherService{
	
	
	public static final String COOKI_NAME_TOKEN = "token";

	@Autowired
	TeacherDao teacherDao;
	
	@Autowired
	RedisService redisService;

	public Teacher getTeacherByCookie(HttpServletRequest request, HttpServletResponse response){

		String paramToken = request.getParameter(TeacherService.COOKI_NAME_TOKEN);
		String cookieToken = CookieUtil.getCookieValue(request,TeacherService.COOKI_NAME_TOKEN);

		if (StringUtils.isEmpty(paramToken) && StringUtils.isEmpty(cookieToken)){
			return null;
		}
		String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
		return getByToken(response,token);
	}
	
	public Teacher getById(long id) {
		//取缓存
		Teacher teacher = redisService.get(TeacherKey.getById, ""+id, Teacher.class);
		if(teacher != null) {
			return teacher;
		}
		//取数据库
		teacher = teacherDao.getById(id);
		if(teacher != null) {
			redisService.set(TeacherKey.getById, ""+id, teacher);
		}
		return teacher;
	}
	public boolean updatePassword(String token, long id, String formPass) {
		//取user
		Teacher teacher = getById(id);
		if(teacher == null) {
			throw new GlobalException(CodeMsg.ACCOUNT_NOT_EXIST);
		}
		//更新数据库
		Teacher toBeUpdate = new Teacher();
		toBeUpdate.setId(id);
		toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPass, teacher.getSalt()));
		teacherDao.update(toBeUpdate);
		//处理缓存
		redisService.delete(TeacherKey.getById, ""+id);
		redisService.delete(TeacherKey.token, token);
		return true;
	}

	public Teacher getByToken(HttpServletResponse response, String token) {
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		Teacher teacher = redisService.get(TeacherKey.token, token, Teacher.class);
		//延长有效期
		if(teacher != null) {
			addCookie(response, token, teacher);
		}
		return teacher;
	}
	

	public String login(HttpServletResponse response, LoginVo loginVo) {
		if(loginVo == null) {
			throw new GlobalException(CodeMsg.SERVER_ERROR);
		}
		String account = loginVo.getAccount();
		String formPass = loginVo.getPassword();
		//判断账号是否存在
		Teacher teacher = getById(Long.parseLong(account));
		if(teacher == null) {
			throw new GlobalException(CodeMsg.ACCOUNT_NOT_EXIST);
		}
		//验证密码
		String dbPass = teacher.getPassword();
		String saltDB = teacher.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
		if(!calcPass.equals(dbPass)) {
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}
		//生成cookie
		String token	 = UUIDUtil.uuid();
		addCookie(response, token, teacher);
		return token;
	}
	
	private void addCookie(HttpServletResponse response, String token, Teacher teacher) {
		redisService.set(TeacherKey.token, token, teacher);
		Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
		cookie.setMaxAge(TeacherKey.token.expireSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	public boolean changeInformation(String token, Teacher teacher) {
		if(teacher == null) {
			throw new GlobalException(CodeMsg.ACCOUNT_NOT_EXIST);
		}
		//更新数据库
		teacherDao.updateInformation(teacher);
		//处理缓存
		redisService.set(TeacherKey.token, token, teacher);
		return true;
	}
	public List<Teacher> getAll(){
		return teacherDao.getAll();
	}

	public Teacher getByTeacherId(long teacherId) {
		return teacherDao.getById(teacherId);

	}

	public void deleteById(long teacherId) {
		teacherDao.deleteById(teacherId);
	}

	public void deleteAllById(List<Long> teacherIdList) {
		for (long id : teacherIdList){
			teacherDao.deleteById(id);
		}
	}

	public void insert(Teacher teacher) {
		teacher.setPassword(MD5Util.formPassToDBPass(teacher.getPassword(), teacher.getSalt()));
		teacherDao.insert(teacher);
	}

	public void update(Teacher teacher) {
		teacherDao.updateInformation(teacher);
		Teacher toBeUpdate = new Teacher();
		if (teacher.getPassword() != null && teacher.getPassword() !="") {
			//更新数据库
			toBeUpdate.setId(teacher.getId());
			toBeUpdate.setPassword(MD5Util.formPassToDBPass(teacher.getPassword(), teacher.getSalt()));
			teacherDao.updatePassword(toBeUpdate);
		}
	}

	public void register(Teacher teacher) {
		teacher.setPassword(MD5Util.formPassToDBPass(teacher.getPassword(), teacher.getSalt()));
		teacherDao.insert(teacher);
	}
}
