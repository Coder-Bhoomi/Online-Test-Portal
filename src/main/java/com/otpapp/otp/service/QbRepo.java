package com.otpapp.otp.service;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.otpapp.otp.model.Qb;

public interface QbRepo extends JpaRepository<Qb, Integer>{

	Page<Qb> findByCourse(String course, Pageable pageable);

}
