package com.wwz.exam.service;


import com.wwz.exam.dao.QuestionDao;
import com.wwz.exam.dao.StudentAnswerDao;
import com.wwz.exam.domain.Question;
import com.wwz.exam.domain.StudentAnswer;
import com.wwz.exam.domain.StudentAnswerInformation;
import com.wwz.exam.domain.Teacher;
import com.wwz.exam.exception.GlobalException;
import com.wwz.exam.result.CodeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentAnswerService {
    @Autowired
    StudentAnswerDao studentAnswerDao;

    public boolean exits(long studentId,long textId,long questionNumber){
        StudentAnswer studentAnswer = studentAnswerDao.getById(studentId, textId, questionNumber);
        if (studentAnswer == null){
            return false;
        }
        return true;
    }
    public void insert(StudentAnswer studentAnswer) {
        if (exits(studentAnswer.getStudentId(),studentAnswer.getTextId(),studentAnswer.getQuestionNumber())){
            studentAnswerDao.update(studentAnswer);
        }else{
            studentAnswerDao.insert(studentAnswer);
        }
    }
    public void insert(List<StudentAnswer> studentAnswerList){
        for (StudentAnswer studentAnswer: studentAnswerList){
            insert(studentAnswer);
        }
    }
    public List<StudentAnswerInformation> getStudentAnswerInformation(Teacher teacher){
        return studentAnswerDao.getStudentAnswerInformation(teacher);
    }
    public List<StudentAnswer> getStudentAnswerList(long studentId,long textId){
        return studentAnswerDao.getListById(studentId,textId);

    }
}
