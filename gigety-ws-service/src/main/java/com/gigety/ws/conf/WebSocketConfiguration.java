package com.gigety.ws.conf;

import java.util.List;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

//	@Value("${spring.rabbitmq.host}")
//	private String host;
//	@Value("${spring.rabbitmq.port}")
//	private int port;
//	@Value("${spring.rabbitmq.username}")
//	private String username;
//	@Value("${spring.rabbitmq.password}")
//	private String password;

	@Value("${active-mq-url}")
	private String activeMqUrl;

	/**
	 * Register STOMP endpoint so clients can connect to STOMP. Enable SockJS for
	 * fallback options
	 */

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws")
				.setAllowedOrigins("https://localhost.com")
				// .setAllowedOrigins("*")
				.withSockJS();
	}

	/**
	 * Convert Message from / to JSON
	 */
	@Override
	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
		DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
		resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setObjectMapper(new ObjectMapper());
		converter.setContentTypeResolver(resolver);
		messageConverters.add(converter);
		return false;
	}

	/**
	 * Enable a message broker - currently its an in memory - will change for
	 * production The /msg prefix is for @MessageMapping methods.
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableStompBrokerRelay("/topic", "/queue", "/user");
		registry.setApplicationDestinationPrefixes("/msg");
		registry.setUserDestinationPrefix("/user");
	}

	@Bean
	public ActiveMQConnectionFactory connectionFatory() {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
		factory.setBrokerURL(activeMqUrl);
		return factory;
	}

}
