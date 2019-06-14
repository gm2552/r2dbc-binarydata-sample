package io.r2dbc.sample;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Provider
{
	@Id 
	Integer id;

	String firstname, lastname;

	/*
	 * X-509 certificate attribute used by a provider to digitally sign stuff
	 */
	byte[] cert;
	
	boolean hasId() {
		return id != null;
	}
}
