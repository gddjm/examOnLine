package com.wwz.exam.controller;

import com.wwz.exam.annotation.AdminIntercept;
import com.wwz.exam.annotation.TeacherIntercept;
import com.wwz.exam.domain.*;
import com.wwz.exam.redis.RedisService;
import com.wwz.exam.redis.TeacherKey;
import com.wwz.exam.result.Result;
import com.wwz.exam.service.*;
import com.wwz.exam.util.CookieUtil;
import com.wwz.exam.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    RedisService redisService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentAnswerService studentAnswerService;
    @Autowired
    QuestionService questionService;
    @Autowired
    ScoreService scoreService;
    @Autowired
    StudentService studentService;
    @Autowired
    ExamService examService;
    @Autowired
    TeacherTextService teacherTextService;
    @Autowired
    CandidateService candidateService;
    private static Logger log = LoggerFactory.getLogger(TeacherController.class);

    @TeacherIntercept
    @RequestMapping("/get_teacher")
    @ResponseBody
    public Result<Teacher> getTeacher(HttpServletRequest request,HttpServletResponse response,Model model) {
        Teacher teacher = teacherService.getTeacherByCookie(request,response);
        return Result.success(teacher);
    }

    @TeacherIntercept
    @RequestMapping("/mark")
    public String mark(HttpServletRequest request,HttpServletResponse response,Model model) {
        Teacher teacher = teacherService.getTeacherByCookie(request,response);
        List<StudentAnswerInformation> studentAnswerInformationList = studentAnswerService.getStudentAnswerInformation(teacher);
        model.addAttribute("studentAnswerInformationList", studentAnswerInformationList);
        return "teacher-mark";
    }

    @TeacherIntercept
    @RequestMapping("/mark_text")
    public String mark(Model model, @RequestParam long studentId, @RequestParam long textId) {
        model.addAttribute("textId", textId);
        List<StudentAnswer> studentAnswerList = studentAnswerService.getStudentAnswerList(studentId, textId);
        model.addAttribute("studentAnswerList", studentAnswerList);
        model.addAttribute("studentId", studentId);
        long examId = candidateService.getExamId(studentId,textId);
        model.addAttribute("examId", examId);

        List<Question> questionList = questionService.getQuestionsByTextId(textId);
        model.addAttribute("questionList", questionList);
        return "teacher-mark-text";
    }

    @TeacherIntercept
    @RequestMapping("/submit_mark")
    @ResponseBody
    public Result<Boolean> submitMark(@Valid Score score){
        scoreService.insert(score);
        teacherTextService.setIsMark(score.getStudentId(),score.getExamId());
        return Result.success(true);
    }

    @TeacherIntercept
    @RequestMapping("/do_change_information")
    @ResponseBody
    public Result<Boolean> doChangeInformation(HttpServletRequest request, Model model, @Valid Teacher teacher) {
        model.addAttribute("teacher",teacher);
        String token = CookieUtil.getCookieValue(request, TeacherService.COOKI_NAME_TOKEN);
        teacherService.changeInformation(token, teacher);
        return Result.success(true);
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response, Model model) {
        Teacher teacher = teacherService.getTeacherByCookie(request,response);
        String token = CookieUtil.getCookieValue(request, TeacherService.COOKI_NAME_TOKEN);
        if (teacher != null) {
            long id = teacher.getId();
            //处理缓存
            redisService.delete(TeacherKey.getById, "" + id);
            redisService.delete(TeacherKey.token, token);
        }
        return "login";
    }


    @TeacherIntercept
    @RequestMapping("/do_update_password")
    @ResponseBody
    public Result<Boolean> doUpdatePassword(HttpServletRequest request,HttpServletResponse response,Model model, @Valid LoginVo loginVo) {
        Teacher teacher = teacherService.getTeacherByCookie(request,response);
        model.addAttribute("teacher",teacher);
        String token = CookieUtil.getCookieValue(request, TeacherService.COOKI_NAME_TOKEN);
        teacherService.updatePassword(token, Long.parseLong(loginVo.getAccount()), loginVo.getPassword());
        return Result.success(true);
    }

    @TeacherIntercept
    @RequestMapping("/questions")
    public String questions(Model model) {
        List<Question> questionList = questionService.getAll();
        model.addAttribute("questionList", questionList);
        return "teacher-questions";
    }

    @TeacherIntercept
    @RequestMapping("/update_question/{questionId}")
    @ResponseBody
    public Result<Question> updateQuestion(@PathVariable long questionId) {
        Question question = questionService.getById(questionId);
        return Result.success(question);
    }

    @TeacherIntercept
    @RequestMapping(value = "/get_question/{questionId}", method = RequestMethod.GET)
    @ResponseBody
    public Result<Question> getQuestion(@PathVariable("questionId") long questionId) {
        Question question = questionService.getById(questionId);
        return Result.success(question);
    }

    @TeacherIntercept
    @RequestMapping("/delete_question/{questionId}")
    public String deleteQuestion(Model model, @PathVariable long questionId) {
        questionService.deleteById(questionId);
        return questions(model);
    }

    @TeacherIntercept
    @RequestMapping("/delete_question_list")
    @ResponseBody
    public Result<Boolean> deleteQuestionList(@RequestBody List<Long> questionIdList) {
        questionService.deleteAllById(questionIdList);
        return Result.success(true);
    }

    @TeacherIntercept
    @RequestMapping("/do_add_question")
    @ResponseBody
    public Result<Boolean> doAddQuestion(@Valid Question question) {
        questionService.addQuestion(question);
        return Result.success(true);
    }

    @TeacherIntercept
    @RequestMapping("/do_update_question")
    @ResponseBody
    public Result<Boolean> doUpdateQuestion(@Valid Question question) {
        questionService.updateQuestion(question);
        return Result.success(true);
    }

}
