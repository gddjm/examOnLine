package com.wwz.exam.service;

import com.wwz.exam.dao.ScoreDao;
import com.wwz.exam.domain.Score;
import com.wwz.exam.domain.ScoreInformation;
import com.wwz.exam.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {
    @Autowired
    ScoreDao scoreDao;
    public List<ScoreInformation> getByStudent(Student student){
        return scoreDao.getByStudent(student);
    }

    public void insert(Score score) {
        scoreDao.insert(score);
    }

    public List<ScoreInformation> getAll() {
        return scoreDao.getAll();
    }

    public void deleteAllById(List<Long> scoreIdList) {
        for (long scoreId :scoreIdList){
            scoreDao.deleteById(scoreId);
        }
    }

    public Score getById(long scoreId) {
        return scoreDao.getById(scoreId);
    }

    public void update(Score score) {
        scoreDao.update(score);
    }

    public void deleteById(long scoreId) {
        scoreDao.deleteById(scoreId);
    }
}
