package io.codekvast.agent.collector.io.impl;

import io.codekvast.agent.lib.codebase.CodeBaseFingerprint;
import io.codekvast.agent.lib.config.CollectorConfig;
import io.codekvast.agent.lib.config.CollectorConfigFactory;
import okhttp3.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author olle.hallin@crisp.se
 */
public class HttpInvocationDataPublisherImplTest {

    private final CollectorConfig config = CollectorConfigFactory
        .createSampleCollectorConfig().toBuilder().appName("appName").appVersion("appVersion").build();

    private File uploadedFile;

    private final HttpInvocationDataPublisherImpl publisher = new TestableHttpInvocationDataPublisherImpl();

    @Test
    public void should_create_and_upload_file() throws Exception {
        Set<String> invocations = new HashSet<>(Arrays.asList("a", "b", "c"));
        publisher.setCodeBaseFingerprint(new CodeBaseFingerprint(1, "sha256"));
        publisher.doPublishInvocationData(System.currentTimeMillis(), invocations);

        assertThat(uploadedFile, notNullValue());
        assertThat(uploadedFile.getName(), startsWith("invocations-appname-appversion-"));
        assertThat(uploadedFile.getName(), endsWith(".ser"));
        assertThat(uploadedFile.exists(), is(false));
    }

    private class TestableHttpInvocationDataPublisherImpl extends HttpInvocationDataPublisherImpl {

        public TestableHttpInvocationDataPublisherImpl() {
            super(HttpInvocationDataPublisherImplTest.this.config);
        }

        @Override
        void doPost(File file, String url, String fingerprint) throws IOException {
            super.doPost(file, url, fingerprint);
            uploadedFile = file;
        }

        @Override
        Response executeRequest(Request request) throws IOException {
            return new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .body(ResponseBody.create(MediaType.parse("text/plain"), "OK"))
                .build();
        }
    }
}