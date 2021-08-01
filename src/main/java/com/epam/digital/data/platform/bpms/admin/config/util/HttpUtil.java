package com.epam.digital.data.platform.bpms.admin.config.util;

import com.epam.digital.data.platform.bpms.admin.config.security.WebAppSecurityConfig;
import javax.servlet.http.HttpServletRequest;

public final class HttpUtil {

  public static String getDefaultRedirectUri(HttpServletRequest request) {
    String requestUrl = request.getRequestURL().toString();
    return requestUrl.substring(0, requestUrl.indexOf(request.getRequestURI()))
        + WebAppSecurityConfig.CAMUNDA_APP_REDIRECT_PATH;
  }
}
