package com.vodafone.msisdns.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Msisdn {
	@Id
	@GeneratedValue
	private Integer id;
	private String msisdn;
	private Integer customerIdOwner;
	private Integer customerIdUser;
	private ServiceType serviceType;
	private Long serviceStartDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public Integer getCustomerIdOwner() {
		return customerIdOwner;
	}

	public void setCustomerIdOwner(Integer customerIdOwner) {
		this.customerIdOwner = customerIdOwner;
	}

	public Integer getCustomerIdUser() {
		return customerIdUser;
	}

	public void setCustomerIdUser(Integer customerIdUser) {
		this.customerIdUser = customerIdUser;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public Long getServiceStartDate() {
		return serviceStartDate;
	}

	public void setServiceStartDate(Long serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}

}