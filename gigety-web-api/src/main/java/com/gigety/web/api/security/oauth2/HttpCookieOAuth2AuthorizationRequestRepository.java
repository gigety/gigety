package com.gigety.web.api.security.oauth2;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import com.gigety.web.api.util.CookieUtils;
import com.nimbusds.oauth2.sdk.util.StringUtils;

@Component
public class HttpCookieOAuth2AuthorizationRequestRepository
		implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

	public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
	public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
	private static final int cookieExpireSeconds = 180;
	private Logger log = LoggerFactory.getLogger(HttpCookieOAuth2AuthorizationRequestRepository.class);
	
	@Override
	public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
		return CookieUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
				.map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class)).orElse(null);
	}

	@Override
	public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("Saving auth REQUEST");
		System.out.println("########################");
		System.out.println("########################");
		System.out.println("HUGH HUH");
		System.out.println("say wha?");
		System.out.println("oh well");
		if (authorizationRequest == null) {
			log.debug("Authorization is null :: Removing Cookies {} and {}", OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
					REDIRECT_URI_PARAM_COOKIE_NAME);
			CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
			CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
			return;
		}

		CookieUtils.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
				CookieUtils.serialize(authorizationRequest), cookieExpireSeconds);
		String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);

		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String name = paramNames.nextElement();
			log.debug("PAramName :: {}", name);
			String[] paramValues = request.getParameterValues(name);
			for (String pv : paramValues) {
				log.debug(pv);
			}
		}
		log.debug("authorizationRequest :: {}", authorizationRequest.getAuthorizationUri());
		log.debug("redirectUriAfterLogin :: {}", redirectUriAfterLogin);

		if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
			CookieUtils.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, cookieExpireSeconds);
		}

	}

	@Override
	public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
		log.debug("Removing or Loading Authorization Request??????");
		OAuth2AuthorizationRequest ret = this.loadAuthorizationRequest(request);
		log.debug("AUTH REQUEST :: {}", ret);
		ret.getAttributes().forEach((k, v) -> {
			log.debug("KEY: {}, VALUE: {}", k, v);
		});
		return ret;
	}

	@Override
	public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("Removing or Loading Authorization Request??????");
		OAuth2AuthorizationRequest ret = this.loadAuthorizationRequest(request);
		log.debug("AUTH REQUEST :: {}", ret);
		ret.getAttributes().forEach((k, v) -> {
			log.debug("KEY: {}, VALUE: {}", k, v);
		});
		return ret;
	}

	public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
		log.debug("Removing Authorization Cookies");
		CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
		CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
	}

}
