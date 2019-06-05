package com.wwz.exam.controller;

import com.wwz.exam.annotation.StudentIntercept;
import com.wwz.exam.domain.*;
import com.wwz.exam.redis.InformationKey;
import com.wwz.exam.redis.RedisService;
import com.wwz.exam.redis.StudentKey;
import com.wwz.exam.result.CodeMsg;
import com.wwz.exam.result.Result;
import com.wwz.exam.service.*;
import com.wwz.exam.util.CookieUtil;
import com.wwz.exam.util.RandomValidateCode;
import com.wwz.exam.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.*;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/stu")
public class StudentController {
    @Autowired
    RedisService redisService;
    @Autowired
    StudentService studentService;
    @Autowired
    ScoreService scoreService;
    @Autowired
    QuestionService questionService;
    @Autowired
    StudentAnswerService studentAnswerService;
    @Autowired
    ExamService examService;
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    CandidateService candidateService;

    private static Logger log = LoggerFactory.getLogger(StudentController.class);

    @StudentIntercept
    @RequestMapping("/get_student")
    @ResponseBody
    public Result<Student> getStudent(HttpServletRequest request, HttpServletResponse response) {
        Student student = studentService.getStudentByCookie(request, response);
        if (student == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        return Result.success(student);
    }

    @StudentIntercept
    @RequestMapping("/start_exam")
    public String startExam(HttpServletRequest request, HttpServletResponse response, Model model) {
        Student student = studentService.getStudentByCookie(request, response);
        Exam exam = examService.getExamByStudentId(student.getId());

        if (exam == null) {
            return "exam-time-no-up";
        }

        log.info(exam.toString());

        long now = System.currentTimeMillis();
        long startTime = exam.getExamStartTime().getTime();
        long endTime = exam.getExamEndTime().getTime();
//            考试前15分钟可进场，这是进入考场的最晚时间,时间单位：毫秒
        long prepareTime = startTime - 15 * 60 * 1000;

        if (now < prepareTime) {//未到考试时间
            return "exam-time-no-up";
        } else if (now > endTime) {//考试结束
            return "exam-time-out";
        } else if (now > startTime) {//进行考试
            List<Question> questionList = questionService.getQuestionsByTextId(exam.getTextId());
            if (questionList == null || questionList.isEmpty()){
                return "student-no-sign";
            }
            int score = examService.getScore(exam.getTextId());

            model.addAttribute("now", now);
            model.addAttribute("student", student);
            model.addAttribute("questionsList", questionList);
            model.addAttribute("exam", exam);
            model.addAttribute("score", score);
            return "student-exam";
        } else {//准备考试
            long remaining_time = startTime - now;
            model.addAttribute("remaining_time", remaining_time);
            return "exam-prepare";
        }

    }

    @StudentIntercept
    @RequestMapping("do_exam_submit")
    @ResponseBody
    public Result<Boolean> doExamSubmit(@RequestBody List<StudentAnswer> answerList) {
        studentAnswerService.insert(answerList);
        return Result.success(true);
    }

    @StudentIntercept
    @RequestMapping("/score")
    public String score(HttpServletRequest request, HttpServletResponse response, Model model) {
        Student student = studentService.getStudentByCookie(request, response);
        List<ScoreInformation> scoreInformationList = scoreService.getByStudent(student);
        model.addAttribute("scoreInformationList", scoreInformationList);
        return "student-score";
    }

    @StudentIntercept
    @RequestMapping("/stu_information")
    @ResponseBody
    public Result<Student> stuInformation(HttpServletRequest request, HttpServletResponse response) {
        Student student = studentService.getStudentByCookie(request, response);
        return Result.success(student);
    }

    @StudentIntercept
    @RequestMapping("/do_change_information")
    @ResponseBody
    public Result<Boolean> doChangeInformation(HttpServletRequest request, @Valid Student student) {
        String token = CookieUtil.getCookieValue(request, StudentService.COOKI_NAME_TOKEN);
        studentService.changeInformation(token, student);
        return Result.success(true);
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Student student = studentService.getStudentByCookie(request, response);
        String token = CookieUtil.getCookieValue(request, StudentService.COOKI_NAME_TOKEN);
        if (student != null) {
            long id = student.getId();
            //处理缓存
            redisService.delete(StudentKey.getById, "" + id);
            redisService.delete(StudentKey.token, token);
        }
        return "login";
    }


    @StudentIntercept
    @RequestMapping("/do_update_password")
    @ResponseBody
    public Result<Boolean> doUpdatePassword(HttpServletRequest request, @Valid LoginVo loginVo) {
        HttpSession session = request.getSession();
        String random = (String) session.getAttribute(RandomValidateCode.RANDOMCODEKEY);
        log.info(random);
        if (!random.equalsIgnoreCase(loginVo.getValidateCode())) {
            return Result.error(CodeMsg.VALIDATE_CODE_ERROR);
        }
        String token = CookieUtil.getCookieValue(request, StudentService.COOKI_NAME_TOKEN);
        studentService.updatePassword(token, Long.parseLong(loginVo.getAccount()), loginVo.getPassword());
        return Result.success(true);
    }

    @StudentIntercept
    @RequestMapping("/sign_up")
    public String signUp(Model model) {
        List<Exam> examList = examService.getExam();
        model.addAttribute("examList", examList);
        return "student-sign-up";
    }


    @StudentIntercept
    @RequestMapping("/do_sign_up")
    public String doSignUp(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam long examId) {
        Student student = studentService.getStudentByCookie(request, response);
        long studentId = student.getId();
        if (candidateService.exists(studentId, examId, examId)){
            return "student-sign-up-repeat";
        }else{
            candidateService.insert(studentId, examId, examId);
            return "student-sign-up-success";
        }
    }

}
