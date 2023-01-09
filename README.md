# spring-validation-custom-property-name-support

This project is created to showcase an issue when
using: https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-property-node-name-provider
Hibernate Validator (HV) feature, in combination with Spring.

See: https://github.com/spring-projects/spring-framework/issues/24811 for more details.

There is one test that is failing with an exception - this is expected behaviour as this test is
used to show how Spring will react to this HV feature.
