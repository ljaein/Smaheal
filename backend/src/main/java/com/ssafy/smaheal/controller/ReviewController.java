package com.ssafy.smaheal.controller;

import java.io.File;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
		review.setLikeCnt((long) 0);
		review.setVisit((long) 0);
		reviewRepository.save(review);

		return ResponseEntity.ok(SUCCESS);
	}

	@GetMapping("/getList/{limit}")
	public List<Review> findReviewAll(@PathVariable(value = "limit") int limit) {
		List<Review> list = reviewRepository.findByAllPaging(limit);
		return list;
	}
	
	@GetMapping("/getListCnt")
	public long findReviewCnt() {
		long paging = reviewRepository.count();
		return paging;
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

	@PostMapping("/img")
	   public ResponseEntity<String> uploadImgs(@RequestPart MultipartFile img) throws Exception {
	       String workspacePath = System.getProperty("user.dir");
	      String[] pathSplited = workspacePath.split("/");
	      if (pathSplited[pathSplited.length - 1].equals("target")) {
	         String newPath = pathSplited[0];
	         for (int i = 1; i < pathSplited.length - 1; i++) {
	            newPath += "/" + pathSplited[i];
	         }
	         workspacePath = newPath;
	      }
	      String baseDir = workspacePath+ "/../frontend/public/reviewImage/";
	      String originalFileName = img.getOriginalFilename();
	      File dest = new File(baseDir + originalFileName);
	      
	      String newName = originalFileName;
	      String realName = originalFileName.split("\\.")[0];
	      String extension = originalFileName.split("\\.")[1];
	      int index = 0;
	      while(dest.exists()) {
	         index++;
	         newName = realName + "(" + index + ")." + extension;
	         dest = new File(baseDir + newName);
	      }
	      
	      img.transferTo(dest);
	   
	      return ResponseEntity.ok(newName);
	   }
}
