package com.gigety.ur.web.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gigety.ur.service.RunAsService;
import com.gigety.ur.util.RoleConstants;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/runas")
@AllArgsConstructor
public class RunAsController {

	private final RunAsService runAsService;

	@RequestMapping
	@ResponseBody
	@Secured({RoleConstants.ROLE_USER, RoleConstants.RUN_AS_REPORTER})
	public String tryRunAs() {
		return runAsService.getCurrentUser().getAuthorities().toString();
	}
}
