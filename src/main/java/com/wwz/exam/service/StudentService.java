package com.wwz.exam.service;

import com.wwz.exam.dao.StudentDao;
import com.wwz.exam.domain.Question;
import com.wwz.exam.domain.Student;
import com.wwz.exam.exception.GlobalException;
import com.wwz.exam.redis.RedisService;
import com.wwz.exam.redis.StudentKey;
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
public class StudentService{

	public static final String COOKI_NAME_TOKEN = "token";

	@Autowired
	StudentDao studentDao;
	
	@Autowired
	RedisService redisService;

	public Student getStudentByCookie(HttpServletRequest request, HttpServletResponse response){

		String paramToken = request.getParameter(StudentService.COOKI_NAME_TOKEN);
		String cookieToken = CookieUtil.getCookieValue(request,StudentService.COOKI_NAME_TOKEN);

		if (StringUtils.isEmpty(paramToken) && StringUtils.isEmpty(cookieToken)){
			return null;
		}
		String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
		return getByToken(response,token);
	}

    public void register(Student student) {
		student.setPassword(MD5Util.formPassToDBPass(student.getPassword(), student.getSalt()));
		studentDao.insert(student);
    }

    public Student getById(long id) {
		//取缓存
		Student stu = redisService.get(StudentKey.getById, ""+id, Student.class);
		if(stu != null) {
			return stu;
		}
		//取数据库
		stu = studentDao.getById(id);
		if(stu != null) {
			redisService.set(StudentKey.getById, ""+id, stu);
		}
		return stu;
	}
	public boolean updatePassword(String token, long id, String formPass) {
		//取user
		Student stu = getById(id);
		if(stu == null) {
			throw new GlobalException(CodeMsg.ACCOUNT_NOT_EXIST);
		}
		//更新数据库
		Student toBeUpdate = new Student();
		toBeUpdate.setId(id);
		toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPass, stu.getSalt()));
		studentDao.updatePassword(toBeUpdate);
		//处理缓存
		redisService.delete(StudentKey.getById, ""+id);
		redisService.delete(StudentKey.token, token);
		return true;
	}


	public Student getByToken(HttpServletResponse response, String token) {
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		Student stu = redisService.get(StudentKey.token, token, Student.class);
		//延长有效期
		if(stu != null) {
			addCookie(response, token, stu);
		}
		return stu;
	}
	

	public String login(HttpServletResponse response, LoginVo loginVo) {
		if(loginVo == null) {
			throw new GlobalException(CodeMsg.SERVER_ERROR);
		}
		String account = loginVo.getAccount();
		String formPass = loginVo.getPassword();
		//判断账号是否存在
		Student user = getById(Long.parseLong(account));
		if(user == null) {
			throw new GlobalException(CodeMsg.ACCOUNT_NOT_EXIST);
		}
		//验证密码
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
		if(!calcPass.equals(dbPass)) {
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}
		//生成cookie
		String token	 = UUIDUtil.uuid();
		addCookie(response, token, user);
		return token;
	}
	
	private void addCookie(HttpServletResponse response, String token, Student user) {
		redisService.set(StudentKey.token, token, user);
		Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
		cookie.setMaxAge(StudentKey.token.expireSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
	}

    public boolean changeInformation(String token, Student student) {
    	if(student == null) {
			throw new GlobalException(CodeMsg.ACCOUNT_NOT_EXIST);
		}
		//更新数据库
		studentDao.updateInformation(student);
		//处理缓存
		redisService.set(StudentKey.token, token, student);
		return true;
    }

    public List<Student> getAll(){
    	return studentDao.getAll();
	}


    public void deleteById(long studentId) {
    	studentDao.deleteById(studentId);
    }

	public void deleteAllById(List<Long> studentIdList) {
    	for (long id : studentIdList){
    		studentDao.deleteById(id);
		}
	}

	public void insert(Student student) {
    	student.setPassword(MD5Util.formPassToDBPass(student.getPassword(), student.getSalt()));
    	studentDao.insert(student);
	}

	public void update(Student student) {
    	studentDao.updateInformation(student);
		Student toBeUpdate = new Student();
		if (student.getPassword() != null && student.getPassword() !="") {
			//更新数据库
			toBeUpdate.setId(student.getId());
			toBeUpdate.setPassword(MD5Util.formPassToDBPass(student.getPassword(), student.getSalt()));
			studentDao.updatePassword(toBeUpdate);
		}
	}

	public Student getByStudentId(long studentId) {
    	return studentDao.getById(studentId);
	}
}
