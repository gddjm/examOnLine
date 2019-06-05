package com.wwz.exam.service;

import com.wwz.exam.dao.ExamDao;
import com.wwz.exam.domain.Exam;
import com.wwz.exam.domain.Question;
import com.wwz.exam.redis.ExamKey;
import com.wwz.exam.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {
    @Autowired
    ExamDao examDao;
    @Autowired
    CandidateService candidateService;
    @Autowired
    RedisService redisService;
    public Exam getById(long examId,long textId){
        return examDao.getById(examId,textId);
    }

    public Exam getById(long examId) {
        return examDao.getByExamId(examId);
    }


    public Exam getExamByStudentId(long studentId){
        Exam exam = redisService.get(ExamKey.getByStudentId,""+studentId,Exam.class);
        if (exam != null){
            return exam;
        }
        exam = examDao.getExamByStudentId(studentId);
        if (exam != null){
            redisService.set(ExamKey.getByStudentId,""+studentId,exam);
        }
        return exam;
    }

    public int getScore(long textId) {
        return examDao.getScore(textId);
    }

    public List<Exam> getExam() {
        return examDao.getExam();
    }

    public void insert(Exam exam){
        examDao.insert(exam);
    }

    public List<Exam> getAll() {
        return examDao.getAll();
    }

    public void deleteById(long examId) {
        examDao.deleteById(examId);
    }

    public void deleteAllById(List<Long> examIdList) {
        for (Long examId:examIdList){
            examDao.deleteById(examId);
        }
    }

    public void addExam(Exam exam) {
        examDao.insert(exam);
    }

    public void updateExam(Exam exam) {
        examDao.updateExam(exam);
    }
}
