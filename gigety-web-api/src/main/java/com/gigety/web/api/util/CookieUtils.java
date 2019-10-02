package com.gigety.web.api.util;

import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.SerializationUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CookieUtils {

	public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
		return Arrays.asList(request.getCookies()).stream().filter(cookie -> cookie.getName().equals(name)).findAny();
	}

	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Arrays.asList(request.getCookies()).stream().filter(cookie -> cookie.getName().equals(name))
				.forEach(cookie -> {
					log.debug("Cookie {} = {}", cookie.getName(), cookie.getValue());
					cookie.setValue("");
					cookie.setPath("/");
					cookie.setMaxAge(0);

					response.addCookie(cookie);
					log.debug("Removing Cookie :: {}", cookie.getName());
					log.debug("NOW Cookie {} = {}", cookie.getName(), cookie.getValue());
				});
	}

	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(maxAge);
		log.debug("Adding Cookie {}", cookie);
	}
	
	public static <T> T deserialize(Cookie cookie, Class<T> cls) {
		log.debug("Deserializing cookie :: {}", cookie);
		return cls.cast(SerializationUtils.deserialize(Base64.getUrlDecoder().decode(cookie.getValue())));
	}

	public static String serialize(Object object) {
		return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(object));
	}
}
