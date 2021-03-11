package com.gigety.ws.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gigety.ws.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NotificationsController {

	private final ChatRoomService chatRoomService;
	
	@GetMapping("/notifications/{userid}")
	public ResponseEntity<?> findUserNotifications(@PathVariable String userid) {
		return null;
	}
}
