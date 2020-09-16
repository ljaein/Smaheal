package com.ssafy.smaheal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.ssafy.smaheal.model.Smile;
import com.ssafy.smaheal.repository.SmileRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/smile")
public class SmileController {
    @Autowired
    private SmileRepository smileRepository;

    @PostMapping("/regist")
    @ApiOperation(value = "웃음 기부 등록")
    public Object registDonation(@RequestBody Smile request) throws SQLException, IOException {
        try {
            Smile smile = new Smile();
            smile.setUser_id(request.getUser_id());
            smile.setDonationid(request.getDonationid());
            smile.setPhoto(request.getPhoto());
            smile.setSmileper(request.getSmileper());
            smile.setComment(request.getComment());
            smile.setAgreement(request.getAgreement());
            smileRepository.save(smile);
            return new ResponseEntity<>(smile, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
