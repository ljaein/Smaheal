package com.ssafy.smaheal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.smaheal.model.Notices;

@Repository
public interface NoticeRepository extends JpaRepository<Notices, Long> {
    
    Notices findByNoticeid(Long noticeid);
    
//    List<Notices> findAllByOrderByNoticeidDesc();
    @Query(nativeQuery = true, value = "select * from notices order by noticeid desc limit :limit, 6")
    List<Notices> findByNoticeidPaging(@Param("limit") int limit);
    
    long count();
    
    @Transactional
    @Modifying
    void deleteByNoticeid(Long noticeid);
    
    List<Notices> findByTitleContainingOrderByNoticeidDesc(String title);
}
