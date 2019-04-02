package com.vodafone.msisdns.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vodafone.msisdns.ValidationException;
import com.vodafone.msisdns.model.Msisdn;
import com.vodafone.msisdns.repository.MsisdnRepository;

@Component
public class MsisdnValidator {

	static final String MSISDN_NOT_IN_VALID_FORMAT = "MSISDN not in valid format";
	static final String MSISDN_ALREADY_EXISTS = "Msisdn already exists";
	static final String SERVICE_ID_IS_MANDATORY = "Service ID is mandatory";
	static final String USER_ID_IS_MANDATORY = "User ID is mandatory";
	static final String CUSTOMERY_ID_OWNER_IS_MANDATORY = "Customery ID owner is mandatory";
	static final String MSISDN_IS_MANDATORY = "Msisdn is mandatory";
	private final MsisdnRepository repository;

	@Autowired
	public MsisdnValidator(MsisdnRepository repository) {
		this.repository = repository;
	}

	public void validate(Msisdn msisdn) throws ValidationException {
		validateMandatoryFields(msisdn);
		validateE164Format(msisdn.getMsisdn());
		validateDoesntExists(msisdn.getMsisdn());

	}

	private void validateMandatoryFields(Msisdn msisdn) {
		if (msisdn.getMsisdn() == null || msisdn.getMsisdn().length() == 0) {
			throw new ValidationException(MSISDN_IS_MANDATORY);
		}
		if (msisdn.getCustomerIdOwner() == null) {
			throw new ValidationException(CUSTOMERY_ID_OWNER_IS_MANDATORY);
		}
		if (msisdn.getCustomerIdUser() == null) {
			throw new ValidationException(USER_ID_IS_MANDATORY);
		}
		if (msisdn.getServiceType() == null) {
			throw new ValidationException(SERVICE_ID_IS_MANDATORY);
		}
	}

	private void validateDoesntExists(String msisdn) {
		Msisdn found = repository.findByMsisdn(msisdn);
		if (found != null) {
			throw new ValidationException(MSISDN_ALREADY_EXISTS);
		}

	}

	private void validateE164Format(String msisdn) {
		// E164 validation checks
		if (msisdn.length() != 11) {
			throw new ValidationException(MSISDN_NOT_IN_VALID_FORMAT);
		}
	}

}
