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

import static io.codekvast.dashboard.file_import.impl.CommonImporter.ImportContext;
import static io.codekvast.javaagent.model.v2.SignatureStatus2.INVOKED;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.sql.Types.BOOLEAN;
import static java.util.Optional.ofNullable;

import io.codekvast.common.customer.CustomerService;
import io.codekvast.database.DatabaseLimits;
import io.codekvast.javaagent.model.v2.CommonPublicationData2;
import io.codekvast.javaagent.model.v2.SignatureStatus2;
import io.codekvast.javaagent.model.v3.CodeBaseEntry3;
import io.codekvast.javaagent.model.v3.MethodSignature3;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/** @author olle.hallin@crisp.se */
@Component
@RequiredArgsConstructor
@Slf4j
public class ImportDAOImpl implements ImportDAO {

  private static final String VISIBILITY_PRIVATE = "private";
  private static final String VISIBILITY_PACKAGE_PRIVATE = "package-private";
  private static final String VISIBILITY_PROTECTED = "protected";
  private static final String VISIBILITY_PUBLIC = "public";
  private static final String DEFAULT_ENVIRONMENT_NAME = "<default>";
  private final JdbcTemplate jdbcTemplate;
  private final CustomerService customerService;

  @Override
  public long importApplication(CommonPublicationData2 data) {
    long customerId = data.getCustomerId();
    String name = data.getAppName();
    Timestamp createdAt = new Timestamp(data.getJvmStartedAtMillis());

    int updated =
        jdbcTemplate.update(
            "UPDATE applications SET createdAt = LEAST(createdAt, ?) "
                + "WHERE customerId = ? AND name = ? "
                + "ORDER BY id ",
            createdAt,
            customerId,
            name);
    if (updated != 0) {
      logger.trace("Updated application {}", name);
    } else {
      jdbcTemplate.update(
          "INSERT INTO applications(customerId, name, createdAt) VALUES (?, ?, ?)",
          customerId,
          name,
          createdAt);
      logger.info(
          "Imported new application: customerId={}, name='{}', createdAt={}",
          customerId,
          name,
          createdAt);
    }

    Long result =
        jdbcTemplate.queryForObject(
            "SELECT id FROM applications WHERE customerId = ? AND name = ?",
            Long.class,
            customerId,
            name);
    logger.debug("Application {}:'{}' has id {}", customerId, name, result);
    return result;
  }

  @Override
  public long importEnvironment(CommonPublicationData2 data) {
    long customerId = data.getCustomerId();
    String name = data.getEnvironment();
    if (name.trim().isEmpty()) {
      name = DEFAULT_ENVIRONMENT_NAME;
    }
    Timestamp createdAt = new Timestamp(data.getJvmStartedAtMillis());

    int updated =
        jdbcTemplate.update(
            "UPDATE environments SET createdAt = LEAST(createdAt, ?) "
                + "WHERE customerId = ? AND name = ? "
                + "ORDER BY id ",
            createdAt,
            customerId,
            name);
    if (updated != 0) {
      logger.trace("Updated environment {}:'{}'", customerId, name);
    } else {
      jdbcTemplate.update(
          "INSERT INTO environments(customerId, name, createdAt, enabled) VALUES (?, ?, ?, ?)",
          customerId,
          name,
          createdAt,
          TRUE);
      logger.info(
          "Imported new environment: customerId={}, name='{}', createdAt={}",
          customerId,
          name,
          createdAt);
    }

    Long result =
        jdbcTemplate.queryForObject(
            "SELECT id FROM environments WHERE customerId = ? AND name = ?",
            Long.class,
            customerId,
            name);
    logger.debug("Environment {}:'{}' has id {}", customerId, name, result);
    return result;
  }

