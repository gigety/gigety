package com.gigety.ws.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SessionConnectedEventListener implements ApplicationListener<SessionConnectedEvent> {

//    private WebSocketSessionService webSocketSessionService;
//
//    public SessionConnectedEventListener(WebSocketSessionService webSocketSessionService) {
//        this.webSocketSessionService = webSocketSessionService;
//    }

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
    	log.info("Gigety Web Socket Session connected with messeage {0} for user {1}", event.getMessage());
    	
        //webSocketSessionService.saveSession(event);
    }
}