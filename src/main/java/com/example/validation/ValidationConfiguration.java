package com.example.validation;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.spi.nodenameprovider.JavaBeanProperty;
import org.hibernate.validator.spi.nodenameprovider.Property;
import org.hibernate.validator.spi.nodenameprovider.PropertyNodeNameProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfiguration {
	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean(ObjectMapper objectMapper) {
		return new CustomLocalValidatorFactoryBean(objectMapper);
	}

	private static class CustomLocalValidatorFactoryBean extends LocalValidatorFactoryBean {
		private final ObjectMapper objectMapper;

		public CustomLocalValidatorFactoryBean(ObjectMapper objectMapper) {
			this.objectMapper = objectMapper;
		}

		@Override
		protected void postProcessConfiguration(javax.validation.Configuration<?> configuration) {
			HibernateValidatorConfiguration hibernateValidatorConfiguration = (HibernateValidatorConfiguration) configuration;
			hibernateValidatorConfiguration.propertyNodeNameProvider(
				new ValidationConfiguration.JacksonPropertyNodeNameProvider(objectMapper));
		}
	}

	private static class JacksonPropertyNodeNameProvider implements PropertyNodeNameProvider {
		private final ObjectMapper objectMapper;

		public JacksonPropertyNodeNameProvider(ObjectMapper objectMapper) {
			this.objectMapper = objectMapper;
		}

		@Override
		public String getName(Property property) {
			if (property instanceof JavaBeanProperty) {
				return getJavaBeanPropertyName((JavaBeanProperty) property);
			}

			return getDefaultName(property);
		}

		private String getJavaBeanPropertyName(JavaBeanProperty property) {
			JavaType type = objectMapper.constructType(property.getDeclaringClass());
			BeanDescription desc = objectMapper.getSerializationConfig().introspect(type);

			return desc.findProperties()
				.stream()
				.filter(prop -> prop.getInternalName().equals(property.getName()))
				.map(BeanPropertyDefinition::getName)
				.findFirst()
				.orElse(property.getName());
		}

		private String getDefaultName(Property property) {
			return property.getName();
		}
	}
}
