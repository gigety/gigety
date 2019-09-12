package com.gigety.ur.conf;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;

import com.gigety.ur.service.impl.AsyncEmailServiceImpl;

/**
 * SringAsyncConfiguration - Configure async thread to be used in conjunction 
 * with @Async("gigaThread")
 * @see AsyncEmailServiceImpl
 */
@Configuration
@EnableAsync
public class SpringAsyncConfiguration implements AsyncConfigurer {

	/**
	 * getAsyncExecutor - configure the threadPoolTaskExecutor for asynchronous 
	 * invocations. 
	 */
	@Override
	@Bean(destroyMethod = "shutdown", name = "gigaThread")
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		//executor.setCorePoolSize(7);
		//executor.setMaxPoolSize(42);
		executor.setThreadNamePrefix("giga-thread-");
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		// TODO Auto-generated method stub
		return AsyncConfigurer.super.getAsyncUncaughtExceptionHandler();
	}

	/**
	 * methodInvokingFactoryBean - sets the securityContextHolder strategy to 
	 * SecurityContextHolder.MODE_INHERITABLETHREADLOCAL. THis allows 
	 * securityContext to propagate to async threads. Without setting this 
	 * strategy, the securityContext will b e null in saync threads.
	 * example - @see {@link AsyncEmailServiceImpl}
	 * @return
	 */
	@Bean
	public MethodInvokingFactoryBean methodInvokingFactoryBean() {
	    MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
	    methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
	    methodInvokingFactoryBean.setTargetMethod("setStrategyName");
	    methodInvokingFactoryBean.setArguments(new String[]{SecurityContextHolder.MODE_INHERITABLETHREADLOCAL});
	    return methodInvokingFactoryBean;
	}
	
}
