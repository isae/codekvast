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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.codekvast.common.messaging.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * An event that is sent after application details were fetched from an external system.
 *
 * @author olle.hallin@crisp.se
 */
@Value
@Builder
@JsonDeserialize(builder = AppDetailsUpdatedEvent.AppDetailsUpdatedEventBuilder.class)
public class AppDetailsUpdatedEvent implements CodekvastEvent {
    @NonNull Long customerId;
    @NonNull String applicationName;
    @NonNull String contactEmail;

    @JsonPOJOBuilder(withPrefix = "")
    public static class AppDetailsUpdatedEventBuilder {
        // Will be filled out by @lombok.Builder
    }

    public static AppDetailsUpdatedEvent sample() {
        return AppDetailsUpdatedEvent.builder()
                                     .customerId(1L)
                                     .applicationName("applicationName")
                                     .contactEmail("contactEmail")
                                     .build();
    }
}