  @Override
  public long importJvm(CommonPublicationData2 data, long applicationId, long environmentId) {

    long customerId = data.getCustomerId();
    Timestamp publishedAt = new Timestamp(data.getPublishedAtMillis());

    int updated =
        jdbcTemplate.update(
            "UPDATE jvms SET codeBaseFingerprint = ?, publishedAt = ?, garbage = ? WHERE uuid = ? "
                + "ORDER BY id ",
            data.getCodeBaseFingerprint(),
            publishedAt,
            FALSE,
            data.getJvmUuid());
    if (updated != 0) {
      logger.trace("Updated JVM {}", data.getJvmUuid());
    } else {
      jdbcTemplate.update(
          "INSERT INTO jvms(customerId, applicationId, applicationVersion, environmentId, uuid, codeBaseFingerprint, startedAt, "
              + "publishedAt, methodVisibility, packages,"
              + " excludePackages, computerId, hostname, agentVersion, tags, garbage) "
              + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
          customerId,
          applicationId,
          data.getAppVersion(),
          environmentId,
          data.getJvmUuid(),
          data.getCodeBaseFingerprint(),
          new Timestamp(data.getJvmStartedAtMillis()),
          publishedAt,
          data.getMethodVisibility(),
          data.getPackages().toString(),
          data.getExcludePackages().toString(),
          data.getComputerId(),
          data.getHostname(),
          data.getAgentVersion(),
          data.getTags(),
          FALSE);
      logger.info(
          "Imported new JVM: customerId={}, applicationId={}, environmentId={}, jvmUUid='{}', startedAt={}",
          customerId,
          applicationId,
          environmentId,
          data.getJvmUuid(),
          Instant.ofEpochMilli(data.getJvmStartedAtMillis()));
    }

    Long result =
        jdbcTemplate.queryForObject(
            "SELECT id FROM jvms WHERE uuid = ?", Long.class, data.getJvmUuid());

    logger.debug("JVM with uuid '{}' has id {}", data.getJvmUuid(), result);
    return result;
  }

  @Override
  public boolean importCodeBaseFingerprint(
      CommonPublicationData2 data, ImportContext importContext) {
    long customerId = importContext.getCustomerId();
    long applicationId = importContext.getAppId();
    String codeBaseFingerprint = data.getCodeBaseFingerprint();
    Timestamp publishedAt = new Timestamp(data.getPublishedAtMillis());

    int updated =
        jdbcTemplate.update(
            "UPDATE codebase_fingerprints SET publishedAt = ? "
                + "WHERE customerId = ? "
                + "AND applicationId = ? "
                + "AND codeBaseFingerprint = ? "
                + "ORDER BY id ",
            publishedAt,
            customerId,
            applicationId,
            codeBaseFingerprint);

    if (updated > 0) {
      logger.debug(
          "Already imported codebase: {}:{}:{}",
          customerId,
          importContext.getAppId(),
          codeBaseFingerprint);
      return false;
    }

    int inserted =
        jdbcTemplate.update(
            "INSERT INTO codebase_fingerprints(customerId, applicationId, codeBaseFingerprint, publishedAt) "
                + "VALUES(?, ?, ?, ?)",
            customerId,
            applicationId,
            codeBaseFingerprint,
            publishedAt);

    if (inserted != 1) {
      logger.error(
          "Failed to imported codebase: {}:{}:{}",
          customerId,
          importContext.getAppId(),
          codeBaseFingerprint);
      return false;
    }

    logger.info("Imported codebase {}:{}:{}", customerId, applicationId, codeBaseFingerprint);
    return true;
  }

  @Override
  public void importMethods(
      CommonPublicationData2 data,
      ImportContext importContext,
      Collection<CodeBaseEntry3> entries) {
    long customerId = importContext.getCustomerId();
    long appId = importContext.getAppId();
    long publishedAtMillis = importContext.getPublishedAtMillis();
    long environmentId = importContext.getEnvironmentId();

    Set<String> existingPackages = getExistingPackages(customerId);
    Set<String> existingTypes = getExistingTypes(customerId);
    Map<String, Long> existingMethods = getExistingMethods(customerId);
    Set<String> incompleteMethods = getIncompleteMethods(customerId);
    Set<Long> invocationsNotFoundInCodeBase = getInvocationsNotFoundInCodeBase(customerId);
    Set<Long> existingMethodLocations = getExistingMethodLocations(customerId);

    importNewPackages(customerId, publishedAtMillis, entries, existingPackages);
    importNewTypes(customerId, publishedAtMillis, entries, existingTypes);
    importNewMethods(customerId, publishedAtMillis, entries, existingMethods);
    insertMethodLocations(customerId, entries, existingMethods, existingMethodLocations);
    updateIncompleteMethods(
        customerId,
        publishedAtMillis,
        entries,
        incompleteMethods,
        existingMethods,
        invocationsNotFoundInCodeBase);
    Instant now = Instant.now();
    ensureInitialInvocations(data, customerId, appId, environmentId, entries, existingMethods, now);
    removeStaleInvocations(customerId, appId, environmentId, now, existingMethods);

    customerService.assertDatabaseSize(customerId);
  }

