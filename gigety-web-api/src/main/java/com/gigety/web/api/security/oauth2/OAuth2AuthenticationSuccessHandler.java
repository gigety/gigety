package com.gigety.web.api.security.oauth2;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.gigety.web.api.conf.AppProperties;
import com.gigety.web.api.exception.BadRequestException;
import com.gigety.web.api.security.JwtTokenProvider;
import com.gigety.web.api.util.CookieUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtTokenProvider tokenProvider;
	private final AppProperties appProperties;
	private final HttpCookieOAuth2AuthorizationRequestRepository cookieAuthRequestRepo;

	public OAuth2AuthenticationSuccessHandler(JwtTokenProvider tokenProvider, AppProperties appProperties,
			HttpCookieOAuth2AuthorizationRequestRepository cookieAuthRequestRepo) {
		super();
		this.tokenProvider = tokenProvider;
		this.appProperties = appProperties;
		this.cookieAuthRequestRepo = cookieAuthRequestRepo;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String targetUrl = determineTargetUrl(request, response, authentication);
		log.debug("Authentication Success :: Target URL :: {}", targetUrl);
		if (response.isCommitted()) {
			log.warn("RESPONSE ALREADY COMMITTED, UNABLE TO REDIRECT {}", targetUrl);
			return;
		}
		clearAuthenticationAttributes(request, response);
	}

	private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		cookieAuthRequestRepo.removeAuthorizationRequetCookies(request, response);
	}

	private String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {

		Optional<String> redirectUri = CookieUtils
				.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
				.map(Cookie::getValue);
		if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
			throw new BadRequestException("RedirectURI is unauthorized, please seek attention");
		}
		String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
		String token = tokenProvider.generateToken(authentication);

		String ret = UriComponentsBuilder.fromUriString(targetUrl).queryParam("token", token).build().toUriString();
		log.debug("RETURN TARGET URL :: {}", ret);
		return ret;
	}

	private boolean isAuthorizedRedirectUri(String uri) {
		URI clientRedirectUri = URI.create(uri);
		return appProperties.getOauth2().getAuthorizationRedirectUris().stream().anyMatch(authorizedRedirectUri -> {
			URI authorizedUri = URI.create(authorizedRedirectUri);
			log.debug("IsAuthorizedRedirectURI :: {}", authorizedUri);
			if (authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
					&& authorizedUri.getPort() == clientRedirectUri.getPort()) {
				log.debug("true");
				return true;
			}
			log.debug("false");
			return false;
		});
	}

}
