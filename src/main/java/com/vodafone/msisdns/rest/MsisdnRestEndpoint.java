package com.vodafone.msisdns.rest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vodafone.msisdns.ApplicationException;
import com.vodafone.msisdns.ValidationException;
import com.vodafone.msisdns.model.Msisdn;
import com.vodafone.msisdns.repository.MsisdnRepository;

@RestController
public class MsisdnRestEndpoint {

	Logger logger = LoggerFactory.getLogger(MsisdnRestEndpoint.class);

	private final MsisdnRepository repository;
	private final MsisdnValidator validator;

	@Autowired
	public MsisdnRestEndpoint(MsisdnRepository repository, MsisdnValidator validator) {
		this.repository = repository;
		this.validator = validator;
	}

	@GetMapping("/msisdns")
	public List<Msisdn> getAll() {
		return repository.findAll();
	}

	@GetMapping("/msisdns/{msisdn}")
	public List<Msisdn> getByMsisdn(@PathVariable String msisdn) {
		List<Msisdn> result = repository.findAllByMsisdnContaining(msisdn);
		return result;

	}

	@PostMapping("/msisdns")
	public ResponseEntity<Msisdn> save(@RequestBody Msisdn msisdn) {
		try {
			validator.validate(msisdn);
			msisdn.setServiceStartDate(new Date().getTime());
			Msisdn result = repository.save(msisdn);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (ValidationException validationException) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("x-reason", validationException.getMessage());
			return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/msisdns")
	public ResponseEntity<Msisdn> update(@RequestBody Msisdn msisdn) {
		try {
			Optional<Msisdn> found = repository.findById(msisdn.getId());
			if (!found.isPresent()) {
				throw new ApplicationException("No MSSIDN found with specified ID");
			}
			Msisdn result = repository.save(msisdn);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (ApplicationException applicationException) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("x-reason", applicationException.getMessage());
			return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/msisdns")
	public ResponseEntity<Msisdn> delete(@RequestBody Msisdn msisdn) {
		try {
			Optional<Msisdn> found = repository.findById(msisdn.getId());
			if (!found.isPresent()) {
				throw new ApplicationException("No MSSIDN found with specified ID");
			}
			repository.delete(found.get());
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (ApplicationException applicationException) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("x-reason", applicationException.getMessage());
			return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
		}
	}

}
