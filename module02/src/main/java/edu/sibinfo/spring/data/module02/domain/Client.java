package edu.sibinfo.spring.data.module02.domain;

import java.nio.charset.StandardCharsets;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Client {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String familyName;
	private String firstName;
	private String mobile;
	private byte[] passwordEncoded;

	public Client() {
		super();
	}

	public Client(String familyName, String firstName, String mobile) {
		super();
		this.familyName = familyName;
		this.firstName = firstName;
		this.mobile = mobile;
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

	public String getMobile() {
		return mobile;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Client [familyName=").append(familyName).append(", firstName=").append(firstName)
				.append(", mobile=").append(mobile);
		if (passwordEncoded != null)
			builder.append(", password=[").append(new String(passwordEncoded, StandardCharsets.US_ASCII)).append(']');
		builder.append("]");
		return builder.toString();
	}

	public void setPassword(byte[] passwordEncoded) {
		this.passwordEncoded = passwordEncoded;
	}
}
