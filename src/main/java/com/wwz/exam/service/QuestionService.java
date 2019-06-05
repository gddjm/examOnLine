package com.wwz.exam.service;


import com.wwz.exam.dao.QuestionDao;
import com.wwz.exam.domain.Question;
import com.wwz.exam.exception.GlobalException;
import com.wwz.exam.redis.QuestionKey;
import com.wwz.exam.redis.RedisService;
import com.wwz.exam.result.CodeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    RedisService redisService;
    public List<Question> getQuestionsById(long studentId) {
        return getQuestionsByTextId(getTextIdByStudentId(studentId));
    }


//    通过考生id获得试卷id
    public long getTextIdByStudentId(long studentId){
        Long textId =  questionDao.getTextIdByStudentId(studentId);
        if (textId == null){
            throw new GlobalException(CodeMsg.STUDENT_NOT_SIGN_UP);
        }
        return textId;
    }
//    通过试卷id获得问题
    public  List<Question> getQuestionsByTextId(long textId){
        List<Question> questionList = redisService.getList(QuestionKey.getByTextId,""+textId,Question.class);
        if (questionList != null && !questionList.isEmpty()){
            return questionList;
        }
        questionList = questionDao.getQuestionsByTextId(textId);
        if (questionList != null && !questionList.isEmpty()){
            redisService.addList(QuestionKey.getByTextId,""+textId,questionList);
        }
        return questionList;
    }

    public void deleteById(long questionId) {
        questionDao.deleteById(questionId);
    }

    public void deleteAllById(List<Long> questionIdList) {
        for (Long questionId:questionIdList){
            questionDao.deleteById(questionId);
        }
    }
    public List<Question> getAll() {
        return questionDao.getAll();
    }

    public Question getById(long questionId) {
        return questionDao.getById(questionId);
    }

    public List<Question> getAllById(List<Long> questionIdList) {
        if (questionIdList == null || questionIdList.isEmpty()){
            return null;
        }
        List<Question> questionList = new ArrayList<Question>();
        for (long questionId:questionIdList){
            questionList.add(getById(questionId));
        }
        return questionList;
    }

    public void updateQuestion(Question question) {
        questionDao.updateQuestion(question);
    }

    public void addQuestion(Question question) {
        questionDao.addQuestion(question);
    }
}
