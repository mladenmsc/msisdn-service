package com.vodafone.msisdns.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.vodafone.msisdns.ValidationException;
import com.vodafone.msisdns.model.Msisdn;
import com.vodafone.msisdns.model.ServiceType;
import com.vodafone.msisdns.repository.MsisdnRepository;

@RunWith(MockitoJUnitRunner.class)
public class MsisdnValidatorTest {

	MsisdnValidator sut;
	@Mock
	MsisdnRepository repository;

	@Before
	public void setUp() {
		sut = new MsisdnValidator(repository);
	}

	@Test
	public void testValidateIfValidDontThrowException() {
		try {
			Msisdn msisdn = generateMsisdn();
			sut.validate(msisdn);
			assertTrue("Msisdn is valid", true);
		} catch (ValidationException e) {
			fail("Validation throw but shouldn't be.");
		}
	}
	@Test
	public void testValidateIfMsisdnDoesntExistThrowException() {
		try {
			Msisdn msisdn = generateMsisdn();
			msisdn.setMsisdn(null);
			;
			sut.validate(msisdn);
		} catch (ValidationException e) {
			assertTrue("Exception is thrown", true);
			assertThat(e.getMessage(), is("Validation Exception. " + MsisdnValidator.MSISDN_IS_MANDATORY));
		}
	}

	@Test
	public void testValidateIfMsisdnAlreadyInDbThrowException() {
		try {
			Msisdn msisdn = generateMsisdn();
			Msisdn foundMsisdn = new Msisdn();
			when(repository.findByMsisdn(msisdn.getMsisdn())).thenReturn(foundMsisdn);
			sut.validate(msisdn);
		} catch (ValidationException e) {
			assertTrue("Exception is thrown", true);
			assertThat(e.getMessage(), is("Validation Exception. " + MsisdnValidator.MSISDN_ALREADY_EXISTS));
		}
	}

	@Test
	public void testValidateIfMsisdnIsNotCorrectFormatThrowException() {
		try {
			Msisdn msisdn = generateMsisdn();
			msisdn.setMsisdn("22222");
			sut.validate(msisdn);
		} catch (ValidationException e) {
			assertTrue("Exception is thrown", true);
			assertThat(e.getMessage(), is("Validation Exception. " + MsisdnValidator.MSISDN_NOT_IN_VALID_FORMAT));
		}
	}

	private Msisdn generateMsisdn() {
		Msisdn msisdn = new Msisdn();
		msisdn.setMsisdn("12345678912");
		msisdn.setCustomerIdOwner(33);
		msisdn.setCustomerIdUser(12);
		msisdn.setId(1);
		msisdn.setServiceStartDate(123l);
		msisdn.setServiceType(ServiceType.MOBILE_POSTPAID);
		return msisdn;
	}

}
