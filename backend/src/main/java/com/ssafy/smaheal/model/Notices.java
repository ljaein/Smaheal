package com.ssafy.smaheal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ssafy.smaheal.model.audit.DateAudit;

@Entity
@Table(name = "notices")
public class Notices extends DateAudit {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeid;

    @NotBlank
    @Size(max = 50)
    private String title;


	@NotBlank
    @Size(max = 1000)
    private String content;
    
	public Notices() {
		
	}
	
	public Notices(Long noticeid, @NotBlank @Size(max = 50) String title, @NotBlank @Size(max = 1000) String content) {
		super();
		this.noticeid = noticeid;
		this.title = title;
		this.content = content;
	}

	public Long getNoticeid() {
		return noticeid;
	}

	public void setNoticeid(Long noticeid) {
		this.noticeid = noticeid;
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

	@Override
	public String toString() {
		return "Notices [noticeid=" + noticeid + ", title=" + title + ", content=" + content + "]";
	}
	
}