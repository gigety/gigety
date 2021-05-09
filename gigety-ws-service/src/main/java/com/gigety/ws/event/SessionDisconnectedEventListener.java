package com.gigety.ws.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SessionDisconnectedEventListener implements ApplicationListener<SessionDisconnectEvent> {

	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		
		log.debug("Gigety Web Socket Connection Disconnecting Event :: ", event);
	}

}
