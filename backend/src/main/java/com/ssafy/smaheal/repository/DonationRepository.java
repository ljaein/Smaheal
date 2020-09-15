package com.ssafy.smaheal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.smaheal.model.Donation;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    Donation findByDonationid(Long donationid);

    List<Donation> findByApprovalAndTempOrderByCreatedateDesc(int approval, int temp);

    List<Donation> findByTempOrderByCreatedateDesc(int temp);

    List<Donation> findByCategoryAndApprovalAndTempOrderByCreatedateDesc(String category, int approval, int temp);
}