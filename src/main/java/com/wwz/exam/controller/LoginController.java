package com.wwz.exam.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.wwz.exam.result.CodeMsg;
import com.wwz.exam.result.Result;
import com.wwz.exam.service.AdminService;
import com.wwz.exam.service.StudentService;
import com.wwz.exam.service.TeacherService;
import com.wwz.exam.util.RandomValidateCode;
import com.wwz.exam.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/user")
public class LoginController {
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    AdminService adminService;
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/login_tip")
    public String loginTip() {
        return "login-tip";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletRequest request, HttpServletResponse response, @Valid LoginVo loginVo) {
        log.info(loginVo.toString());

        HttpSession session = request.getSession();
        String random = (String) session.getAttribute(RandomValidateCode.RANDOMCODEKEY);
        log.info(random);
        if (!random.equalsIgnoreCase(loginVo.getValidateCode())) {
            return Result.error(CodeMsg.VALIDATE_CODE_ERROR);
        }
        String token = "";
        //登录
        if (loginVo.getPower().equals("student")) {
            token = studentService.login(response, loginVo);
        } else if (loginVo.getPower().equals("teacher")) {
            token = teacherService.login(response, loginVo);
        } else if (loginVo.getPower().equals("admin")) {
            token = adminService.login(response, loginVo);
        }
        return Result.success(token);
    }

    /**
     * 登录页面生成验证码
     */
    @RequestMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
