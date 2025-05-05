package com.otpapp.otp.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otpapp.otp.model.Result;

public interface ResultRepo extends JpaRepository<Result, Long> {

    List<Result> findByEmailaddress(String emailaddress);

}
