/*
 * Copyright (c) 2015-2019 Hallin Information Technology AB
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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.codekvast.backoffice.facts;

import io.codekvast.common.messaging.model.UserAuthenticatedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.Instant;

/**
 * @author olle.hallin@crisp.se
 */
@Data
@AllArgsConstructor
public class UserAuthenticated implements PersistentFact {
    @NonNull
    private final String emailAddress;

    @NonNull
    private final String authenticationProvider;

    @NonNull
    private Instant instant;

    private int count;

    public static UserAuthenticated of(UserAuthenticatedEvent event) {
        return new UserAuthenticated(event.getEmailAddress(), event.getAuthenticationProvider(), Instant.now(), 1);
    }
}