package com.ssafy.smaheal.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.smaheal.model.Smile;

@Repository
public interface SmileRepository extends JpaRepository<Smile, Long> {
    List<Smile> findTop3ByOrderBySmileperDesc();
    List<Smile> findByCreatedate(LocalDate createdate);
    List<Smile> findByDonationid(Long donationid);
}