package com.otpapp.otp.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otpapp.otp.model.Test;

public interface TestRepo extends JpaRepository<Test, Long> {
    List<Test> findByStudentEmail(String studentEmail);    
}
