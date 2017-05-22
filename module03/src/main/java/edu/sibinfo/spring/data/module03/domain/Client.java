package edu.sibinfo.spring.data.module03.domain;

import java.nio.charset.StandardCharsets;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Client {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(length=31)
	private String familyName;
	@Column(length=31)
	private String firstName;
	@Column(length=63)
	private byte[] passwordEncoded;

	public Client() {
		super();
	}

	public Client(String familyName, String firstName) {
		super();
		this.familyName = familyName;
		this.firstName = firstName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFamilyName() {
		return familyName;
	}

	public String getFirstName() {
		return firstName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Client [familyName=").append(familyName).append(", firstName=").append(firstName);
		if (passwordEncoded != null)
			builder.append(", password=[").append(new String(passwordEncoded, StandardCharsets.US_ASCII)).append(']');
		builder.append("]");
		return builder.toString();
	}

	public void setPassword(byte[] passwordEncoded) {
		this.passwordEncoded = passwordEncoded;
	}
}
