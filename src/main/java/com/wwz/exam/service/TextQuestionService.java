package com.wwz.exam.service;


import com.wwz.exam.dao.TextDao;
import com.wwz.exam.dao.TextQuestionDao;
import com.wwz.exam.domain.Question;
import com.wwz.exam.redis.RedisService;
import com.wwz.exam.redis.TextKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextQuestionService {
    @Autowired
    TextQuestionDao textQuestionDao;

    public void insert(long textId,long questionId,long questionNumber){
        textQuestionDao.insert(textId,questionId,questionNumber);
    }
}
