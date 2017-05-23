package edu.sibinfo.spring.data.module03.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.sibinfo.spring.data.module03.dao.PhoneType;

@Entity
@Table(name="client_phones")
public class Phone {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="phone_number")
	private String number;

	@ManyToOne
	private Client client;
	
	@Enumerated(EnumType.STRING)
	@Column(name="phone_type")
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
