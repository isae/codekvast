/*
 * Copyright (c) 2015-2017 Crisp AB
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
package io.codekvast.javaagent.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.codekvast.javaagent.appversion.AppVersionResolver;
import io.codekvast.javaagent.model.Endpoints;
import io.codekvast.javaagent.model.v1.CommonPublicationData;
import io.codekvast.javaagent.util.ConfigUtils;
import io.codekvast.javaagent.util.Constants;
import lombok.*;
import okhttp3.OkHttpClient;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Encapsulates the configuration that is used by the Codekvast agent.
 *
 * @author olle.hallin@crisp.se
 */
@SuppressWarnings("ClassWithTooManyFields")
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class AgentConfig implements CodekvastConfig {
    public static final String INVOCATIONS_BASENAME = "invocations.dat";
    public static final String JVM_BASENAME = "jvm.dat";

    @NonNull
    private String licenseKey;

    @NonNull
    private String serverUrl;

    @NonNull
    private String aspectjOptions;

    private boolean bridgeAspectjMessagesToSLF4J;

    @NonNull
    private String methodVisibility;

    @NonNull
    private String appName;

    @NonNull
    private String appVersion;

    @NonNull
    private String codeBase;

    @NonNull
    private String environment;

    @NonNull
    private String packages;

    @NonNull
    private String excludePackages;

    @NonNull
    private String tags;

    private int httpConnectTimeoutSeconds;
    private int httpReadTimeoutSeconds;
    private int httpWriteTimeoutSeconds;
    private String httpProxyHost;
    private int httpProxyPort;

    @NonNull
    @JsonIgnore
    private File aspectFile;

    @JsonIgnore
    private transient String resolvedAppVersion;

    @JsonIgnore
    private transient OkHttpClient httpClient;

    @JsonIgnore
    public List<String> getNormalizedPackages() {
        return ConfigUtils.getNormalizedPackages(packages);
    }

    @JsonIgnore
    public List<String> getNormalizedExcludePackages() {
        return ConfigUtils.getNormalizedPackages(excludePackages);
    }

    @JsonIgnore
    public List<File> getCodeBaseFiles() {
        return ConfigUtils.getCommaSeparatedFileValues(codeBase, false);
    }

    @JsonIgnore
    public MethodAnalyzer getMethodAnalyzer() {
        return new MethodAnalyzer(this.methodVisibility);
    }

    @JsonIgnore
    public String getPollConfigRequestEndpoint() {
        return String.format("%s%s", serverUrl, Endpoints.AGENT_V1_POLL_CONFIG);
    }

    @JsonIgnore
    public String getCodeBaseUploadEndpoint() {
        return String.format("%s%s", serverUrl, Endpoints.AGENT_V1_UPLOAD_CODEBASE);
    }

    @JsonIgnore
    public String getInvocationDataUploadEndpoint() {
        return String.format("%s%s", serverUrl, Endpoints.AGENT_V1_UPLOAD_INVOCATION_DATA);
    }

    @JsonIgnore
    public String getResolvedAppVersion() {
        if (resolvedAppVersion == null) {
            resolvedAppVersion = new AppVersionResolver(this).resolveAppVersion();
        }
        return resolvedAppVersion;
    }

    @JsonIgnore
    public OkHttpClient getHttpClient() {
        if (httpClient == null) {
            validate();
            httpClient = new OkHttpClient.Builder()
                .connectTimeout(httpConnectTimeoutSeconds, TimeUnit.SECONDS)
                .writeTimeout(httpWriteTimeoutSeconds, TimeUnit.SECONDS)
                .readTimeout(httpReadTimeoutSeconds, TimeUnit.SECONDS)
                .proxy(createHttpProxy())
                .build();
        }
        return httpClient;
    }

    private Proxy createHttpProxy() {
        if (httpProxyHost == null || httpProxyHost.trim().isEmpty()) {
            return null;
        }
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(httpProxyHost, httpProxyPort));
    }

    public String getFilenamePrefix(@NonNull String prefix) {
        String result = String.format("%s-%s-%s-", prefix.replaceAll("-+$", ""), appName, getResolvedAppVersion());
        return result.toLowerCase().replaceAll("[^a-z0-9._+-]", "");
    }

    public CommonPublicationData.CommonPublicationDataBuilder commonPublicationDataBuilder() {
        return CommonPublicationData
            .builder()
            .appName(getAppName())
            .appVersion(getResolvedAppVersion())
            .agentVersion(Constants.AGENT_VERSION)
            .computerId(Constants.COMPUTER_ID)
            .environment(getEnvironment())
            .excludePackages(getNormalizedExcludePackages().toString())
            .hostname(Constants.HOST_NAME)
            .jvmStartedAtMillis(Constants.JVM_STARTED_AT_MILLIS)
            .jvmUuid(Constants.JVM_UUID)
            .methodVisibility(getMethodVisibility())
            .packages(getNormalizedPackages().toString())
            .publishedAtMillis(System.currentTimeMillis())
            .tags(getTags());

    }

    AgentConfig validate() {
        if (httpProxyPort <= 0) {
            throw new IllegalArgumentException("Illegal httpProxyPort " + httpProxyPort + ": must be a positive integer");
        }
        return this;
    }
}