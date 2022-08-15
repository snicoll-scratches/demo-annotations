package com.example.demo.sample;

import com.example.annotation.web.GetMapping;
import com.example.annotation.web.PostMapping;

import org.springframework.stereotype.Component;

@Component
public class WebSample {

	@GetMapping
	public String get() {
		return "hello";
	}

	@GetMapping(customName = "something")
	public String getWithName() {
		return "hello";
	}

	@PostMapping
	public void post() {

	}

	@PostMapping(customName = "something-else")
	public void postWithName() {

	}

}
