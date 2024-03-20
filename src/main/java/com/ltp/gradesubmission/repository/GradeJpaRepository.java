package com.ltp.gradesubmission.repository;

import java.util.List;

import com.ltp.gradesubmission.connectpostgresqldb.GradeEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeJpaRepository extends JpaRepository<GradeEntity, String> {

    List<GradeEntity> findByName(String name);
    
}

