package com.ssafy.smaheal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.smaheal.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	Optional<Review> findByNum(Long num);
	
	@Transactional
    @Modifying
	void deleteByNum(Long num);

}