  private void importNewPackages(
      long customerId,
      long publishedAtMillis,
      Collection<CodeBaseEntry3> entries,
      Set<String> existingPackages) {
    Instant startedAt = Instant.now();
    Timestamp createdAt = new Timestamp(publishedAtMillis);
    int count = 0;
    for (CodeBaseEntry3 entry : entries) {
      MethodSignature3 methodSignature = entry.getMethodSignature();
      if (methodSignature == null) {
        logger.warn("Cannot import package name from {}, no methodSignature", entry);
      } else {
        String packageName = methodSignature.getPackageName();
        if (!existingPackages.contains(packageName)) {
          int updated =
              jdbcTemplate.update(
                  "INSERT INTO packages(customerId, name, createdAt) VALUES (?, ?, ?)",
                  customerId,
                  packageName,
                  createdAt);
          if (updated == 1) {
            existingPackages.add(packageName);
          } else {
            logger.warn("Failed to insert {}:{} into packages", customerId, packageName);
          }
          count += 1;
        }
      }
    }
    logger.debug(
        "Imported {} packages in {} ms", count, Duration.between(startedAt, Instant.now()));
  }

  private void importNewTypes(
      long customerId,
      long publishedAtMillis,
      Collection<CodeBaseEntry3> entries,
      Set<String> existingTypes) {
    Instant startedAt = Instant.now();
    Timestamp createdAt = new Timestamp(publishedAtMillis);
    int count = 0;
    for (CodeBaseEntry3 entry : entries) {
      MethodSignature3 methodSignature = entry.getMethodSignature();
      if (methodSignature == null) {
        logger.warn("Cannot import declaring type from {}, no methodSignature", entry);
      } else {
        String declaringType = methodSignature.getDeclaringType();
        if (!existingTypes.contains(declaringType)) {
          int updated =
              jdbcTemplate.update(
                  "INSERT INTO types(customerId, name, createdAt) VALUES (?, ?, ?)",
                  customerId,
                  declaringType,
                  createdAt);
          if (updated == 1) {
            existingTypes.add(declaringType);
          } else {
            logger.warn("Failed to insert {}:{} into types", customerId, declaringType);
          }
          count += 1;
        }
      }
    }
    logger.debug(
        "Imported {} packages in {} ms", count, Duration.between(startedAt, Instant.now()));
  }

  @Override
  public void importInvocations(
      ImportContext importContext, long recordingIntervalStartedAtMillis, Set<String> invocations) {
    long customerId = importContext.getCustomerId();
    long appId = importContext.getAppId();
    long environmentId = importContext.getEnvironmentId();

    Map<String, Long> existingMethods = getExistingMethods(customerId);
    doImportInvocations(
        customerId,
        appId,
        environmentId,
        recordingIntervalStartedAtMillis,
        invocations,
        existingMethods);

    customerService.assertDatabaseSize(customerId);
  }

  private void doImportInvocations(
      long customerId,
      long appId,
      long environmentId,
      long invokedAtMillis,
      Set<String> invokedSignatures,
      Map<String, Long> existingMethods) {
    for (String sig : invokedSignatures) {
      String signature = DatabaseLimits.normalizeSignature(sig);
      Long methodId = existingMethods.get(signature);
      if (methodId == null) {
        logger.trace("Inserting incomplete method {}:{}", methodId, signature);
        methodId =
            doInsertRow(
                new InsertIncompleteMethodStatement(customerId, signature, invokedAtMillis));
        existingMethods.put(signature, methodId);
      }
      logger.trace("Upserting invocation {}", signature);
      jdbcTemplate.update(
          new UpsertInvocationStatement(
              customerId, appId, environmentId, methodId, INVOKED, invokedAtMillis, null));
    }
  }

