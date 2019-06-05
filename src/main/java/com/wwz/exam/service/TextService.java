package com.wwz.exam.service;


import com.wwz.exam.dao.TextDao;
import com.wwz.exam.domain.Exam;
import com.wwz.exam.domain.Question;
import com.wwz.exam.redis.RedisService;
import com.wwz.exam.redis.TextKey;
import com.wwz.exam.vo.TextVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TextService {
    @Autowired
    RedisService redisService;
    @Autowired
    AdminService adminService;
    @Autowired
    TextDao textDao;
    @Autowired
    TextQuestionService textQuestionService;
    @Autowired
    ExamService examService;

    public void set(long adminId,List<Question> checkQuestionList) {
        redisService.addList(TextKey.getByAdminId, "" + adminId, checkQuestionList);
    }

    public List<Question> get(long adminId, Class<Question> clazz) {
        return redisService.getList(TextKey.getByAdminId, "" + adminId, clazz);
    }

    public void insert(long text_id,String type,int score){
        textDao.insert(text_id,type,score);
    }

    @Transactional
    public void makeText(TextVo textVo){
        String type = textVo.getSubjectType();
        int score = textVo.getScore();
        long textId = textVo.getTextId();
        insert(textId,type,score);
        List<Long> questionIdList = textVo.getQuestionIdList();
        List<Long> questionNumberList = textVo.getQuestionNumberList();
        for (int i=0 ; i<questionIdList.size() ; i++){
            textQuestionService.insert(textId,questionIdList.get(i),questionNumberList.get(i));
        }
//        Exam exam = textVo.getExam();
//        examService.insert(exam);
    }

}
