/*
 * Copyright 2021 EPAM Systems.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.digital.data.platform.bpms.admin.config.security;

import com.epam.digital.data.platform.bpms.security.config.BpmSecurityConfig;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.rest.security.auth.ProcessEngineAuthenticationFilter;
import org.camunda.bpm.webapp.impl.security.auth.ContainerBasedAuthenticationFilter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Camunda Web application SSO configuration.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@Import(BpmSecurityConfig.class)
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String ACTUATOR_URL = "/actuator/**";
  public static final String CAMUNDA_APP_REDIRECT_PATH = "/camunda/app/";

  private final KeycloakLogoutHandler keycloakLogoutHandler;
  private final AdminPortalAuthenticationSuccessHandler adminPortalAuthenticationSuccessHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    allowAccessToActuatorEndpoints(http);
    http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
        .requestMatchers().antMatchers("/**").and()
        .authorizeRequests(
            authorizeRequests ->
                authorizeRequests
                    .antMatchers("/login**", "/oauth2/authorization**")
                    .permitAll()
                    .antMatchers("/**", "/api/**", "/lib/**")
                    .authenticated())
        .oauth2Login().successHandler(adminPortalAuthenticationSuccessHandler)
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/camunda/**/logout"))
        .logoutSuccessHandler(keycloakLogoutHandler)
    ;
  }

  private void allowAccessToActuatorEndpoints(HttpSecurity http) throws Exception {
    log.info("Permit GET requests for anyone to actuator endpoints: {}", ACTUATOR_URL);
    http.authorizeRequests().antMatchers(HttpMethod.GET, ACTUATOR_URL).permitAll();
  }

  @Bean
  @SuppressWarnings({"rawtypes", "unchecked"})
  public FilterRegistrationBean containerBasedAuthenticationFilter(
      SecurityProperties securityProperties) {
    FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
    filterRegistration.setFilter(new ContainerBasedAuthenticationFilter());
    filterRegistration.setInitParameters(Collections.singletonMap(
        ProcessEngineAuthenticationFilter.AUTHENTICATION_PROVIDER_PARAM,
        KeycloakAuthenticationProvider.class.getName()));
    filterRegistration.setOrder(securityProperties.getFilter().getOrder() + 1);
    filterRegistration.addUrlPatterns("/*");
    return filterRegistration;
  }
}
