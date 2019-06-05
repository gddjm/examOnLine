package com.wwz.exam.controller;


import com.wwz.exam.annotation.AdminIntercept;
import com.wwz.exam.domain.*;
import com.wwz.exam.redis.AdminKey;
import com.wwz.exam.redis.RedisService;
import com.wwz.exam.result.Result;
import com.wwz.exam.service.*;
import com.wwz.exam.util.CookieUtil;
import com.wwz.exam.vo.LoginVo;
import com.wwz.exam.vo.TextVo;
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
@RequestMapping("/admin")
public class AdministratorController {

    @Autowired
    RedisService redisService;
    @Autowired
    AdminService adminService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    ScoreService scoreService;
    @Autowired
    QuestionService questionService;
    @Autowired
    ExamService examService;
    @Autowired
    CandidateService candidateService;
    @Autowired
    TextService textService;
    private static Logger log = LoggerFactory.getLogger(AdministratorController.class);

    @AdminIntercept
    @RequestMapping("/get_admin")
    @ResponseBody
    public Result<Admin> getAdmin(HttpServletRequest request,HttpServletResponse response) {
        Admin admin = adminService.getAdminByCookie(request,response);
        return Result.success(admin);
    }

    @AdminIntercept
    @RequestMapping("/show_student")
    public String showStudent(Model model) {
        List<Student> studentList = studentService.getAll();
        model.addAttribute("studentList", studentList);
        return "admin-stu-information";
    }

    @AdminIntercept
    @RequestMapping("/show_teacher")
    public String showTeacher(Model model) {
        List<Teacher> teacherList = teacherService.getAll();
        model.addAttribute("teacherList", teacherList);
        return "admin-teacher-information";
    }

    @AdminIntercept
    @RequestMapping("/update_student/{studentId}")
    public String updateStudent(Model model, @PathVariable long studentId) {
        Student student = studentService.getByStudentId(studentId);
        model.addAttribute("student", student);
        return "admin-update-student";
    }


    @AdminIntercept
    @RequestMapping("/read_student/{studentId}")
    @ResponseBody
    public Result<Student> readStudent(@PathVariable long studentId) {
        Student student = studentService.getByStudentId(studentId);
        return Result.success(student);
    }

    @AdminIntercept
    @RequestMapping("/delete_student/{studentId}")
    public String deleteStudent( Model model, @PathVariable long studentId) {
        studentService.deleteById(studentId);
        return showStudent(model);
    }

    @AdminIntercept
    @RequestMapping("/delete_student_list")
    @ResponseBody
    public Result<Boolean> deleteStudentList(@RequestBody List<Long> studentIdList) {
        studentService.deleteAllById(studentIdList);
        return Result.success(true);
    }

    @AdminIntercept
    @RequestMapping("/do_add_student")
    @ResponseBody
    public Result<Boolean> doAddStudent(@Valid Student student) {
        studentService.insert(student);
        return Result.success(true);
    }

    @AdminIntercept
    @RequestMapping("/do_update_student")
    @ResponseBody
    public Result<Boolean> doUpdateStudent(@Valid Student student) {
        studentService.update(student);
        return Result.success(true);
    }

    @AdminIntercept
    @RequestMapping("/update_teacher/{teacherId}")
    public String updateTeacher(Model model, @PathVariable long teacherId) {
        Teacher teacher = teacherService.getByTeacherId(teacherId);
        model.addAttribute("teacher", teacher);
        return "admin-update-teacher";
    }

    @AdminIntercept
    @RequestMapping("/read_teacher/{teacherId}")
    @ResponseBody
    public Result<Teacher> readTeacher(@PathVariable long teacherId) {
        Teacher teacher = teacherService.getByTeacherId(teacherId);
        return Result.success(teacher);
    }

    @AdminIntercept
    @RequestMapping("/delete_teacher/{teacherId}")
    public String deleteTeacher(Model model, @PathVariable long teacherId) {
        teacherService.deleteById(teacherId);
        return showTeacher(model);
    }

    @AdminIntercept
    @RequestMapping("/delete_teacher_list")
    @ResponseBody
    public Result<Boolean> deleteTeacherList(@RequestBody List<Long> teacherIdList) {
        teacherService.deleteAllById(teacherIdList);
        return Result.success(true);
    }

