package com.gigety.ur.web.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gigety.ur.db.model.GigUser;
import com.gigety.ur.db.model.PWResetToken;
import com.gigety.ur.db.model.SecurityQuestion;
import com.gigety.ur.db.model.UserSecurityQuestion;
import com.gigety.ur.db.model.VerificationToken;
import com.gigety.ur.security.registration.OnRegistrationCompleteEvent;
import com.gigety.ur.service.AsyncEmailService;
import com.gigety.ur.service.GigUserService;
import com.gigety.ur.service.SecurityQuestionService;
import com.gigety.ur.util.GigUrls;
import com.gigety.ur.util.validation.EmailExistsException;
import com.gigety.ur.util.validation.FormValidationGroup;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 *
 * Registration Web Controller
 */
@Controller
@Slf4j
public class RegistrationController {

	private final GigUserService userService;
	private final ApplicationEventPublisher appEventPublisher;
	private final MessageSource messageSource;
	private final AsyncEmailService emailService;
	private final SecurityQuestionService securityQuestionService;

	public RegistrationController(GigUserService userService, ApplicationEventPublisher appEventPublisher,
			MessageSource messageSource, AsyncEmailService emailService,
			SecurityQuestionService securityQuestionService) {
		super();
		this.userService = userService;
		this.appEventPublisher = appEventPublisher;
		this.messageSource = messageSource;
		this.emailService = emailService;
		this.securityQuestionService = securityQuestionService;
	}

	/**
	 * Sign up - redirects to registration sign up page
	 * 
	 * @return
	 */
	@GetMapping("signup")
	public ModelAndView registrationForm() {
		Map<String, Object> model = new HashMap<>();
		model.put("gigUser", new GigUser());
		model.put("questions", securityQuestionService.findAll());
		return new ModelAndView("registrationPage", model);
	}

