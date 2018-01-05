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
package io.codekvast.dashboard.webapp.model.methods;

import io.codekvast.dashboard.webapp.WebappService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * A validated parameters object for {@link WebappService#getMethods(GetMethodsRequest)}
 *
 * @author olle.hallin@crisp.se
 */
@Builder(toBuilder = true)
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Slf4j
public class GetMethodsRequest {

    /**
     * The signature to search for.
     */
    @NonNull
    @Size(min = 1, message = "signature must be at least 1 characters")
    private final String signature;

    /**
     * How many results to return.
     */
    @Min(value = 1, message = "maxResult must be >= 1")
    @Max(value = 10_000, message = "maxResults must be <= 10000")
    private final int maxResults;

    /**
     * Surround signature with "%" and replace "#" with "."
     */
    private final boolean normalizeSignature;

    /**
     * Only include methods that have been invoked before this timestamp.
     */
    @Min(value = 0, message = "onlyInvokedBeforeMillis must be >= 0")
    private final long onlyInvokedBeforeMillis;

    /**
     * Only include methods that have been invoked after this timestamp.
     */
    @Min(value = 0, message = "onlyInvokedAfterMillis must be >= 0")
    private final long onlyInvokedAfterMillis;

    /**
     * Suppress synthetic methods generated by e.g., Guice, Spring or the compiler.
     * Example: xxx..$FastClassByGuice$..yyy
     */
    private final boolean suppressSyntheticMethods;

    /**
     * Suppress methods that are untracked by the java agent.
     */
    private final boolean suppressUntrackedMethods;

    /**
     * Only include methods that have been collected for this number of days
     */
    @Min(value = 0, message = "minCollectedDays must be >= 0")
    private final int minCollectedDays;

    public String getNormalizedSignature() {
        String result;
        if (!normalizeSignature) {
            result = signature;
        } else {
            String sig = signature.replace("*", "%").replace("?", "_").replace("#", ".");
            sig = "%" + sig + "%";
            result = sig.replaceAll("%+", "%");
        }
        if (!result.equals(signature)) {
            logger.debug("Normalized '{}' to '{}'", signature, result);
        }
        return result;
    }

    public static GetMethodsRequest defaults() {
        return builder()
            .onlyInvokedBeforeMillis(WebappService.DEFAULT_ONLY_INVOKED_BEFORE_MILLIS)
            .onlyInvokedAfterMillis(WebappService.DEFAULT_ONLY_INVOKED_AFTER_MILLIS)
            .suppressUntrackedMethods(WebappService.DEFAULT_SUPPRESS_UNTRACKED_METHODS)
            .suppressSyntheticMethods(WebappService.DEFAULT_SUPPRESS_SYNTHETIC_METHODS)
            .maxResults(WebappService.DEFAULT_MAX_RESULTS)
            .minCollectedDays(WebappService.DEFAULT_MIN_COLLECTED_DAYS)
            .normalizeSignature(WebappService.DEFAULT_NORMALIZE_SIGNATURE)
            .signature("")
            .build();
    }

}
