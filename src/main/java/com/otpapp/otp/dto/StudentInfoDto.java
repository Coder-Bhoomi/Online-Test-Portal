package com.otpapp.otp.dto;

import org.springframework.web.multipart.MultipartFile;

public class StudentInfoDto 
{

	private int rollno;
	private String name;
	private String enrollmentno;
	private String emailaddress;
	private String contactno;
	private String whatsappno;
	private String collegename;
	private String course;
	private String branch;
	private String year;
	private String highschool;
	private String interschool;
	private String aggregate;
	private String trainingmode;
	private String traininglocation;
	private String password;
	private String regdate;
	private MultipartFile profilepic;
	public int getRollno() {
		return rollno;
	}
	public void setRollno(int rollno) {
		this.rollno = rollno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnrollmentno() {
		return enrollmentno;
	}
	public void setEnrollmentno(String enrollmentno) {
		this.enrollmentno = enrollmentno;
	}
	public String getEmailaddress() {
		return emailaddress;
	}
	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}
	public String getContactno() {
		return contactno;
	}
	public void setContactno(String contactno) {
		this.contactno = contactno;
	}
	public String getWhatsappno() {
		return whatsappno;
	}
	public void setWhatsappno(String whatsappno) {
		this.whatsappno = whatsappno;
	}
	public String getCollegename() {
		return collegename;
	}
	public void setCollegename(String collegename) {
		this.collegename = collegename;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getHighschool() {
		return highschool;
	}
	public void setHighschool(String highschool) {
		this.highschool = highschool;
	}
	public String getInterschool() {
		return interschool;
	}
	public void setInterschool(String interschool) {
		this.interschool = interschool;
	}
	public String getAggregate() {
		return aggregate;
	}
	public void setAggregate(String aggregate) {
		this.aggregate = aggregate;
	}
	public String getTrainingmode() {
		return trainingmode;
	}
	public void setTrainingmode(String trainingmode) {
		this.trainingmode = trainingmode;
	}
	public String getTraininglocation() {
		return traininglocation;
	}
	public void setTraininglocation(String traininglocation) {
		this.traininglocation = traininglocation;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public MultipartFile getProfilepic() {
		return profilepic;
	}
	public void setProfilepic(MultipartFile profilepic) {
		this.profilepic = profilepic;
	}
	
	
}