    @AdminIntercept
    @RequestMapping("/do_add_teacher")
    @ResponseBody
    public Result<Boolean> doAddTeacher(@Valid Teacher teacher) {
        teacherService.insert(teacher);
        return Result.success(true);
    }

    @AdminIntercept
    @RequestMapping("/do_update_teacher")
    @ResponseBody
    public Result<Boolean> doUpdateTeacher(@Valid Teacher teacher) {
        teacherService.update(teacher);
        return Result.success(true);
    }

    @AdminIntercept
    @RequestMapping("/score")
    public String score(Model model) {
        List<ScoreInformation> scoreInformationList = scoreService.getAll();
        model.addAttribute("scoreInformationList",scoreInformationList);
        return "admin-score";
    }

    @AdminIntercept
    @RequestMapping("/delete_score_list")
    @ResponseBody
    public Result<Boolean> deleteScoreList(@RequestBody List<Long> scoreIdList) {
        scoreService.deleteAllById(scoreIdList);
        return Result.success(true);
    }

    @AdminIntercept
    @RequestMapping("/do_add_score")
    @ResponseBody
    public Result<Boolean> doAddScore(@Valid Score score) {
        scoreService.insert(score);
        return Result.success(true);
    }

    @AdminIntercept
    @RequestMapping("/get_score/{scoreId}")
    @ResponseBody
    public Result<Score> getScore(@PathVariable long scoreId) {
        Score score = scoreService.getById(scoreId);
        return Result.success(score);
    }

    @AdminIntercept
    @RequestMapping("/do_update_score")
    @ResponseBody
    public Result<Boolean> doUpdateScore(@Valid Score score) {
        scoreService.update(score);
        return Result.success(true);
    }

    @AdminIntercept
    @RequestMapping("/read_score/{scoreId}")
    @ResponseBody
    public Result<Score> readScore(@PathVariable long scoreId) {
        Score score = scoreService.getById(scoreId);
        return Result.success(score);
    }

    @AdminIntercept
    @RequestMapping("/delete_score/{scoreId}")
    public String deleteScore(HttpServletRequest request,Model model, @PathVariable long scoreId) {
        String token = CookieUtil.getCookieValue(request, AdminService.COOKI_NAME_TOKEN);
        boolean exists = redisService.exists(AdminKey.token, token);
        if (exists) {
            scoreService.deleteById(scoreId);
        }
        return score(model);
    }

    @AdminIntercept
    @RequestMapping("/questions")
    public String questions(Model model) {
        List<Question> questionList = questionService.getAll();
        model.addAttribute("questionList", questionList);
        return "admin-questions";
    }

    @AdminIntercept
    @RequestMapping("/update_question/{questionId}")
    @ResponseBody
    public Result<Question> updateQuestion(@PathVariable long questionId) {
        Question question = questionService.getById(questionId);
        return Result.success(question);
    }

    @AdminIntercept
    @RequestMapping(value = "/get_question/{questionId}", method = RequestMethod.GET)
    @ResponseBody
    public Result<Question> getQuestion(@PathVariable("questionId") long questionId) {
        Question question = questionService.getById(questionId);
        return Result.success(question);
    }

    @AdminIntercept
    @RequestMapping("/delete_question/{questionId}")
    public String deleteQuestion(Model model, @PathVariable long questionId) {
        questionService.deleteById(questionId);
        return questions(model);
    }

    @AdminIntercept
    @RequestMapping("/delete_question_list")
    @ResponseBody
    public Result<Boolean> deleteQuestionList(@RequestBody List<Long> questionIdList) {
        questionService.deleteAllById(questionIdList);
        return Result.success(true);
    }

    @AdminIntercept
    @RequestMapping("/do_add_question")
    @ResponseBody
    public Result<Boolean> doAddQuestion(@Valid Question question) {
        questionService.addQuestion(question);
        return Result.success(true);
    }

    @AdminIntercept
    @RequestMapping("/do_update_question")
    @ResponseBody
    public Result<Boolean> doUpdateQuestion(@Valid Question question) {
        questionService.updateQuestion(question);
        return Result.success(true);
    }


