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

	@GetMapping(name = "something")
	public String getWithName() {
		return "hello";
	}

	@PostMapping
	public void post() {

	}

	@PostMapping(name = "something-else")
	public void postWithName() {

	}

}
