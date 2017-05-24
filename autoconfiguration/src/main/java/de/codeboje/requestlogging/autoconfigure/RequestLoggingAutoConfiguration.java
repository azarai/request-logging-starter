package de.codeboje.requestlogging.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import de.codeboje.requestlogging.RequestContextLoggingFilter;

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

}
