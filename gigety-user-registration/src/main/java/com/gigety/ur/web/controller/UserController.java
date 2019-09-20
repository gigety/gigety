package com.gigety.ur.web.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.service.ActiveUserService;
import com.gigety.ur.service.GigUserService;
import com.gigety.ur.util.transformer.GigUserConverter;
import com.gigety.ur.util.validation.EmailExistsException;
import com.gigety.ur.util.validation.PasswordValidationGroup;

import lombok.extern.slf4j.Slf4j;

/**
 * User Web Controller
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

	private final GigUserService userService;
	private final ActiveUserService activeUserService;

	public UserController(GigUserService userService, ActiveUserService activeUserService) {
		super();
		this.userService = userService;
		this.activeUserService = activeUserService;
	}

	@RequestMapping
	public ModelAndView list() {
		Iterable<GigUser> users = userService.findAll();
		return new ModelAndView("tl/list", "users", users);
	}

	@GetMapping("/active/{email}")
	public ModelAndView getByUserName(@PathVariable("email") String email) {
		GigUser gigUser = userService.findByEmail(email);
		//log.debug("PASSWORD {}", user.getPassword());
		return new ModelAndView("tl/view", "user", gigUser);
	}
	
	@GetMapping("/activeList")
	public ModelAndView activeUsers() {
		List<String> usernames = activeUserService.getAllActiveUsers();
		//TODO:write a conversion utility to convert List of usernames to 
		//collection of gigUsers with ids populated.
		//fix this asap to avoid confusion 
		//users.stream().map(s-> new GigUser(1L, s)).collect(Collectors.toList());
		List<GigUser> users = userService.findByEmails(usernames);
		ModelAndView view = new ModelAndView("tl/list", "users", users);
		view.addObject("isFromActive", true);
		return view;
	}
	
	@RequestMapping("{id}")
	public ModelAndView view(@PathVariable("id") GigUser user) {
		return new ModelAndView("tl/view", "user", user);
	}

	@PostMapping()
	@PreAuthorize("isGigAdmin() and hasRole('USER') and principal.username == 'samuelmosessegal@gmail.com'")
	public ModelAndView create(
			@Validated(PasswordValidationGroup.class) final GigUser user,
			final BindingResult result,
			final RedirectAttributes redirect) {

		if (result.hasErrors()) {
			return new ModelAndView("tl/form", "formErrors", result.getAllErrors());
		}
		try {
			if (user.getId() == null) {
				userService.registerNewUser(user, null);
				user.setEnabled(true);
				userService.updateExistingUser(user);
				redirect.addFlashAttribute("globalMessage", "New user has been created");
			} else {
				userService.updateExistingUser(user);
				redirect.addFlashAttribute("globalMessage", "User " + user.getEmail() + " has been updated");
			}
		} catch (EmailExistsException | Exception e) {
			result.addError(new FieldError("user", "email", e.getMessage()));
			return new ModelAndView("tl/form", "user", user);
		}
		return new ModelAndView("redirect:/user/{user.id}", "user.id", user.getId());
	}

	@GetMapping("delete/{id}")
	public ModelAndView delete(@PathVariable("id") final Long id) {
		userService.removeUser(id);
		return new ModelAndView("redirect:/");
	}

	@GetMapping("modify/{id}")
	public ModelAndView modifyForm(@PathVariable("id") final GigUser user) {
		return new ModelAndView("tl/updateForm", "gigUser", user);
	}

	@GetMapping(params = "form")
	@PreAuthorize("isGigAdmin()  and hasRole('USER') and principal.username == 'samuelmosessegal@gmail.com'")
	public String createForm(@ModelAttribute final GigUser user) {
		return "tl/form";
	}

	@PostMapping("/update")
	public ModelAndView update(@Validated(PasswordValidationGroup.class) final GigUser gigUser,
			final BindingResult result,
			final RedirectAttributes redirect) {
		log.debug("Updating User : {}", gigUser);
		if (result.hasErrors()) {
			return new ModelAndView("tl/updateForm");
		}
		try {
			userService.changePassword(gigUser, gigUser.getPassword());
			userService.updateExistingUser(gigUser);
		} catch (EmailExistsException | Exception e) {
			log.error("Error updating User {} :: {}", gigUser, e.getMessage(), e);
			return new ModelAndView("tl/updateForm");
		}
		return new ModelAndView("redirect:/user/{user.id}", "user.id", gigUser.getId());
	}
}
