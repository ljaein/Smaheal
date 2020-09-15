package com.ssafy.smaheal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.ssafy.smaheal.model.Donation;
import com.ssafy.smaheal.repository.DonationRepository;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/donation")
public class DonationController {
    @Autowired
    private DonationRepository donationRepository;

    @GetMapping("/getList")
    @ApiOperation(value = "기부 게시판 리스트(승인)")
    public Object getDonationList() throws SQLException, IOException {
        try {
            List<Donation> donationList = donationRepository.findByApprovalAndTempOrderByCreatedateDesc(1,0);
            if (donationList != null) {
                return new ResponseEntity<>(donationList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCategoryList/{category}")
    @ApiOperation(value = "기부 게시판 카테고리 별 리스트(승인)")
    public Object getDonationList(@PathVariable String category) throws SQLException, IOException {
        try {
            List<Donation> donationList = donationRepository.findByCategoryAndApprovalAndTempOrderByCreatedateDesc(category,1,0);
            if (donationList != null) {
                return new ResponseEntity<>(donationList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getWaitList")
    @ApiOperation(value = "기부 게시판 리스트(대기)")
    public Object getWaitDonationList() throws SQLException, IOException {
        try {
            List<Donation> donationList = donationRepository.findByApprovalAndTempOrderByCreatedateDesc(0,0);
            if (donationList != null) {
                return new ResponseEntity<>(donationList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTempList")
    @ApiOperation(value = "기부 게시판 임시저장 리스트")
    public Object getTempDonationList() throws SQLException, IOException {
        try {
            List<Donation> tempDonationList = donationRepository.findByTempOrderByCreatedateDesc(1);
            if (tempDonationList != null) {
                return new ResponseEntity<>(tempDonationList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/regist")
    @ApiOperation(value = "기부 게시물 등록 요청")
    public Object registDonation(@RequestBody Donation request) throws SQLException, IOException {
        try {
            Donation donation = new Donation();
            donation.setTitle(request.getTitle());
            donation.setWriter(request.getWriter());
            donation.setSdate(request.getSdate());
            donation.setEdate(request.getEdate());
            donation.setCategory(request.getCategory());
            donation.setMaxcnt(request.getMaxcnt());
            donation.setReceiver(request.getReceiver());
            donation.setAddress(request.getAddress());
            donation.setContent(request.getContent());
            donation.setApproval(0); //아직 승인되지 않은 게시물
            donation.setTemp(0); //임시저장이 아닌 게시물
            donationRepository.save(donation);
            return new ResponseEntity<>(donation, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/registTemp")
    @ApiOperation(value = "기부 게시물 임시저장")
    public Object registTempDonation(@RequestBody Donation request) throws SQLException, IOException {
        try {
            Donation donation = new Donation();
            donation.setTitle(request.getTitle());
            donation.setWriter(request.getWriter());
            donation.setSdate(request.getSdate());
            donation.setEdate(request.getEdate());
            donation.setCategory(request.getCategory());
            donation.setMaxcnt(request.getMaxcnt());
            donation.setReceiver(request.getReceiver());
            donation.setAddress(request.getAddress());
            donation.setApproval(0); //아직 승인되지 않은 게시물
            donation.setTemp(1); //임시저장인 게시물
            donationRepository.save(donation);
            return new ResponseEntity<>(donation, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{donationid}")
    @ApiOperation(value = "기부 게시물 삭제/거절")
    public Object deleteDonation(@PathVariable Long donationid) throws SQLException, IOException {
        try {
            Donation donation = donationRepository.findByDonationid(donationid);
            if (donation != null) {
                donationRepository.delete(donation);
                return new ResponseEntity<>(donation, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    @ApiOperation(value = "기부 게시물 수정")
    public Object updateDonation(@RequestBody Donation request) throws SQLException, IOException {
        try {
            Donation donation = donationRepository.findByDonationid(request.getDonationid());
            if (donation != null) {
                donation.setTitle(request.getTitle());
                donation.setWriter(request.getWriter());
                donation.setSdate(request.getSdate());
                donation.setEdate(request.getEdate());
                donation.setCategory(request.getCategory());
                donation.setMaxcnt(request.getMaxcnt());
                donation.setReceiver(request.getReceiver());
                donation.setAddress(request.getAddress());
                donationRepository.save(donation);
                return new ResponseEntity<>(donation, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(donation, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/approve/{donationid}")
    @ApiOperation(value = "기부 게시물 승인")
    public Object approveDonation(@PathVariable Long donationid) throws SQLException, IOException {
        try {
            Donation donation = donationRepository.findByDonationid(donationid);
            if (donation != null) {
                donation.setApproval(1);
                donationRepository.save(donation);
                return new ResponseEntity<>(donation, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(donation, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/detail/{donationid}")
    @ApiOperation(value = "기부 게시물 상세정보")
    public Object detailDonation(@PathVariable Long donationid) throws SQLException, IOException {
        try {
            Donation donation = donationRepository.findByDonationid(donationid);
            if (donation != null) {
                return new ResponseEntity<>(donation, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
