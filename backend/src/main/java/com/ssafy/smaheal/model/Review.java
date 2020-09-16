package com.ssafy.smaheal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ssafy.smaheal.model.audit.DateAudit;

@Entity
@Table(name = "review")
public class Review extends DateAudit{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long num;
	private String nickName;
	private String title;
	private String content;
	private String img;
	private Long likeCnt;
	private Long visit;

	public Review() {
		super();
	}

	public Review(Long num, String nickName, String title, String content, String img, Long likeCnt, Long visit) {
		super();
		this.num = num;
		this.nickName = nickName;
		this.title = title;
		this.content = content;
		this.img = img;
		this.likeCnt = likeCnt;
		this.visit = visit;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Long getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(Long likeCnt) {
		this.likeCnt = likeCnt;
	}

	public Long getVisit() {
		return visit;
	}

	public void setVisit(Long visit) {
		this.visit = visit;
	}

	@Override
	public String toString() {
		return "Review [num=" + num + ", nickName=" + nickName + ", title=" + title + ", content=" + content + ", img="
				+ img + ", likeCnt=" + likeCnt + ", visit=" + visit + "]";
	}

}
