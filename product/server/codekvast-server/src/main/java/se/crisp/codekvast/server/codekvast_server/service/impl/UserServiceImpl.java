package se.crisp.codekvast.server.codekvast_server.service.impl;

import com.google.common.eventbus.EventBus;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.crisp.codekvast.server.codekvast_server.dao.DaemonDAO;
import se.crisp.codekvast.server.codekvast_server.dao.UserDAO;
import se.crisp.codekvast.server.codekvast_server.exception.CodekvastException;
import se.crisp.codekvast.server.codekvast_server.model.AppId;
import se.crisp.codekvast.server.codekvast_server.model.event.display.WebSocketMessage;
import se.crisp.codekvast.server.codekvast_server.model.event.rest.Collector;
import se.crisp.codekvast.server.codekvast_server.model.event.rest.OrganisationSettings;
import se.crisp.codekvast.server.codekvast_server.service.StatisticsService;
import se.crisp.codekvast.server.codekvast_server.service.UserService;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;

/**
 * @author olle.hallin@crisp.se
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final DaemonDAO daemonDAO;
    private final StatisticsService statisticsService;
    private final EventBus eventBus;

    @Inject
    public UserServiceImpl(@NonNull UserDAO userDAO, DaemonDAO daemonDAO, StatisticsService statisticsService, EventBus eventBus) {
        this.userDAO = userDAO;
        this.daemonDAO = daemonDAO;
        this.statisticsService = statisticsService;
        this.eventBus = eventBus;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public WebSocketMessage getWebSocketMessage(String username) throws CodekvastException {
        long organisationId = userDAO.getOrganisationIdForUsername(username);
        return daemonDAO.createWebSocketMessage(organisationId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrganisationSettings(String username, OrganisationSettings organisationSettings) throws CodekvastException {
        long organisationId = userDAO.getOrganisationIdForUsername(username);

        Collection<String> updatedAppNames = daemonDAO.saveSettings(organisationId, organisationSettings);
        statisticsService.recalculateApplicationStatistics(organisationId, updatedAppNames);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCollector(String username, Collector collector) throws CodekvastException {
        long organisationId = userDAO.getOrganisationIdForUsername(username);
        Collection<AppId> appIds = daemonDAO.getApplicationIds(organisationId, collector.getAppName(), collector.getAppVersion(),
                                                              collector.getHostname());
        // The collection does only contain one element
        int rowsDeleted =
                daemonDAO.deleteCollectors(organisationId, collector.getAppName(), collector.getAppVersion(), collector.getHostname());
        int numCollectorsByName = daemonDAO.getNumCollectors(organisationId, collector.getAppName());
        int numCollectorsByNameAndVersion = daemonDAO.getNumCollectors(organisationId, collector.getAppName(), collector.getAppVersion());
        if (numCollectorsByName == 0) {
            log.debug("Deleting application {}", collector.getAppName());
            rowsDeleted += daemonDAO.deleteApplication(organisationId, collector.getAppName());
        } else if (numCollectorsByNameAndVersion > 0) {
            statisticsService.recalculateApplicationStatistics(organisationId, Collections.singletonList(collector.getAppName()));
        } else if (numCollectorsByNameAndVersion == 0) {
            rowsDeleted += daemonDAO.deleteApplicationStatistics(organisationId, collector.getAppName(), collector.getAppVersion());
        }
        log.info("Deleted {}, {} database rows deleted", collector, rowsDeleted);
        eventBus.post(daemonDAO.createWebSocketMessage(organisationId));
    }

}
