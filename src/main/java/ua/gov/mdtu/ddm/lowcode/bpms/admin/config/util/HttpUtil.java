package ua.gov.mdtu.ddm.lowcode.bpms.admin.config.util;

import javax.servlet.http.HttpServletRequest;
import ua.gov.mdtu.ddm.lowcode.bpms.admin.config.security.WebAppSecurityConfig;

public final class HttpUtil {

  public static String getDefaultRedirectUri(HttpServletRequest request) {
    String requestUrl = request.getRequestURL().toString();
    return requestUrl.substring(0, requestUrl.indexOf(request.getRequestURI()))
        + WebAppSecurityConfig.CAMUNDA_APP_REDIRECT_PATH;
  }
}
