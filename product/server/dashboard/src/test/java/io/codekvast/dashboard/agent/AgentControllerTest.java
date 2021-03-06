package io.codekvast.dashboard.agent;

import static io.codekvast.javaagent.model.Endpoints.Agent.PARAM_FINGERPRINT;
import static io.codekvast.javaagent.model.Endpoints.Agent.PARAM_LICENSE_KEY;
import static io.codekvast.javaagent.model.Endpoints.Agent.PARAM_PUBLICATION_FILE;
import static io.codekvast.javaagent.model.Endpoints.Agent.PARAM_PUBLICATION_SIZE;
import static io.codekvast.javaagent.model.Endpoints.Agent.V1_POLL_CONFIG;
import static io.codekvast.javaagent.model.Endpoints.Agent.V2_POLL_CONFIG;
import static io.codekvast.javaagent.model.Endpoints.Agent.V2_UPLOAD_CODEBASE;
import static io.codekvast.javaagent.model.Endpoints.Agent.V2_UPLOAD_INVOCATION_DATA;
import static io.codekvast.javaagent.model.Endpoints.Agent.V3_UPLOAD_CODEBASE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import io.codekvast.common.customer.LicenseViolationException;
import io.codekvast.dashboard.model.PublicationType;
import io.codekvast.javaagent.model.v1.rest.GetConfigRequest1;
import io.codekvast.javaagent.model.v1.rest.GetConfigResponse1;
import io.codekvast.javaagent.model.v2.GetConfigRequest2;
import io.codekvast.javaagent.model.v2.GetConfigResponse2;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class AgentControllerTest {

  @Mock private AgentService agentService;

  private MockMvc mockMvc;

  private final Gson gson = new Gson();

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    AgentController agentController = new AgentController(agentService);
    this.mockMvc =
        MockMvcBuilders.standaloneSetup(agentController)
            .setMessageConverters(new GsonHttpMessageConverter(), new StringHttpMessageConverter())
            .addFilters(new LogAndSuppressRemoteClosedConnectionException())
            .build();
  }

  @Test
  public void pollConfig2_should_reject_invalid_method() throws Exception {
    mockMvc
        .perform(get(V2_POLL_CONFIG).contentType(APPLICATION_JSON))
        .andExpect(status().isMethodNotAllowed());
  }

  @Test
  public void pollConfig2_should_reject_invalid_media_type() throws Exception {
    mockMvc
        .perform(post(V2_POLL_CONFIG).contentType(TEXT_PLAIN))
        .andExpect(status().isUnsupportedMediaType());
  }

  @Test
  public void pollConfig2_should_reject_invalid_json() throws Exception {
    mockMvc
        .perform(post(V2_POLL_CONFIG).content("invalid json").contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void pollConfig2_should_reject_invalid_request() throws Exception {
    mockMvc
        .perform(
            post(V2_POLL_CONFIG)
                .content(gson.toJson(GetConfigRequest2.sample().toBuilder().appName("").build()))
                .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void pollConfig2_should_reject_invalid_licenseKey() throws Exception {
    when(agentService.getConfig(any(GetConfigRequest2.class)))
        .thenThrow(new LicenseViolationException("foobar"));

    mockMvc
        .perform(
            post(V2_POLL_CONFIG)
                .content(gson.toJson(GetConfigRequest2.sample()))
                .contentType(APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  public void pollConfig1_should_accept_valid_request_with_accept_application_json_1()
      throws Exception {
    when(agentService.getConfig(any(GetConfigRequest1.class)))
        .thenReturn(
            GetConfigResponse1.sample().toBuilder().codeBasePublisherName("foobar").build());

    //noinspection deprecation
    mockMvc
        .perform(
            post(V1_POLL_CONFIG)
                .content(gson.toJson(GetConfigRequest1.sample()))
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.codeBasePublisherName").value("foobar"));
  }

  @Test
  public void pollConfig2_should_accept_valid_request_with_accept_application_json_2()
      throws Exception {
    when(agentService.getConfig(any(GetConfigRequest2.class)))
        .thenReturn(
            GetConfigResponse2.sample().toBuilder().codeBasePublisherName("foobar").build());

    //noinspection deprecation
    mockMvc
        .perform(
            post(V2_POLL_CONFIG)
                .content(gson.toJson(GetConfigRequest2.sample()))
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.codeBasePublisherName").value("foobar"));
  }

  @Test
  public void pollConfig2_should_accept_valid_request_with_accept_application_json_utf8()
      throws Exception {
    when(agentService.getConfig(any(GetConfigRequest2.class)))
        .thenReturn(
            GetConfigResponse2.sample().toBuilder().codeBasePublisherName("foobar").build());

    //noinspection deprecation
    mockMvc
        .perform(
            post(V2_POLL_CONFIG)
                .content(gson.toJson(GetConfigRequest2.sample()))
                .contentType(APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.codeBasePublisherName").value("foobar"));
  }

  @Test
  public void should_accept_upload_codebase_publication2_when_valid_license() throws Exception {
    assertUploadPublication(PublicationType.CODEBASE, V2_UPLOAD_CODEBASE);
  }

  @Test
  public void should_accept_upload_codebase_publication3_when_valid_license() throws Exception {
    assertUploadPublication(PublicationType.CODEBASE, V3_UPLOAD_CODEBASE);
  }

  @Test
  public void should_accept_upload_invocation_data_publication2_when_valid_license()
      throws Exception {
    assertUploadPublication(PublicationType.INVOCATIONS, V2_UPLOAD_INVOCATION_DATA);
  }

  private void assertUploadPublication(PublicationType publicationType, String endpoint)
      throws Exception {
    String licenseKey = "licenseKey";
    String fingerprint = "fingerprint";
    int publicationSize = 10000;
    String originalFilename = String.format("codekvast-%s-9128371293719273.ser", publicationType);

    MockMultipartFile multipartFile =
        new MockMultipartFile(
            PARAM_PUBLICATION_FILE,
            originalFilename,
            APPLICATION_OCTET_STREAM_VALUE,
            ("PublicationContent-" + publicationType).getBytes());

    mockMvc
        .perform(
            multipart(endpoint)
                .file(multipartFile)
                .param(PARAM_LICENSE_KEY, licenseKey)
                .param(PARAM_FINGERPRINT, fingerprint)
                .param(PARAM_PUBLICATION_SIZE, publicationSize + ""))
        .andExpect(status().isOk())
        .andExpect(content().string("OK"));

    verify(agentService)
        .savePublication(
            eq(publicationType),
            eq(licenseKey),
            eq(fingerprint),
            eq(publicationSize),
            any(InputStream.class));
  }
}
