package com.mycompany;

import com.mycompany.ordertest.CompositeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@EnableAutoConfiguration
public class Example {

	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}

	@Autowired
	private CompositeBean compositeBean;

	/*public static void main(String[] args) throws Exception {
		SpringApplication.run(Example.class, args);
	}*/

}