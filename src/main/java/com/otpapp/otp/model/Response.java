package com.otpapp.otp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="response")
public class Response 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int resid;
		
	@Column(length = 60, nullable = false)
	private String name;
		
	@Column(length = 13, nullable = false)
	private String contactno;
		
	@Column(length = 60, nullable = false)
	private String emailaddress;
		
	@Column(length = 50, nullable = false)
	private String responsetype;
		
	@Column(length = 50, nullable = false)
	private String responsesubject;
		
	@Column(length = 1000, nullable = false)
	private String responsetext;
		
	@Column(length = 50, nullable = false)
	private String responsedate;

	public int getResid() {
		return resid;
	}

	public void setResid(int resid) {
		this.resid = resid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactno() {
		return contactno;
	}

	public void setContactno(String contactno) {
		this.contactno = contactno;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getResponsetype() {
		return responsetype;
	}

	public void setResponsetype(String responsetype) {
		this.responsetype = responsetype;
	}

	public String getResponsesubject() {
		return responsesubject;
	}

	public void setResponsesubject(String responsesubject) {
		this.responsesubject = responsesubject;
	}

	public String getResponsetext() {
		return responsetext;
	}

	public void setResponsetext(String responsetext) {
		this.responsetext = responsetext;
	}

	public String getResponsedate() {
		return responsedate;
	}

	public void setResponsedate(String responsedate) {
		this.responsedate = responsedate;
	}

}
