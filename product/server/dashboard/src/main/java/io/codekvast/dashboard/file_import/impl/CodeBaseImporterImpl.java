/*
 * Copyright (c) 2015-2020 Hallin Information Technology AB
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
package io.codekvast.dashboard.file_import.impl;

import static io.codekvast.common.util.LoggingUtils.humanReadableDuration;

import io.codekvast.common.aspects.Restartable;
import io.codekvast.common.lock.Lock;
import io.codekvast.common.lock.LockTemplate;
import io.codekvast.common.messaging.EventService;
import io.codekvast.common.messaging.model.CodeBaseReceivedEvent;
import io.codekvast.dashboard.file_import.CodeBaseImporter;
import io.codekvast.dashboard.metrics.AgentMetricsService;
import io.codekvast.dashboard.model.PublicationType;
import io.codekvast.javaagent.model.v2.CommonPublicationData2;
import io.codekvast.javaagent.model.v3.CodeBaseEntry3;
import io.codekvast.javaagent.model.v3.CodeBasePublication3;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** @author olle.hallin@crisp.se */
@Component
@RequiredArgsConstructor
@Slf4j
public class CodeBaseImporterImpl implements CodeBaseImporter {

  private final CommonImporter commonImporter;
  private final ImportDAO importDAO;
  private final SyntheticSignatureService syntheticSignatureService;
  private final AgentMetricsService metricsService;
  private final EventService eventService;
  private final LockTemplate lockTemplate;
  private final Clock clock;

  @Override
  @Transactional(rollbackFor = Exception.class)
  @Restartable
  public boolean importPublication(CodeBasePublication3 publication) throws Exception {
    logger.debug("Importing {}", publication);
    CommonPublicationData2 data = publication.getCommonData();

    Collection<CodeBaseEntry3> entries =
        publication.getEntries().stream()
            .filter(e -> !syntheticSignatureService.isSyntheticMethod(e.getSignature()))
            .collect(Collectors.toList());
    int ignoredSyntheticSignatures = publication.getEntries().size() - entries.size();

    Duration duration =
        lockTemplate.doWithLockOrThrow(
            Lock.forCustomer(data.getCustomerId()), () -> doImportCodeBase(data, entries));

    logger.info(
        "Imported {} in {} (ignoring {} synthetic signatures)",
        publication,
        humanReadableDuration(duration),
        ignoredSyntheticSignatures);

    metricsService.recordImportedPublication(
        PublicationType.CODEBASE, entries.size(), ignoredSyntheticSignatures, duration);
    return true;
  }

  private Duration doImportCodeBase(
      CommonPublicationData2 data, Collection<CodeBaseEntry3> entries) {
    Instant startedAt = clock.instant();

    CommonImporter.ImportContext importContext = commonImporter.importCommonData(data);

    boolean isNewCodebase = importDAO.importCodeBaseFingerprint(data, importContext);

    if (isNewCodebase) {
      importDAO.importMethods(data, importContext, entries);
    }

    eventService.send(
        CodeBaseReceivedEvent.builder()
            .customerId(data.getCustomerId())
            .appName(data.getAppName())
            .appVersion(data.getAppVersion())
            .agentVersion(data.getAgentVersion())
            .environment(data.getEnvironment())
            .hostname(data.getHostname())
            .size(entries.size())
            .receivedAt(Instant.ofEpochMilli(data.getPublishedAtMillis()))
            .trialPeriodEndsAt(importContext.getTrialPeriodEndsAt())
            .build());
    return Duration.between(startedAt, clock.instant());
  }
}
