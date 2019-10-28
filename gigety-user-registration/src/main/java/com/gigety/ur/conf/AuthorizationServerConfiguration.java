package com.gigety.ur.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.gigety.ur.conf.properties.GigetyAuthProperties;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableAuthorizationServer
@NoArgsConstructor
@Slf4j
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private GigetyAuthProperties gigetyAuthProperties;
//	public AuthorizationServerConfiguration() {
//		super();
//	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
			.inMemory()
			//.jdbc(dataSource)
			.withClient("samo")
			.secret(passwordEncoder.encode("samo"))
			.authorizedGrantTypes( "password","authorization_code","refresh_token") 
			.refreshTokenValiditySeconds(3600 * 24)
			.scopes("read", "write", "trust")
			.resourceIds("samo")
			.redirectUris(gigetyAuthProperties.getRedirectUris().stream().toArray(String[]::new))
			.autoApprove(true)		
			.accessTokenValiditySeconds(3600)
			.and().build();
		
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints 
			.tokenStore(tokenStore())
			.authenticationManager(authenticationManager)
			.userDetailsService(userDetailsService)
			.accessTokenConverter(accessTokenConverter());
//			.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS);
//        endpoints.addInterceptor(new HandlerInterceptorAdapter() {
//
//            @Override
//            public boolean preHandle(HttpServletRequest hsr, HttpServletResponse rs, Object o) throws Exception {
//                rs.setHeader("Access-Control-Allow-Origin", "*");
//                rs.setHeader("Access-Control-Allow-Methods", "GET");
//                rs.setHeader("Access-Control-Max-Age", "3600");
//                rs.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
//                return true;
//                }
//            });
//        
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("isAuthenticated()");
		security.tokenKeyAccess("permitAll()");
		security.allowFormAuthenticationForClients();
		super.configure(security);
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		log.debug("Setting default token services");
		DefaultTokenServices dts = new DefaultTokenServices();
		dts.setTokenStore(tokenStore());
		dts.setSupportRefreshToken(true);
		//log.debug("AUTH: {}",dts.loadAuthentication("samo"));
		return dts;
	}
	
	@Value("${signing-key:samo}")
	private String signingKey;
	
	/**
	 * This converter must be duplicate on resourceServer.
	 * If Authorization and Resource server are in same app context, having
	 * one defined will suffice.
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(signingKey);
		return converter;
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	
}
