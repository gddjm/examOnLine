package com.wwz.exam.service;

import com.wwz.exam.dao.AdminDao;
import com.wwz.exam.domain.Admin;
import com.wwz.exam.exception.GlobalException;
import com.wwz.exam.redis.RedisService;
import com.wwz.exam.redis.AdminKey;
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

@Service
public class AdminService{


	public static final String COOKI_NAME_TOKEN = "token";

	@Autowired
	AdminDao adminDao;

	@Autowired
	RedisService redisService;

	public Admin getAdminByCookie(HttpServletRequest request, HttpServletResponse response){

		String paramToken = request.getParameter(AdminService.COOKI_NAME_TOKEN);
		String cookieToken = CookieUtil.getCookieValue(request,AdminService.COOKI_NAME_TOKEN);

		if (StringUtils.isEmpty(paramToken) && StringUtils.isEmpty(cookieToken)){
			return null;
		}
		String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
		return getByToken(response,token);
	}

	public void register(Admin admin) {
		admin.setPassword(MD5Util.formPassToDBPass(admin.getPassword(), admin.getSalt()));
		adminDao.insert(admin);
	}

	public Admin getById(long id) {
		//取缓存
		Admin admin = redisService.get(AdminKey.getById, ""+id, Admin.class);
		if(admin != null) {
			return admin;
		}
		//取数据库
		admin = adminDao.getById(id);
		if(admin != null) {
			redisService.set(AdminKey.getById, ""+id, admin);
		}
		return admin;
	}
	public boolean updatePassword(String token, long id, String formPass) {
		//取user
		Admin admin = getById(id);
		if(admin == null) {
			throw new GlobalException(CodeMsg.ACCOUNT_NOT_EXIST);
		}
		//更新数据库
		Admin toBeUpdate = new Admin();
		toBeUpdate.setId(id);
		toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPass, admin.getSalt()));
		adminDao.update(toBeUpdate);
		//处理缓存
		redisService.delete(AdminKey.getById, ""+id);
		redisService.delete(AdminKey.token, token);
		return true;
	}


	public Admin getByToken(HttpServletResponse response, String token) {
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		Admin admin = redisService.get(AdminKey.token, token, Admin.class);
		//延长有效期
		if(admin != null) {
			addCookie(response, token, admin);
		}
		return admin;
	}


	public String login(HttpServletResponse response, LoginVo loginVo) {
		if(loginVo == null) {
			throw new GlobalException(CodeMsg.SERVER_ERROR);
		}
		String account = loginVo.getAccount();
		String formPass = loginVo.getPassword();
		//判断账号是否存在
		Admin user = getById(Long.parseLong(account));
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

	private void addCookie(HttpServletResponse response, String token, Admin user) {
		redisService.set(AdminKey.token, token, user);
		Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
		cookie.setMaxAge(AdminKey.token.expireSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public boolean changeInformation(String token, Admin admin) {
		if(admin == null) {
			throw new GlobalException(CodeMsg.ACCOUNT_NOT_EXIST);
		}
		//更新数据库
		adminDao.updateInformation(admin);
		//处理缓存
		redisService.set(AdminKey.token, token, admin);
		return true;
	}
}
