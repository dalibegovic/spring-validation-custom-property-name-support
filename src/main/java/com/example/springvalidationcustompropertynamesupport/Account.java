package com.example.springvalidationcustompropertynamesupport;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public class Account {
	@Valid
	@JsonProperty("personal_data")
	private PersonalData personalData;

	public Account(@JsonProperty("personal_data") PersonalData personalData) {
		this.personalData = personalData;
	}

	public PersonalData getPersonalData() {
		return personalData;
	}

	public void setPersonalData(PersonalData personalData) {
		this.personalData = personalData;
	}

	public static class PersonalData {
		@NotEmpty
		private String firstName;

		public PersonalData(@JsonProperty(value = "first_name") String firstName) {
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
	}
}