	/**
	 * Register a new user. User will initially be set to disabled. A confirmation
	 * email is sent to users email. This link can be used to enable the new user.
	 * 
	 * @param user
	 * @param result
	 * @param request
	 * @param redirectAttributes
	 * @param locale
	 * @return
	 */
	@RequestMapping("/reg/register")
	public ModelAndView registerUser(@Validated(FormValidationGroup.class) final GigUser user,
			final BindingResult result,
			@RequestParam final Long questionId,
			@RequestParam final String answer,
			final HttpServletRequest request,
			final RedirectAttributes redirectAttributes,
			final Locale locale) {

		try {
			if (result.hasErrors()) {
				ModelAndView v = new ModelAndView("registrationPage", "gigUser", user);
				v.addObject("questions", securityQuestionService.findAll());
				return v;
			}
			SecurityQuestion sq = securityQuestionService.findQuestionById(questionId);
			user.setUserSecurityQuestion(new UserSecurityQuestion(sq, user, answer));
			final GigUser registered = userService.registerNewUser(user);
			registered.setEnabled(false);
			final String appUrl = getAppUrl(request);
			log.debug("Create User {} ", registered);
			appEventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, appUrl, locale));

		} catch (EmailExistsException | Exception e) {
			log.error("Error Registering User {} : {}", user, e.getMessage(), e);
			result.addError(new FieldError("gigUser", "email", e.getMessage()));
			ModelAndView v = new ModelAndView("registrationPage", "gigUser", user);

			v.addObject("questions", securityQuestionService.findAll());
			return v;
		}

		ModelAndView view = new ModelAndView("loginPage");
		view.addObject("message",
				messageSource.getMessage("email.reg.conf.sent", new Object[] { user.getEmail() }, locale));
		return view;
	}

	/**
	 * URL with included token to enable a new user account. Token ,must have been
	 * pre-generated for whom it may concern THis is last step in generic
	 * registration process.
	 * 
	 * @param model
	 * @param token
	 * @param redirectAttributes
	 * @param locale
	 * @return
	 */
	@GetMapping("/reg/confirm-reg")
	public ModelAndView confirmRegistration(final Model model,
			@RequestParam("token") final String token,
			final RedirectAttributes redirectAttributes,
			Locale locale) {

		final VerificationToken vToken = userService.getVerificationToken(token);

		if (vToken == null) {
			redirectAttributes.addFlashAttribute("errorMessage",
					messageSource.getMessage("invalid.conf.token", null, locale));
			return new ModelAndView("redirect:/login");
		}
		if (tokenExpired(vToken)) {
			redirectAttributes.addFlashAttribute("errorMessage",
					messageSource.getMessage("reg.token.expired", new Object[] { vToken.getExpiryDate() }, locale));
		} else {

			final GigUser user = vToken.getGigUser();
			user.setEnabled(true);
			userService.saveRegisteredUser(user);
			redirectAttributes.addFlashAttribute("message",
					messageSource.getMessage("account.verified", new Object[] { user.getEmail() }, locale));

		}
		return new ModelAndView(GigUrls.REDIRECT_LOGIN);

	}

	/**
	 * Send a resetPassword link to given email
	 * 
	 * @param request
	 * @param userEmail
	 * @param redirectAttributes
	 * @param locale
	 * @return
	 */
	@RequestMapping("/reg/resetpw")
	@ResponseBody
	public ModelAndView resetPassword(HttpServletRequest request,
			@RequestParam("email") final String userEmail,
			final RedirectAttributes redirectAttributes,
			final Locale locale) {

		final GigUser user = userService.findByEmail(userEmail);
		if (user != null) {
			final String appUrl = getAppUrl(request);
			emailService.sendPWResetEmail(user, appUrl, locale);
			redirectAttributes.addFlashAttribute("message",
					messageSource.getMessage("password.reset.email", new Object[] { userEmail }, locale));

		}
		return new ModelAndView(GigUrls.REDIRECT_LOGIN);
	}

	/**
	 * Load the update password page, this is reached via url with a token
	 * parameter. Token must have been pre-generated for a given user. Used in
	 * password recovery process
	 * 
	 * @param id
	 * @param token
	 * @param redirectAttributes
	 * @param locale
	 * @return
	 */
	@RequestMapping("/reg/updatepw")
	public ModelAndView updatePwPage(@RequestParam("id") final long id,
			@RequestParam("token") final String token,
			final RedirectAttributes redirectAttributes,
			final Locale locale) {

		final PWResetToken pwResetToken = userService.getPWRestToken(token);

		if (pwResetToken == null) {
			// TODO: If no token found, currently we redirect saying token does not exist.
			// Maybe revisit this with some kind of flow to further investigate why the url
			// is invalid.
			// Maybe update link to include email, and check if user exists;
			String message = messageSource.getMessage("pw.token.not.found", new Object[] { token }, locale);
			log.warn(message);
			redirectAttributes.addFlashAttribute("errorMessage", message);
			return new ModelAndView(GigUrls.REDIRECT_LOGIN);
		}
		final GigUser user = pwResetToken.getGigUser();

		// check if token belongs to user
		if (!user.getId().equals(id)) {
			redirectAttributes.addFlashAttribute("errorMessage",
					messageSource.getMessage("invalid.pw.token", null, locale));
		}

		// check if expiration has expired
		final Calendar cal = Calendar.getInstance();
		if ((pwResetToken.getExpiryDate().getTime() - cal.getTime().getTime() <= 0)) {
			redirectAttributes.addFlashAttribute("errorMessage",
					messageSource.getMessage("pw.token.expired", null, locale));
			return new ModelAndView(GigUrls.REDIRECT_LOGIN);
		}
		// pass along required token
		final ModelAndView view = new ModelAndView("resetpw");
		UserSecurityQuestion userQuestion = securityQuestionService.findByUser(user);
		view.addObject("gigUser", user);
		view.addObject("token", token);
		view.addObject("userQuestion", userQuestion);

		return view;
	}

	/**
	 * Update users password. Used for lost password case. A token assigned to given
	 * user is required.
	 * 
	 * @param password
	 * @param passwordConfirmation
	 * @param token
	 * @param redirectAttributes
	 * @param locale
	 * @return
	 */
	@PostMapping("/reg/savepw")
	@ResponseBody
	public ModelAndView savePassword(@Validated(FormValidationGroup.class) final GigUser gigUser,
			final BindingResult result,
			@RequestParam("token") String token,
			@RequestParam final String answer,
			final RedirectAttributes redirectAttributes,
			final Locale locale,
			final Model model) {

		final PWResetToken pToke = userService.getPWRestToken(token);
		if (result.hasErrors()) {
			final GigUser user = pToke.getGigUser();
			gigUser.setUserSecurityQuestion(user.getUserSecurityQuestion());
			gigUser.setId(user.getId());
			return reloadRestPw(gigUser, token, null);
			//ModelAndView v = new ModelAndView("resetpw", "gigUser", user);
			//return v;
		}
		if (pToke == null) {
			log.warn("No pwRestToken found for token {}", token);
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("invalid.pw.token", null, locale));
		} else {
			final GigUser user = pToke.getGigUser();
//			if (!password.equals(passwordConfirmation)) {
//				return  reloadRestPw(user, token,messageSource.getMessage("password.no.match", null, locale));
//			}

			if (user == null) {
				log.warn("Unknown User for token {}", pToke);
				redirectAttributes.addFlashAttribute("message", messageSource.getMessage("unknown.user", null, locale));
			} else {
				try {
					String savedAnswer = securityQuestionService.findByUser(user).getAnswer();
					if (answer.equals(savedAnswer)) {
						userService.changePassword(user, gigUser.getPassword());
						redirectAttributes.addFlashAttribute("message",
								messageSource.getMessage("pw.reset.success", null, locale));
					} else {

						return reloadRestPw(user, token, messageSource.getMessage("answer.incorrect", null, locale));
					}
				} catch (Exception e) {
					log.error("Error saving user password: {}", e.getMessage(), e);
					return reloadRestPw(user, token, e.getMessage());
				}
			}
		}

		return new ModelAndView(GigUrls.REDIRECT_LOGIN);
	}

	private ModelAndView reloadRestPw(GigUser user,
			String token,
			String errorMessage) {
		UserSecurityQuestion userQuestion = securityQuestionService.findByUser(user);
		ModelAndView view = new ModelAndView("resetpw");
		view.addObject("gigUser", user);
		view.addObject("token", token);
		view.addObject("userQuestion", userQuestion);
		if(errorMessage != null) {
			view.addObject("errorMessage", errorMessage);
		}
		return view;
	}

	private boolean tokenExpired(VerificationToken vToken) {
		return (vToken.getExpiryDate().getTime() - Calendar.getInstance().getTime().getTime() <= 0);
	}

	private String getAppUrl(HttpServletRequest request) {
		return String.format("%1$s%2$s:%3$s%4$s", GigUrls.HTTP, request.getServerName(), request.getServerPort(),
				request.getContextPath());
	}
}
