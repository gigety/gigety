package com.gigety.ur.conf.filters;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GigLogFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request,
			ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String url = httpServletRequest.getRequestURL().toString();
		String queryString = Optional.ofNullable(httpServletRequest.getQueryString()).map(value -> "?" + value)
				.orElse("");
		log.trace("Request URL :: {}{}", url, queryString);
		
		chain.doFilter(request, response);
	}

}