  private Set<String> getExistingPackages(long customerId) {
    return new HashSet<>(
        jdbcTemplate.queryForList(
            "SELECT name FROM packages WHERE customerId = ?", String.class, customerId));
  }

  private Set<String> getExistingTypes(long customerId) {
    return new HashSet<>(
        jdbcTemplate.queryForList(
            "SELECT name FROM types WHERE customerId = ?", String.class, customerId));
  }

  private Map<String, Long> getExistingMethods(long customerId) {
    Map<String, Long> result = new HashMap<>();
    jdbcTemplate.query(
        "SELECT id, signature FROM methods WHERE customerId = ? ",
        rs -> {
          result.put(rs.getString(2), rs.getLong(1));
        },
        customerId);
    return result;
  }

  private Set<String> getIncompleteMethods(long customerId) {
    return new HashSet<>(
        jdbcTemplate.queryForList(
            "SELECT signature FROM methods WHERE customerId = ? AND methodName IS NULL ",
            String.class,
            customerId));
  }

  private Set<Long> getExistingMethodLocations(long customerId) {
    return new HashSet<>(
        jdbcTemplate.queryForList(
            "SELECT methodId FROM method_locations WHERE customerId = ? ", Long.class, customerId));
  }

  private Set<Long> getInvocationsNotFoundInCodeBase(long customerId) {
    return new HashSet<>(
        jdbcTemplate.queryForList(
            "SELECT methodId FROM invocations WHERE customerId = ? AND status = ?",
            Long.class,
            customerId,
            SignatureStatus2.NOT_FOUND_IN_CODE_BASE.name()));
  }

  private void importNewMethods(
      long customerId,
      long publishedAtMillis,
      Collection<CodeBaseEntry3> entries,
      Map<String, Long> existingMethods) {
    Instant startedAt = Instant.now();
    int count = 0;
    for (CodeBaseEntry3 entry : entries) {
      String signature = truncateTooLongSignature(customerId, entry.getSignature());

      if (!existingMethods.containsKey(signature)) {
        existingMethods.put(
            signature,
            doInsertRow(
                new InsertCompleteMethodStatement(
                    customerId,
                    publishedAtMillis,
                    entry.getMethodSignature(),
                    entry.getVisibility(),
                    signature)));
        count += 1;
      }
    }
    logger.debug("Imported {} methods in {} ms", count, Duration.between(startedAt, Instant.now()));
  }

  private String truncateTooLongSignature(long customerId, String signature) {
    if (signature.length() > DatabaseLimits.MAX_METHOD_SIGNATURE_LENGTH) {
      int inserted =
          jdbcTemplate.update(
              "INSERT IGNORE INTO truncated_signatures(customerId, signature, length, truncatedLength) VALUES(?, ?, ?, ?)",
              customerId,
              signature,
              signature.length(),
              DatabaseLimits.MAX_METHOD_SIGNATURE_LENGTH);
      if (inserted > 0) {
        logger.warn(
            "Too long signature {}:'{}' ({} characters), longer than {} characters, will be truncated.",
            customerId,
            signature,
            signature.length(),
            DatabaseLimits.MAX_METHOD_SIGNATURE_LENGTH);
      }
      return DatabaseLimits.normalizeSignature(signature);
    }
    return signature;
  }

  private void insertMethodLocations(
      long customerId,
      Collection<CodeBaseEntry3> entries,
      Map<String, Long> existingMethods,
      Set<Long> existingMethodLocations) {
    Instant startedAt = Instant.now();
    int count = 0;
    for (CodeBaseEntry3 entry : entries) {
      String location = entry.getMethodSignature().getLocation();
      String signature = DatabaseLimits.normalizeSignature(entry.getSignature());
      long methodId = existingMethods.get(signature);
      if (location != null && !existingMethodLocations.contains(methodId)) {
        logger.debug("Inserting {} ({})", signature, location);
        count +=
            jdbcTemplate.update(new InsertMethodLocationStatement(customerId, methodId, location));
        existingMethodLocations.add(methodId);
      }
    }
    logger.debug(
        "Inserted {} method locations in {} ms", count, Duration.between(startedAt, Instant.now()));
  }

