package com.epam.digital.data.platform.bpms.admin.config.security;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.rest.security.auth.AuthenticationResult;
import org.camunda.bpm.engine.rest.security.auth.impl.ContainerBasedAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

/**
 * OAuth2 Authentication Provider for usage with Keycloak.
 */
@Component
public class KeycloakAuthenticationProvider extends ContainerBasedAuthenticationProvider {

  @Override
  public AuthenticationResult extractAuthenticatedUser(HttpServletRequest request,
      ProcessEngine engine) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof OAuth2AuthenticationToken) || !(authentication
        .getPrincipal() instanceof OidcUser)) {
      return AuthenticationResult.unsuccessful();
    }
    String userId = ((OidcUser) authentication.getPrincipal()).getName();
    if (StringUtils.isEmpty(userId)) {
      return AuthenticationResult.unsuccessful();
    }

    AuthenticationResult authenticationResult = new AuthenticationResult(userId, true);
    authenticationResult.setGroups(getUserGroups(userId, engine));

    return authenticationResult;
  }

  private List<String> getUserGroups(String userId, ProcessEngine engine) {
    return engine.getIdentityService().createGroupQuery().groupMember(userId).list().stream()
        .map(Group::getId)
        .collect(Collectors.toList());
  }

}
