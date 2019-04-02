package com.vodafone.msisdns.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vodafone.msisdns.model.Msisdn;

public interface MsisdnRepository extends JpaRepository<Msisdn, Integer> {

	List<Msisdn> findAllByMsisdnContaining(String msisdn);
	
	Msisdn findByMsisdn(String msisdn);
}
