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

import com.ssafy.smaheal.model.Notices;
import com.ssafy.smaheal.repository.NoticeRepository;

@RestController
@RequestMapping("/api/notice")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NoticeController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	@PostMapping("/write")
	public ResponseEntity<String> createNotice(@RequestBody Notices notice) {
		
		noticeRepository.save(notice);
		
        return ResponseEntity.ok(SUCCESS);
	}
	
	@GetMapping("/findAll")
	public List<Notices> findAllNotice() {
		
		List<Notices> list = noticeRepository.findAllByOrderByNoticeidDesc();
		
		return list;
	}
	
	@GetMapping("/find/{noticeid}")
	public Notices findNotice(@PathVariable(value="noticeid") Long noticeid) {
		
		Notices article = noticeRepository.findByNoticeid(noticeid);
		
		return article;
	}
	
	@PutMapping("/update/{noticeid}")
	public ResponseEntity<String> modifyNoticeByNum(@PathVariable(value = "noticeid")
	Long noticeid, @RequestBody Notices notice) {
		
		noticeRepository.save(notice);
		
		return ResponseEntity.ok(SUCCESS);		
	}
	
	@DeleteMapping("/delete/{noticeid}")
	public ResponseEntity<String> deleteNoticeByNum(@PathVariable(value = "noticeid")
	Long noticeid) {
		
		noticeRepository.deleteByNoticeid(noticeid);
		
		return ResponseEntity.ok(SUCCESS);
	}
	
	@GetMapping("/search/{title}")
	public List<Notices> noticeSearch(@PathVariable(value = "title")
	String title) {
		
		List<Notices> searchNotice = noticeRepository.findByTitleContainingOrderByNoticeidDesc(title);
		
		return searchNotice;
	}
	
}
