package com.gigety.ur.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.db.repo.GigUserRepository;
import com.gigety.ur.service.GigUserService;
import com.gigety.ur.util.validation.EmailExistsException;

/**
 * User Web Controller
 */
@Controller
@RequestMapping("/user")
public class UserController {

	private final GigUserRepository userRepo;
	private final GigUserService userService;

	@Autowired
	public UserController(GigUserRepository userRepo, GigUserService userService) {
		super();
		this.userRepo = userRepo;
		this.userService = userService;
	}

	@RequestMapping
	public ModelAndView list() {
		Iterable<GigUser> users = this.userRepo.findAll();
		return new ModelAndView("tl/list", "users", users);
	}

	@RequestMapping("{id}")
	public ModelAndView view(@PathVariable("id") GigUser user) {
		return new ModelAndView("tl/view", "user", user);
	}

//	@RequestMapping(method = RequestMethod.POST)
//	public ModelAndView create(
//			@Valid final GigUser user,
//			final BindingResult result,
//			final RedirectAttributes redirect) {
//
//		if (result.hasErrors()) {
//			return new ModelAndView("tl/form", "formErrors", result.getAllErrors());
//		}
//		try {
//			if (user.getId() == null) {
//				userService.registerNewUser(user);
//				redirect.addFlashAttribute("globalMessage", "New user has been created");
//			} else {
//				userService.updateExistingUser(user);
//				redirect.addFlashAttribute("globalMessage", "User " + user.getEmail() + " has been updated");
//			}
//		} catch (EmailExistsException e) {
//			result.addError(new FieldError("user", "email", e.getMessage()));
//			return new ModelAndView("tl/form", "user", user);
//		}
//		return new ModelAndView("redirect:/user/{user.id}", "user.id", user.getId());
//	}

	@GetMapping("delete/{id}")
	public ModelAndView delete(@PathVariable("id") final Long id) {
		userRepo.findById(id).ifPresent(user -> userRepo.delete(user));
		return new ModelAndView("redirect:/");
	}

	@GetMapping("modify/{id}")
	public ModelAndView modifyForm(@PathVariable("id") final GigUser user) {
		return new ModelAndView("tl/form", "user", user);
	}

	@GetMapping(params = "form")
	public String createForm(@ModelAttribute final GigUser user) {
		return "tl/form";
	}
}
