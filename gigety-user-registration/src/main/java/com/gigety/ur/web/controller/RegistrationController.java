package com.gigety.ur.web.controller;

import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.db.model.VerificationToken;
import com.gigety.ur.security.registration.OnRegistrationCompleteEvent;
import com.gigety.ur.service.AsyncEmailService;
import com.gigety.ur.service.GigUserService;
import com.gigety.ur.util.validation.EmailExistsException;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RegistrationController {

	private final GigUserService userService;
	private final ApplicationEventPublisher appEventPublisher;
	private final MessageSource messageSource;
	private final AsyncEmailService emailService;


	public RegistrationController(GigUserService userService, ApplicationEventPublisher appEventPublisher,
			MessageSource messageSource, AsyncEmailService emailService) {
		super();
		this.userService = userService;
		this.appEventPublisher = appEventPublisher;
		this.messageSource = messageSource;
		this.emailService = emailService;
	}

	@GetMapping("signup")
	public ModelAndView registrationForm() {
		return new ModelAndView("registrationPage", "user", new GigUser());
	}

	@RequestMapping("/reg/register")
	public ModelAndView registerUser(
			@Valid final GigUser user, 
			final BindingResult result,
			final HttpServletRequest request) {
		if (result.hasErrors()) {
			return new ModelAndView("registrationPage", "user", new GigUser());
		}
		try {
			final GigUser registered = userService.registerNewUser(user);
			registered.setEnabled(false);
			final String appUrl = getAppUrl(request);
			log.debug("Create User {} ", registered);
			appEventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, appUrl, request.getLocale()));

		} catch (EmailExistsException e) {
			log.error("Error Registering User {} : {}", user, e.getMessage());
			result.addError((new FieldError("user", "email", e.getMessage())));
			return new ModelAndView("registrationPage", "user", user);
		}
		return new ModelAndView("loginPage");
	}

	@GetMapping("/reg/confirm-reg")
	public ModelAndView confirmRegistration(
			final Model model, 
			@RequestParam("token") 
			final String token,
			final RedirectAttributes redirectAttributes) {

		final VerificationToken vToken = userService.getVerificationToken(token);

		if (vToken == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "Confirmation token invalid :: null");
			return new ModelAndView("redirect:/login");
		}
		if (tokenExpired(vToken)) {
			redirectAttributes.addFlashAttribute("errorMessage", String.format(
					"Your registration token has expired as of %s, please sign up again", vToken.getExpiryDate()));
		} else {

			final GigUser user = vToken.getGigUser();
			user.setEnabled(true);
			userService.saveRegisteredUser(user);
			redirectAttributes.addFlashAttribute("message",
					String.format("Thank you %s, your account has been verified", user.getEmail()));

		}
		return new ModelAndView("redirect:/login");

	}
	
	
	@RequestMapping("/reg/resetpw")
	@ResponseBody
	public ModelAndView resetPassword(
			HttpServletRequest request, 
			@RequestParam("email") final String userEmail, 
			final RedirectAttributes redirectAttributes,
			final Locale locale) {
		
		final GigUser user = userService.findByEmail(userEmail);
		if(user != null) {
			final String appUrl = getAppUrl(request);
			emailService.sendPWResetEmail(user, appUrl, locale);
			redirectAttributes.addFlashAttribute("message", 
					messageSource.getMessage("password.reset.email", null, locale ));
			
		}
		return new ModelAndView("redirect:/login");
	}
	
	
	@RequestMapping("/reg/update-pw")
	public ModelAndView updatePwPage(
			@RequestParam("id") final long id, 
			@RequestParam("token") final String token,
			final RedirectAttributes redirectAttributes) {
		return null;
	}

	private boolean tokenExpired(VerificationToken vToken) {
		return (vToken.getExpiryDate().getTime() - Calendar.getInstance().getTime().getTime() <= 0);
	}
	private String getAppUrl(HttpServletRequest request) {
		return String.format("http://%1$s:%2$s%3$s", request.getServerName(),
				request.getServerPort(), request.getContextPath());
	}
}
