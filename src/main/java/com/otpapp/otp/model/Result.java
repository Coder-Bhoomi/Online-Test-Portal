package com.otpapp.otp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="results")
public class Result 
{
	@Id
    @Column(length=50, nullable=false)
    private String emailaddress;
    
    @Column(length=50, nullable=false)
    private String name;
    
    @Column(length=100, nullable=false)
    private String course;
    
    @Column(length=15, nullable=false)
    private String contactno;
    
    private int totalmarks;
    
    private int getmarks;
    
    @Column(length=50, nullable=false)
    private String status;

    @Column(length=50, nullable=false)
    private int testNumber;

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public int getTotalmarks() {
        return totalmarks;
    }

    public void setTotalmarks(int totalmarks) {
        this.totalmarks = totalmarks;
    }

    public int getGetmarks() {
        return getmarks;
    }

    public void setGetmarks(int getmarks) {
        this.getmarks = getmarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTestNumber() {
        return testNumber;
    }

	public void setTestNumber(int testNumber) {
		this.testNumber = testNumber;
	}

}
