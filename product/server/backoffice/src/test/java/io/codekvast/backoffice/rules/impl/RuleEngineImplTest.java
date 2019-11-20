package io.codekvast.backoffice.rules.impl;

import io.codekvast.backoffice.facts.CollectionStarted;
import io.codekvast.backoffice.rules.RuleEngine;
import io.codekvast.backoffice.service.MailSender;
import io.codekvast.common.customer.CustomerData;
import io.codekvast.common.customer.CustomerService;
import io.codekvast.common.messaging.model.AgentPolledEvent;
import io.codekvast.common.messaging.model.CodekvastEvent;
import lombok.Value;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static io.codekvast.backoffice.service.MailSender.Template.WELCOME_COLLECTION_HAS_STARTED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author olle.hallin@crisp.se
 */
public class RuleEngineImplTest {

    private static final Instant NOW = Instant.now();

    @Mock
    private FactDAO factDAO;

    @Mock
    private MailSender mailSender;

    @Mock
    private CustomerService customerService;

    private Clock clock = Clock.fixed(NOW, ZoneId.of("Z"));

    private RuleEngine ruleEngine;

    @Before
    public void beforeTest() throws IOException {
        MockitoAnnotations.initMocks(this);

        ruleEngine = new RuleEngineImpl(factDAO, mailSender, customerService, clock).configureDrools();
    }

    @Test
    public void should_send_welcome_email_when_agent_polls_and_contact_email_is_defined() {
        // given
        val event = AgentPolledEvent.sample();
        Long customerId = event.getCustomerId();
        Long factId = 4711L;
        when(factDAO.getFacts(customerId)).thenReturn(Collections.emptyList());
        when(factDAO.addFact(eq(customerId), any(CollectionStarted.class))).thenReturn(factId);
        when(customerService.getCustomerDataByCustomerId(customerId))
            .thenReturn(CustomerData.sample()
                                    .toBuilder().contactEmail("some-email-address")
                                    .build());
        // when
        ruleEngine.handle(event);

        // then
        verify(mailSender).sendMail(WELCOME_COLLECTION_HAS_STARTED, "some-email-address", customerId);
        verify(factDAO).updateFact(eq(factId), eq(customerId),
                                   eq(new CollectionStarted(event.getPolledAt(), event.getTrialPeriodEndsAt(), "some-email-address", NOW)));
    }

    @Test
    public void should_not_send_welcome_email_when_agent_polls_and_contact_email_is_undefined() {
        // given
        val event = AgentPolledEvent.sample();
        Long customerId = event.getCustomerId();
        Long factId = 4711L;
        when(factDAO.getFacts(customerId)).thenReturn(Collections.emptyList());
        when(factDAO.addFact(eq(customerId), any(CollectionStarted.class))).thenReturn(factId);
        when(customerService.getCustomerDataByCustomerId(customerId))
            .thenReturn(CustomerData.sample()
                                    .toBuilder().contactEmail("  ")
                                    .build());
        // when
        ruleEngine.handle(event);

        // then
        verifyNoInteractions(mailSender);
    }

    @Test
    public void should_not_send_welcome_email_when_agent_polls_and_contact_email_starts_with_bang() {
        // given
        val event = AgentPolledEvent.sample();
        Long customerId = event.getCustomerId();
        Long factId = 4711L;
        when(factDAO.getFacts(customerId)).thenReturn(Collections.emptyList());
        when(factDAO.addFact(eq(customerId), any(CollectionStarted.class))).thenReturn(factId);
        when(customerService.getCustomerDataByCustomerId(customerId))
            .thenReturn(CustomerData.sample()
                                    .toBuilder().contactEmail("  ! some-email-address")
                                    .build());
        // when
        ruleEngine.handle(event);

        // then
        verifyNoInteractions(mailSender);
    }

    @Test
    public void should_send_welcome_email_on_any_event_after_collection_has_started_when_contact_email_becomes_defined() {
        // given
        long customerId = 1L;
        Long factId = 4711L;
        CollectionStarted fact = new CollectionStarted(NOW.minus(3, ChronoUnit.DAYS), null, null, null);
        when(factDAO.getFacts(customerId)).thenReturn(List.of(new FactWrapper(factId, fact)));

        when(customerService.getCustomerDataByCustomerId(customerId))
            .thenReturn(CustomerData.sample().toBuilder().contactEmail(null).build());

        // when
        ruleEngine.handle(new AnyEvent(customerId));

        // then
        verifyNoInteractions(mailSender);

        // given
        when(customerService.getCustomerDataByCustomerId(customerId))
            .thenReturn(CustomerData.sample().toBuilder().contactEmail("contactEmail").build());

        // when
        ruleEngine.handle(new AnyEvent(customerId));

        // then
        verify(mailSender).sendMail(WELCOME_COLLECTION_HAS_STARTED, "contactEmail", customerId);
        verify(factDAO).updateFact(eq(factId), eq(customerId),
                                   eq(new CollectionStarted(fact.getCollectionStartedAt(), fact.getTrialPeriodEndsAt(), "contactEmail",
                                                            NOW)));
    }

    @Value
    private static class AnyEvent implements CodekvastEvent {
        private final Long customerId;
    }
}