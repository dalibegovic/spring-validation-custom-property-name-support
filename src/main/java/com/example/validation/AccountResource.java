package com.example.validation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AccountResource {
	@PostMapping("/account")
	public ResponseEntity<Void> create(@Valid @RequestBody Account account) {
		return ResponseEntity.ok().build();
	}
}
