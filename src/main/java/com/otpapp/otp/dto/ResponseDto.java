package com.otpapp.otp.dto;

public class ResponseDto 
{

	private String name;
	private String contactno;
	private String emailaddress;
	private String responsetype;
	private String responsesubject;
	private String responsetext;
	private String responsedate;
	
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
