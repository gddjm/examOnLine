package com.wwz.exam.controller;

import com.wwz.exam.domain.Student;
import com.wwz.exam.domain.Teacher;
import com.wwz.exam.result.CodeMsg;
import com.wwz.exam.result.Result;
import com.wwz.exam.service.StudentService;
import com.wwz.exam.service.TeacherService;
import com.wwz.exam.util.RandomValidateCode;
import com.wwz.exam.vo.RegisterVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/to_register")
    public String to_register() {
        return "register";
    }

    @RequestMapping("/do_register")
    @ResponseBody
    public Result<Boolean> doRegister(HttpServletRequest request, @Valid @RequestBody RegisterVo registerVo) {
        HttpSession session = request.getSession();
        String random = (String) session.getAttribute(RandomValidateCode.RANDOMCODEKEY);
        if (!random.equalsIgnoreCase(registerVo.getValidateCode())) {
            return Result.error(CodeMsg.VALIDATE_CODE_ERROR);
        }
        studentService.register(registerVo.getStudent());
        return Result.success(true);
    }
    @RequestMapping("/do_teacher_register")
    @ResponseBody
    public Result<Boolean> doTeacherRegister( @Valid Teacher teacher) {
        teacherService.register(teacher);
        return Result.success(true);
    }
    
}
