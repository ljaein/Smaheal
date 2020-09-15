package com.ssafy.smaheal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.smaheal.exception.ResourceNotFoundException;
import com.ssafy.smaheal.model.Review;
import com.ssafy.smaheal.repository.ReviewRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/review")
public class ReviewController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private ReviewRepository reviewRepository;

	@PostMapping("/regist")
	public ResponseEntity<String> registReview(@RequestBody Review review) {
		System.out.println(review.toString());
		reviewRepository.save(review);

		return ResponseEntity.ok(SUCCESS);
	}

	@GetMapping("/getList")
	public List<Review> findReviewAll() {
		List<Review> list = reviewRepository.findAll();
		return list;
	}

	@GetMapping("/get/{num}")
	public Review findReviewByNum(@PathVariable(value = "num") Long num) {
		Review review = reviewRepository.findByNum(num)
				.orElseThrow(() -> new ResourceNotFoundException("Review", "num", num));
		return review;
	}

	@PutMapping("/update/{num}")
	public ResponseEntity<String> modifyReview(@PathVariable(value = "num") Long num, @RequestBody Review review) {
		review.setNum(num);
		reviewRepository.save(review);

		return ResponseEntity.ok(SUCCESS);
	}

	@DeleteMapping("/delete/{num}")
	public ResponseEntity<String> deleteReview(@PathVariable(value = "num") Long num) {
		reviewRepository.deleteByNum(num);

		return ResponseEntity.ok(SUCCESS);
	}

}
