/*
 * Copyright (c) 2015-2018 Hallin Information Technology AB
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.codekvast.login.api;

import io.codekvast.common.customer.CustomerService;
import io.codekvast.common.customer.UnrecognizedEmailException;
import io.codekvast.login.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

/**
 * Implements the API used by login-api.service.ts
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8088")
@RequiredArgsConstructor
@Slf4j
public class LoginApiController {

    private final CustomerService customerService;

    @ExceptionHandler
    public void onUnrecognizedEmailException(UnrecognizedEmailException e, HttpServletRequest request, HttpServletResponse response)
        throws IOException {

        // This happens after a successful OAuth dance, but the principal contains an unrecognized email address.
        logger.warn("Invalid login attempt: " + e.getMessage());

        new CookieClearingLogoutHandler("sessionToken", "XSRF-TOKEN").logout(request, response, null);
        new SecurityContextLogoutHandler().logout(request, response, null);
        response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
    }

    /**
     * This is an unprotected endpoint that returns true if the user is authenticated.
     *
     * @param principal The logged in principal, or null if unauthenticated.
     * @return true iff the user is authenticated.
     */
    @RequestMapping("/is-authenticated")
    public boolean isAuthenticated(Principal principal) {
        logger.debug("isAuthenticated(): principal={}", principal);
        return principal != null;
    }

    /**
     * This is a protected endpoint that requires authentication.
     *
     * @param authentication The OAuth2 authentication object
     * @return A User object.
     */
    @RequestMapping("/user")
    public User user(OAuth2Authentication authentication) {
        logger.info("Authentication={}", authentication);

        if (authentication == null) {
            return null;
        }

        //noinspection unchecked
        return getUserFromDetails((Map<String, String>) authentication.getUserAuthentication().getDetails());

        // TODO: instead of just returning a User, create a sessionToken cookie and redirect to settings.getDashboardUrl();
    }

    User getUserFromDetails(Map<String, String> details) {
        String email = details.get("email");

        String id = details.get("link"); // Facebook
        if (id == null) {
            id = details.get("url"); // Github
        }
        if (id == null) {
            id = details.get("profile"); // Google+
        }
        if (id == null) {
            id = details.get("sub"); // Google+
        }

        User user = User.builder()
                        .id(id)
                        .name(details.get("name"))
                        .email(email)
                        .customerData(customerService.getCustomerDataByUserEmail(email))
                        .build();
        logger.debug("Returning {}", user);
        return user;
    }


}
