package com.ltp.gradesubmission;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.ltp.gradesubmission.connectpostgresqldb.GradeEntity;
import com.ltp.gradesubmission.repository.GradeJpaRepository;
import com.ltp.gradesubmission.service.GradeService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GradeServiceTest {

    @Mock
    private GradeJpaRepository gradeRepository;

    @InjectMocks
    private GradeService gradeService;

    @Test
    public void getGradesFromRepoTest() {
        when(gradeRepository.findAll()).thenReturn(Arrays.asList(
            new GradeEntity("Harry", "Potions", "C-"),
            new GradeEntity("Hermione", "Arithmancy", "A+")
        ));

        List<GradeEntity> result = gradeService.getGrades(); 

        assertEquals("Harry", result.get(0).getName());
        assertEquals("Arithmancy", result.get(1).getSubject());
    }

    @Test
    public void gradeIndexTest() {
        GradeEntity grade = new GradeEntity("Harry", "Potions", "C-");
        when(gradeRepository.findAll()).thenReturn(Arrays.asList(grade));
        when(gradeRepository.findById(grade.getId())).thenReturn(Optional.of(grade));

        int valid = gradeService.getGradeIndex(grade.getId());
        int notFound = gradeService.getGradeIndex("123");

        assertEquals(0, valid);
        assertEquals(Constants.NOT_FOUND, notFound);
    }

    @Test
    public void returnGradeByIdTest() {
        GradeEntity grade = new GradeEntity("Harry", "Potions", "C-");
        when(gradeRepository.findAll()).thenReturn(Arrays.asList(grade));
        when(gradeRepository.findById(grade.getId())).thenReturn(Optional.of(grade));

        String id = grade.getId();
        GradeEntity result = gradeService.getGradeById(id);
        assertEquals(grade, result);
    }

    @Test
    public void addGradeTest() {
        GradeEntity grade = new GradeEntity("Harry", "Potions", "C-");
        when(gradeRepository.findAll()).thenReturn(Arrays.asList(grade));

        GradeEntity newGrade = new GradeEntity("Hermione", "Arithmancy", "A+");
        gradeService.submitGrade(newGrade);
        verify(gradeRepository, times(1)).save(newGrade);
    }

    @Test
    public void updateGradeTest() {
        GradeEntity grade = new GradeEntity("Harry", "Potions", "C-");
        when(gradeRepository.findAll()).thenReturn(Arrays.asList(grade));

        grade.setScore("A-");
        gradeService.submitGrade(grade);
        verify(gradeRepository, times(1)).save(grade);
    }
}
