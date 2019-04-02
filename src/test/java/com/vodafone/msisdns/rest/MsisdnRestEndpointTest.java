package com.vodafone.msisdns.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.vodafone.msisdns.ValidationException;
import com.vodafone.msisdns.model.Msisdn;
import com.vodafone.msisdns.repository.MsisdnRepository;

@RunWith(MockitoJUnitRunner.class)
public class MsisdnRestEndpointTest {

	MsisdnRestEndpoint sut;
	@Mock
	MsisdnRepository repository;
	@Mock
	MsisdnValidator validator;

	@Before
	public void setUp() throws Exception {
		sut = new MsisdnRestEndpoint(repository, validator);
	}

	@Test
	public final void testGetAllWhenCalledShouldCallRepositoryFindAll() {
		// Given
		// When
		sut.getAll();
		// Than
		verify(repository, times(1)).findAll();
	}

	@Test
	public final void testGetByMsisdnWhenCalledShouldCallRepositoryWithPassedMsisdn() {
		// Given
		String msisdn = "12341234123";
		// When
		sut.getByMsisdn(msisdn);
		// Than
		verify(repository).findAllByMsisdnContaining(msisdn);
	}

	@Test
	public final void testSaveAssertThatValidationIsCalled() {
		// Given
		Msisdn msisdn = new Msisdn();
		// When
		sut.save(msisdn);
		// Than
		verify(validator).validate(msisdn);
	}

	@Test
	public final void testSaveWhenValidationFailsSendBadRequest() {
		// Given
		Msisdn msisdn = new Msisdn();
		doThrow(new ValidationException("Some validation exception")).when(validator).validate(msisdn);
		// When
		ResponseEntity<Msisdn> result = sut.save(msisdn);
		// Than
		assertThat(result.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

	@Test
	public final void testUpdateWhenMsisdnFoundDoUpdate() {
		// Given
		Msisdn msisdn = new Msisdn();
		Msisdn dbMsisdin = new Msisdn();
		Optional<Msisdn> returnedMsisdn = Optional.of(dbMsisdin);
		when(repository.findById(msisdn.getId())).thenReturn(returnedMsisdn);
		// When
		ResponseEntity<Msisdn> result = sut.update(msisdn);
		// Than
		verify(repository).save(msisdn);
	}

	@Test
	public final void testUpdateWhenMsisdnNotFoundDoReturnBadRequest() {
		// Given
		Msisdn msisdn = new Msisdn();
		Optional<Msisdn> notFound = Optional.empty();
		when(repository.findById(msisdn.getId())).thenReturn(notFound);
		// When
		ResponseEntity<Msisdn> result = sut.update(msisdn);
		// Than
		assertThat(result.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

}
