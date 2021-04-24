package com.springboot.keycloak.junit5.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SpringSessionController {

	@GetMapping("/hello")
	public String hello(Model model) {
		return "Hello";
	}

	@GetMapping("/")
	public String home(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if (messages == null) {
			messages = new ArrayList<>();
		}
		model.addAttribute("sessionMessages", messages);
		model.addAttribute("sessionId", session.getId());
		return "session";
	}

	@GetMapping("/rohit/get")
	String rohit(Model model, Principal principal, HttpServletRequest request) {
		return "Rohit";
	}

	@PutMapping("/rohit/put/{id}")
	public String putMessage(@PathVariable String id, @ModelAttribute EditDevelopedProductDto editDevelopedProductDto) {
		System.out.println("****************id : " + id);
		System.out.println("*******************EditDevelopedProductDto: " + editDevelopedProductDto);
		return "Hello";
	}

	@PostMapping("/rohit/persistMessage")
	public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<String> msgs = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		if (msgs == null) {
			msgs = new ArrayList<>();
			request.getSession().setAttribute("MY_SESSION_MESSAGES", msgs);
		}
		msgs.add(msg);
		request.getSession().setAttribute("MY_SESSION_MESSAGES", msgs);
		return "redirect:/";
	}

	@DeleteMapping("/rohit/deleteMessage")
	public String deleteMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<String> msgs = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		if (Objects.nonNull(msgs)) {
			request.getSession().removeAttribute("MY_SESSION_MESSAGES");
		}
		return "redirect:/";
	}

	@PostMapping("/rohit/destroy")
	public String destroySession(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/";
	}
}