  private void updateIncompleteMethods(
      long customerId,
      long publishedAtMillis,
      Collection<CodeBaseEntry3> entries,
      Set<String> incompleteMethods,
      Map<String, Long> existingMethods,
      Set<Long> incompleteInvocations) {
    Instant startedAt = Instant.now();
    int count = 0;
    for (CodeBaseEntry3 entry : entries) {
      String signature = DatabaseLimits.normalizeSignature(entry.getSignature());
      long methodId = existingMethods.get(signature);
      if (incompleteMethods.contains(signature) || incompleteInvocations.contains(methodId)) {
        logger.debug("Updating {}", signature);
        jdbcTemplate.update(
            new UpdateIncompleteMethodStatement(customerId, publishedAtMillis, entry));
        count += 1;
      }
    }
    logger.debug(
        "Updated {} incomplete methods in {} ms",
        count,
        Duration.between(startedAt, Instant.now()));
  }

  private void ensureInitialInvocations(
      CommonPublicationData2 data,
      long customerId,
      long appId,
      long environmentId,
      Collection<CodeBaseEntry3> entries,
      Map<String, Long> existingMethods,
      Instant now) {
    Instant startedAt = Instant.now();
    int importCount = 0;

    for (CodeBaseEntry3 entry : entries) {
      String signature = DatabaseLimits.normalizeSignature(entry.getSignature());
      long methodId = existingMethods.get(signature);
      SignatureStatus2 initialStatus = calculateInitialStatus(data, entry);
      int updated =
          jdbcTemplate.update(
              new UpsertInvocationStatement(
                  customerId, appId, environmentId, methodId, initialStatus, 0L, now));
      importCount += updated == 1 ? 1 : 0;
    }
    logger.debug(
        "Imported {} initial invocations in {} ms",
        importCount,
        Duration.between(startedAt, Instant.now()));
  }

  private SignatureStatus2 calculateInitialStatus(
      CommonPublicationData2 data, CodeBaseEntry3 entry) {
    for (String pkg : data.getExcludePackages()) {
      if (entry.getMethodSignature().getPackageName().startsWith(pkg)) {
        return SignatureStatus2.EXCLUDED_BY_PACKAGE_NAME;
      }
    }

    return ofNullable(getExcludeByVisibility(data.getMethodVisibility(), entry))
        .orElse(getExcludeByTriviality(entry));
  }

  private SignatureStatus2 getExcludeByTriviality(CodeBaseEntry3 entry) {
    String name = entry.getMethodSignature().getMethodName();
    String parameterTypes = entry.getMethodSignature().getParameterTypes().trim();

    boolean noParameters = parameterTypes.isEmpty();
    boolean singleParameter = !parameterTypes.isEmpty() && !parameterTypes.contains(",");

    if (name.equals("hashCode") && noParameters) {
      return SignatureStatus2.EXCLUDED_SINCE_TRIVIAL;
    }
    if (name.equals("equals") && singleParameter) {
      return SignatureStatus2.EXCLUDED_SINCE_TRIVIAL;
    }
    if (name.equals("canEqual") && singleParameter) {
      return SignatureStatus2.EXCLUDED_SINCE_TRIVIAL;
    }
    if (name.equals("compareTo") && singleParameter) {
      return SignatureStatus2.EXCLUDED_SINCE_TRIVIAL;
    }
    if (name.equals("toString") && noParameters) {
      return SignatureStatus2.EXCLUDED_SINCE_TRIVIAL;
    }

    return SignatureStatus2.NOT_INVOKED;
  }

