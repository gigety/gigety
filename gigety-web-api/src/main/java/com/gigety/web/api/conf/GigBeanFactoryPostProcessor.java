//package com.gigety.web.api.conf;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.beans.factory.support.DefaultListableBeanFactory;
//import org.springframework.stereotype.Component;
//
//@Component
//public class GigBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
//
//	private static final String SERIALIZATION_ID = "-17560265";
//	@Override
//	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//		if(beanFactory instanceof DefaultListableBeanFactory) {
//			DefaultListableBeanFactory dlbf =  (DefaultListableBeanFactory) beanFactory;
//			dlbf.setSerializationId(SERIALIZATION_ID);
//		}
//	}
//
//}
