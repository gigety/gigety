package com.gigety.ur.web.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.db.model.VerificationToken;
import com.gigety.ur.security.registration.OnRegistrationCompleteEvent;
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
			final String appUrl = String.format("http://%1$s:%2$s%3$s", request.getServerName(),
					request.getServerPort(), request.getContextPath());
			log.debug("Create User {} ", registered);
			appEventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, appUrl));

		} catch (EmailExistsException e) {
			log.error("Error Registering User {} : {}", user, e.getMessage());
			result.addError((new FieldError("user", "email", e.getMessage())));
			return new ModelAndView("registrationPage", "user", user);
		}
		return new ModelAndView("loginPage");
	}

	@GetMapping("gigety/confirm-reg")
	public ModelAndView confirmRegistration(final Model model, @RequestParam("token") final String token,
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
			redirectAttributes.addAttribute("message",
					String.format("Thank you {}, your account has been verified", user.getEmail()));

		}
		return new ModelAndView("redirect:/login");

	}

	private boolean tokenExpired(VerificationToken vToken) {
		return (vToken.getExpiryDate().getTime() - Calendar.getInstance().getTime().getTime() <= 0);
	}

}