  SignatureStatus2 getExcludeByVisibility(String methodVisibility, CodeBaseEntry3 entry) {
    String v = entry.getVisibility();
    switch (methodVisibility) {
      case VISIBILITY_PRIVATE:
        return null;
      case VISIBILITY_PACKAGE_PRIVATE:
        return v.equals(VISIBILITY_PUBLIC)
                || v.equals(VISIBILITY_PROTECTED)
                || v.equals(VISIBILITY_PACKAGE_PRIVATE)
            ? null
            : SignatureStatus2.EXCLUDED_BY_VISIBILITY;
      case VISIBILITY_PROTECTED:
        return v.equals(VISIBILITY_PUBLIC) || v.equals(VISIBILITY_PROTECTED)
            ? null
            : SignatureStatus2.EXCLUDED_BY_VISIBILITY;
      case VISIBILITY_PUBLIC:
        return v.equals(VISIBILITY_PUBLIC) ? null : SignatureStatus2.EXCLUDED_BY_VISIBILITY;
    }
    return null;
  }

  private void removeStaleInvocations(
      long customerId,
      long appId,
      long environmentId,
      Instant now,
      Map<String, Long> existingMethods) {

    // TODO: Convert to a simple DELETE once the erroneous stale deletions have been fixed

    Map<Long, String> methodsById =
        existingMethods.entrySet().stream()
            .collect(Collectors.toMap(Entry::getValue, Entry::getKey, (a, b) -> b));

    AtomicInteger deleted = new AtomicInteger(0);

    jdbcTemplate.query(
        "DELETE FROM invocations WHERE customerId = ? AND applicationId = ? AND environmentId = ? "
            + "AND lastSeenAtMillis < ? AND status <> ? "
            + "RETURNING methodId, status, invokedAtMillis, createdAt, lastSeenAtMillis, timestamp ",
        rs -> {
          long methodId = rs.getLong("methodId");
          logger.info(
              "Removed stale invocation {}:{}:{}:{} ('{}'), {}, createdAt={}, lastSeenAt={}, timestamp={}, now={}",
              customerId,
              appId,
              environmentId,
              methodId,
              methodsById.get(methodId),
              formatInvokedAt(rs.getString("status"), rs.getLong("invokedAtMillis")),
              rs.getTimestamp("createdAt").toInstant(),
              Instant.ofEpochMilli(rs.getLong("lastSeenAtMillis")),
              rs.getTimestamp("timestamp").toInstant(),
              now);
          deleted.incrementAndGet();
        },
        customerId,
        appId,
        environmentId,
        now.toEpochMilli(),
        INVOKED.name());
    if (deleted.get() > 0) {
      logger.info(
          "Removed {} stale invocations for {}:{}:{}", deleted, customerId, appId, environmentId);
    } else {
      logger.debug("Removed no stale invocations for {}:{}:{}", customerId, appId, environmentId);
    }
  }

  private String formatInvokedAt(String status, long invokedAtMillis) {
    return invokedAtMillis == 0L ? status : status + " at " + Instant.ofEpochMilli(invokedAtMillis);
  }

