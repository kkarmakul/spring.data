package edu.sibinfo.spring.data.module01;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncoderConfiguration {
	@Bean
	public MessageDigest getPasswordEncoder() throws NoSuchAlgorithmException {
		return MessageDigest.getInstance("MD5");
	}
}
