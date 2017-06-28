package de.codeboje.requestlogging.autoconfigure;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;

import de.codeboje.requestlogging.RequestContextLoggingFilter;
import de.codeboje.requestlogging.RequestContextLoggingInterceptor;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(RequestContextLoggingFilter.class)
@EnableConfigurationProperties(RequestLoggingProperties.class)
public class RequestLoggingAutoConfiguration {

	@Autowired
	private RequestLoggingProperties requestLoggingProperties;

	@Bean
	@Order(1)
	@ConditionalOnMissingBean
	public RequestContextLoggingFilter requestContextLoggingFilter() {
		return new RequestContextLoggingFilter(requestLoggingProperties.getRequestHeaderId(),
				requestLoggingProperties.getLogIdentifier());
	}

	@Bean
	@ConditionalOnMissingBean
	public RequestContextLoggingInterceptor requestContextLoggingInterceptor() {
		return new RequestContextLoggingInterceptor(requestLoggingProperties.getRequestHeaderId(),
				requestLoggingProperties.getLogIdentifier());
	}

	@Bean
	public BeanPostProcessor beanPostProcessor(RequestContextLoggingInterceptor requestContextLoggingInterceptor) {
		return new BeanPostProcessor() {
			@Override
			public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
				return bean;
			}

			@Override
			public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
				if (bean instanceof RestTemplate) {
					((RestTemplate) bean).getInterceptors().add(0, requestContextLoggingInterceptor);
				}
				return bean;
			}
		};
	}
}
