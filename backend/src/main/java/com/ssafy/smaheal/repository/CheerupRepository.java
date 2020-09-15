package com.ssafy.smaheal.repository;

import java.util.List;

import com.ssafy.smaheal.model.Cheerup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheerupRepository extends JpaRepository<Cheerup, Long> {
    List<Cheerup> findByDonationid(Long donationid);
    Cheerup findByCheerupid(Long cheerupid);
}