  private Long doInsertRow(PreparedStatementCreator psc) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(psc, keyHolder);
    return keyHolder.getKey().longValue();
  }

  @RequiredArgsConstructor
  private static class InsertCompleteMethodStatement implements PreparedStatementCreator {

    private final long customerId;
    private final long publishedAtMillis;
    private final MethodSignature3 method;
    private final String visibility;
    private final String signature;

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

      PreparedStatement ps =
          con.prepareStatement(
              "INSERT INTO methods(customerId, visibility, signature, createdAt, declaringType, "
                  + "exceptionTypes, methodName, bridge, synthetic, modifiers, packageName, parameterTypes, "
                  + "returnType) "
                  + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
              Statement.RETURN_GENERATED_KEYS);
      int column = 0;
      ps.setLong(++column, customerId);
      ps.setString(++column, visibility);
      ps.setString(++column, signature);
      ps.setTimestamp(++column, new Timestamp(publishedAtMillis));
      ps.setString(++column, method.getDeclaringType());
      ps.setString(++column, method.getExceptionTypes());
      ps.setString(++column, method.getMethodName());
      ps.setObject(++column, method.getBridge(), BOOLEAN);
      ps.setObject(++column, method.getSynthetic(), BOOLEAN);
      ps.setString(++column, method.getModifiers());
      ps.setString(++column, method.getPackageName());
      ps.setString(++column, method.getParameterTypes());
      ps.setString(++column, method.getReturnType());
      return ps;
    }
  }

  @RequiredArgsConstructor
  private static class UpdateIncompleteMethodStatement implements PreparedStatementCreator {

    private final long customerId;
    private final long publishedAtMillis;
    private final CodeBaseEntry3 entry;

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

      PreparedStatement ps =
          con.prepareStatement(
              "UPDATE methods\n"
                  + "SET visibility   = ?, createdAt = LEAST(createdAt, ?), declaringType = ?, exceptionTypes = ?, methodName = ?,\n"
                  + "  bridge = ?, synthetic = ?, modifiers = ?, packageName    = ?, parameterTypes = ?, returnType = ?\n"
                  + "WHERE customerId = ? AND signature = ? "
                  + "ORDER BY id ");
      int column = 0;
      MethodSignature3 method = entry.getMethodSignature();
      ps.setString(++column, entry.getVisibility());
      ps.setTimestamp(++column, new Timestamp(publishedAtMillis));
      ps.setString(++column, method.getDeclaringType());
      ps.setString(++column, method.getExceptionTypes());
      ps.setString(++column, method.getMethodName());
      ps.setObject(++column, method.getBridge(), BOOLEAN);
      ps.setObject(++column, method.getSynthetic(), BOOLEAN);
      ps.setString(++column, method.getModifiers());
      ps.setString(++column, method.getPackageName());
      ps.setString(++column, method.getParameterTypes());
      ps.setString(++column, method.getReturnType());
      ps.setLong(++column, customerId);
      ps.setString(++column, DatabaseLimits.normalizeSignature(entry.getSignature()));
      return ps;
    }
  }

  @RequiredArgsConstructor
  private static class UpsertInvocationStatement implements PreparedStatementCreator {

    private final long customerId;
    private final long appId;
    private final long environmentId;
    private final long methodId;
    private final SignatureStatus2 status;
    private final long invokedAtMillis;
    private final Instant lastSeenAt;

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

      String sql_codebase =
          "INSERT INTO invocations(customerId, applicationId, environmentId, methodId, status, invokedAtMillis, lastSeenAtMillis) "
              + "VALUES(?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE lastSeenAtMillis = ? ";

      String sql_invocation =
          "INSERT INTO invocations(customerId, applicationId, environmentId, methodId, status, invokedAtMillis) "
              + "VALUES(?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE "
              + "invokedAtMillis = GREATEST(invokedAtMillis, VALUE(invokedAtMillis)), status = VALUE(status)";

      PreparedStatement ps =
          con.prepareStatement(invokedAtMillis == 0L ? sql_codebase : sql_invocation);
      int column = 0;
      ps.setLong(++column, customerId);
      ps.setLong(++column, appId);
      ps.setLong(++column, environmentId);
      ps.setLong(++column, methodId);
      ps.setString(++column, status.name());
      ps.setLong(++column, invokedAtMillis);
      if (invokedAtMillis == 0L) { // codebase
        ps.setLong(++column, lastSeenAt.toEpochMilli()); // insert
        ps.setLong(++column, lastSeenAt.toEpochMilli()); // update
      }
      return ps;
    }
  }

  @RequiredArgsConstructor
  private static class InsertMethodLocationStatement implements PreparedStatementCreator {

    private final long customerId;
    private final long methodId;
    private final String location;

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
      PreparedStatement ps =
          con.prepareStatement(
              "INSERT INTO method_locations(customerId, methodId, location) VALUES(?, ?, ?) ");
      int column = 0;
      ps.setLong(++column, customerId);
      ps.setLong(++column, methodId);
      ps.setString(++column, location);
      return ps;
    }
  }

  @RequiredArgsConstructor
  private static class InsertIncompleteMethodStatement implements PreparedStatementCreator {

    private final long customerId;
    private final String signature;
    private final long invokedAtMillis;

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
      PreparedStatement ps =
          con.prepareStatement(
              "INSERT INTO methods(customerId, visibility, signature, createdAt) VALUES (?, ?, ?, ?)",
              Statement.RETURN_GENERATED_KEYS);
      int column = 0;
      ps.setLong(++column, customerId);
      ps.setString(++column, "");
      ps.setString(++column, signature);
      ps.setTimestamp(++column, new Timestamp(invokedAtMillis));
      return ps;
    }
  }
}