    @AdminIntercept
    @RequestMapping("/do_change_information")
    @ResponseBody
    public Result<Boolean> doChangeInformation(HttpServletRequest request, Model model, @Valid Admin admin) {
        model.addAttribute("admin", admin);
        String token = CookieUtil.getCookieValue(request, AdminService.COOKI_NAME_TOKEN);
        adminService.changeInformation(token, admin);
        return Result.success(true);
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Admin admin = adminService.getAdminByCookie(request,response);
        String token = CookieUtil.getCookieValue(request, AdminService.COOKI_NAME_TOKEN);
        if (admin != null) {
            long id = admin.getId();
            //处理缓存
            redisService.delete(AdminKey.getById, "" + id);
            redisService.delete(AdminKey.token, token);
        }
        return "login";
    }

    @AdminIntercept
    @RequestMapping("/update_password")
    @ResponseBody
    public Result<Long> updatePassword(HttpServletRequest request,HttpServletResponse response) {
        Admin admin =adminService.getAdminByCookie(request,response);
        return Result.success(admin.getId());
    }

    @AdminIntercept
    @RequestMapping("/do_update_password")
    @ResponseBody
    public Result<Boolean> doUpdatePassword(HttpServletRequest request,HttpServletResponse response, Model model, @Valid LoginVo loginVo) {
        Admin admin = adminService.getAdminByCookie(request,response);
        model.addAttribute("admin", admin);
        String token = CookieUtil.getCookieValue(request, AdminService.COOKI_NAME_TOKEN);
        adminService.updatePassword(token, Long.parseLong(loginVo.getAccount()), loginVo.getPassword());
        return Result.success(true);
    }

    @AdminIntercept
    @RequestMapping("/make_text")
    @ResponseBody
    public Result<Boolean> makeText(HttpServletResponse response,HttpServletRequest request,@RequestBody List<Question> checkQuestionList) {
        Admin admin = adminService.getAdminByCookie(request,response);
        long adminId = admin.getId();
        textService.set(adminId,checkQuestionList);
        return Result.success(true);
    }

    @AdminIntercept
    @RequestMapping("/to_make_text")
    public String toMakeText(Model model,HttpServletResponse response,HttpServletRequest request) {
        Admin admin = adminService.getAdminByCookie(request,response);
        long adminId = admin.getId();
        List<Question> checkQuestionList = textService.get(adminId,Question.class);
        model.addAttribute("questionsList",checkQuestionList);
        return "admin-make-text";
    }

    @AdminIntercept
    @RequestMapping("/do_make_text")
    @ResponseBody
    public Result<Boolean> do_makeText(@RequestBody TextVo textVo) {
        log.info("text"+textVo.toString());
        textService.makeText(textVo);
        return Result.success(true);
    }


    @AdminIntercept
    @RequestMapping("/exam")
    public String exam(Model model) {
        List<Exam> examList = examService.getAll();
        model.addAttribute("examList", examList);
        return "admin-exam";
    }


    @AdminIntercept
    @RequestMapping("/update_exam/{examId}")
    @ResponseBody
    public Result<Exam> updateExam(@PathVariable long examId) {
        Exam exam = examService.getById(examId);
        return Result.success(exam);
    }

    @AdminIntercept
    @RequestMapping(value = "/get_exam/{examId}", method = RequestMethod.GET)
    @ResponseBody
    public Result<Exam> getExam(@PathVariable("examId") long examId) {
        Exam exam = examService.getById(examId);
        log.info(exam.toString());
        return Result.success(exam);
    }

    @AdminIntercept
    @RequestMapping("/delete_exam/{examId}")
    public String deleteExam(Model model, @PathVariable long examId) {
        examService.deleteById(examId);
        return exam(model);
    }

    @AdminIntercept
    @RequestMapping("/delete_exam_list")
    @ResponseBody
    public Result<Boolean> deleteExamList(@RequestBody List<Long> examIdList) {
        examService.deleteAllById(examIdList);
        return Result.success(true);
    }

    @AdminIntercept
    @RequestMapping("/do_add_exam")
    @ResponseBody
    public Result<Boolean> doAddExam(@Valid Exam exam) {
        examService.addExam(exam);
        return Result.success(true);
    }

    @AdminIntercept
    @RequestMapping("/do_update_exam")
    @ResponseBody
    public Result<Boolean> doUpdateExam(@Valid Exam exam) {
        examService.updateExam(exam);
        return Result.success(true);
    }

}
