package ua.gov.mdtu.ddm.lowcode.bpms.admin.config.security;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ua.gov.mdtu.ddm.lowcode.bpms.admin.config.util.HttpUtil;

/**
 * Redirect to default camunda cockpit app page.
 */
@Component
public class AdminPortalAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {

    redirectStrategy.sendRedirect(request, response, HttpUtil.getDefaultRedirectUri(request));
  }
}
