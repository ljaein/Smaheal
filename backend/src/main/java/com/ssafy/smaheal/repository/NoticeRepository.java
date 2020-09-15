package com.ssafy.smaheal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.smaheal.model.Notices;

@Repository
public interface NoticeRepository extends JpaRepository<Notices, Long> {
    
    Notices findByNoticeid(Long noticeid);
    
    List<Notices> findAll();
    
    void deleteByNoticeid(Long noticeid);
    
}
