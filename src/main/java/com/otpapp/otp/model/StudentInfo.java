package com.otpapp.otp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="studentinfo")
public class StudentInfo 
{
	@Column(length=50, nullable=false)
	private int rollno;
	
	@Column(length=50, nullable=false)
	private String name;
	
	@Column(length=50, nullable=false)
	private String enrollmentno;
	
	@Id
	@Column(length=100, nullable=false)
	private String emailaddress;
	
	@Column(length=13, nullable=false)
	private String contactno;
	
	@Column(length=13, nullable=false)
	private String whatsappno;
	
	@Column(length=100, nullable=false)
	private String collegename;
	
	@Column(length=50, nullable=false)
	private String course;
	
	@Column(length=50, nullable=false)
	private String branch;
	
	@Column(length=40, nullable=false)
	private String year;
	
	@Column(length=50, nullable=false)
	private String highschool;
	
	@Column(length=50, nullable=false)
	private String interschool;
	
	@Column(length=50, nullable=false)
	private String aggregate;
	
	@Column(length=50, nullable=false)
	private String trainingmode;
	
	@Column(length=50, nullable=false)
	private String traininglocation;
	
	@Column(length=500, nullable=true)
	private String profilepic;
	
	public String getProfilepic() {
		return profilepic;
	}

	public void setProfilepic(String profilepic) {
		this.profilepic = profilepic;
	}

	@Column(length=50, nullable=false)
	private String password;
	
	@Column(length=50, nullable=false)
	private String regdate;

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
	
	
}