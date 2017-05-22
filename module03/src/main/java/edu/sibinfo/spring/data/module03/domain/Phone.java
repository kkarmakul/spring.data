package edu.sibinfo.spring.data.module03.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import edu.sibinfo.spring.data.module03.dao.PhoneType;

@Entity
public class Phone {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 15)
	private String number;

	@ManyToOne
	private Client client;
	
	@Enumerated(EnumType.STRING)
	private PhoneType phoneType;
	
	// for Hibernate
	public Phone() {
	}

	public Phone(Client client, String number, PhoneType phoneType) {
		super();
		this.number = number;
		this.client = client;
		this.phoneType = phoneType;
	}

	public Long getId() {
		return id;
	}

	public String getNumber() {
		return number;
	}

	public Client getClient() {
		return client;
	}

	public PhoneType getPhoneType() {
		return phoneType;
	}
}
