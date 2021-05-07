package com.TeenPatti.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class TestController {

	@PostMapping(value = "/test")
	public String testFuction() {
		return "OK AND TEST PASS";
	}
}
