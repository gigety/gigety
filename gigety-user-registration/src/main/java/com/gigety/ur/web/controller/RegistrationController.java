package com.gigety.ur.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.service.GigUserService;
import com.gigety.ur.util.validation.EmailExistsException;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RegistrationController {

	private final GigUserService userService;
	private final ApplicationEventPublisher appEventPublisher;
	

	@Autowired
	public RegistrationController(GigUserService userService, ApplicationEventPublisher appEventPublisher) {
		super();
		this.userService = userService;
		this.appEventPublisher = appEventPublisher;
	}

	@GetMapping("signup")
	public ModelAndView registrationForm() {
		return new ModelAndView("registrationPage", "user", new GigUser());
	}

	@RequestMapping("/gigety/register")
	public ModelAndView registerUser(@Valid final GigUser user, final BindingResult result,
			final HttpServletRequest request) {
		if (result.hasErrors()) {
			return new ModelAndView("registrationPage", "user", new GigUser());
		}
		try {
			final GigUser registered = userService.registerNewUser(user);
			final String appUrl = String.format("http://%1$s:%2$s%3$s", request.getServerName(),request.getServerPort(), request.getContextPath());
			
			//appEventPublisher.publishEvent(new );
				
		}catch(EmailExistsException e) {
			result.addError((new FieldError("user", "email", e.getMessage())));
			return new ModelAndView("registrationPage", "user", user);
		}
		return new ModelAndView("loginPage");
	}

	@GetMapping("user/registration")
	public ModelAndView registerUser(@Valid GigUser user, BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("registrationPage", "user", new GigUser());
		}
		try {
			userService.registerNewUser(user);
		} catch (EmailExistsException e) {

			result.addError(new FieldError("user", "email", e.getMessage()));
			return new ModelAndView("registrationPage", "user", user);
		}
		return new ModelAndView("redirect:/login");
	}
}
