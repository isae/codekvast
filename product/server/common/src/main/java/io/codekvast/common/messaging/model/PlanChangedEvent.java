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
 * An event that is sent when a customer changes plan.
 *
 * @author olle.hallin@crisp.se
 */
@Value
@Builder
@JsonDeserialize(builder = PlanChangedEvent.PlanChangedEventBuilder.class)
public class PlanChangedEvent implements CodekvastEvent {
    @NonNull Long customerId;
    @NonNull String oldPlan;
    @NonNull String newPlan;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PlanChangedEventBuilder {
        // Will be filled out by @lombok.Builder
    }

    public static PlanChangedEvent sample() {
        return PlanChangedEvent.builder()
                               .customerId(1L)
                               .oldPlan("oldPlan")
                               .newPlan("newPlan")
                               .build();
    }
}