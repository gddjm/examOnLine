package com.wwz.exam.service;

import com.wwz.exam.dao.TeacherTextDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherTextService {

    @Autowired
    TeacherTextDao teacherTextDao;
    public void setIsMark(long studentId,long textId){
        teacherTextDao.setIsMark(studentId,textId);

    }
}
