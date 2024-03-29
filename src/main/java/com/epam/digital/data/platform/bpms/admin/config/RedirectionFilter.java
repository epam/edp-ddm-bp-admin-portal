/*
 * Copyright 2023 EPAM Systems.
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

package com.epam.digital.data.platform.bpms.admin.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.epam.digital.data.platform.bpms.admin.config.util.HttpUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RedirectionFilter extends GenericFilterBean {

    public static final String BASE_PATH = "/";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (BASE_PATH.equals(httpRequest.getRequestURI())) {
            log.info("Redirecting to " + HttpUtil.getDefaultRedirectUri(httpRequest));
            ((HttpServletResponse) response).sendRedirect(HttpUtil.getDefaultRedirectUri(httpRequest));    //NOSONAR
            return;
        }
        filterChain.doFilter(request, response);
    }
}
