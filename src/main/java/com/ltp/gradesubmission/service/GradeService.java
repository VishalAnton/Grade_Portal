package com.ltp.gradesubmission.service;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import com.ltp.gradesubmission.Constants;
import com.ltp.gradesubmission.connectpostgresqldb.GradeEntity;
import com.ltp.gradesubmission.repository.GradeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeService {

    @Autowired
    private GradeJpaRepository gradeRepository;

    public GradeEntity getGrade(int index) {
        return gradeRepository.findAll().get(index);
    }
    
    public void addGrade(@Nonnull GradeEntity grade) {
        gradeRepository.save(grade);
    }

    public void updateGrade(@Nonnull GradeEntity grade, int index) {
        gradeRepository.save(grade);
    }

    public List<GradeEntity> getGrades() {
        Iterable<GradeEntity> iterable = gradeRepository.findAll();
        List<GradeEntity> gradeList = new ArrayList<>();
        iterable.forEach(gradeList::add);
        return gradeList;
    }

    public int getGradeIndex(String id) {
        if (id == null) {
            return Constants.NOT_FOUND;
        }

        List<GradeEntity> grades = getGrades();
        for (int i = 0; i < grades.size(); i++) {
            GradeEntity grade = grades.get(i);
            if (id.equals(grade.getId())) {
                return i;
            }
        }
        return Constants.NOT_FOUND;
    }

    public GradeEntity getGradeById(String id) {
        int index = getGradeIndex(id);
        return index == Constants.NOT_FOUND ? null : getGrade(index);
    }

    public void submitGrade(GradeEntity grade) {
        int index = getGradeIndex(grade.getId());
        if (index == Constants.NOT_FOUND) {
            addGrade(grade);
        } else {
            updateGrade(grade, index);
        }
    }
}
