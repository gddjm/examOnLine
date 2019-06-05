package com.wwz.exam.service;

import com.wwz.exam.dao.CandidateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {
    @Autowired
    CandidateDao candidateDao;
//
//    public long[] getExamId(long studentId){
//        return candidateDao.getExamId(studentId);
//    }
    public long getTextId(long studentId, long examId) {
        return candidateDao.getTextId(studentId,examId);
    }


    public long getExamId(long studentId, long textId) {
        return candidateDao.getExamId(studentId,textId);
    }

    public void insert(long studentId, long examId, long textId) {
        candidateDao.insert(studentId,examId,textId);
    }

    public boolean exists(long studentId, long examId, long textId) {
        return candidateDao.getById(studentId,examId,textId) > 0;
    }
}
