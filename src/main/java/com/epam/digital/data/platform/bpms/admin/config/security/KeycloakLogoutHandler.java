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

import com.epam.digital.data.platform.bpms.admin.config.util.HttpUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

/**
 * Keycloak Logout Handler.
 */
@Slf4j
@Service
public class KeycloakLogoutHandler implements LogoutSuccessHandler {

  @Value("${spring.security.oauth2.client.provider.keycloak.logout-url:}")
  private String oauth2LogoutUri;

  private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  /**
   * {@inheritDoc}
   */
  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {
    if (StringUtils.isEmpty(oauth2LogoutUri)) {
      return;
    }
    String redirectUri = HttpUtil.getDefaultRedirectUri(request);
    String logoutUrl = oauth2LogoutUri + "?redirect_uri=" + redirectUri;

    // do logout by redirecting to Keycloak logout
    log.debug("redirect to logout URL {}", logoutUrl);
    redirectStrategy.sendRedirect(request, response, logoutUrl);
  }
}
