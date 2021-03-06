package io.codekvast.dashboard.file_import;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import io.codekvast.common.lock.Lock;
import io.codekvast.common.lock.LockManager;
import io.codekvast.common.lock.LockTemplate;
import io.codekvast.dashboard.bootstrap.CodekvastDashboardSettings;
import io.codekvast.dashboard.metrics.AgentMetricsService;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/** @author olle.hallin@crisp.se */
public class FileImportTaskTest {

  @TempDir File temporaryFolder;

  @Mock private PublicationImporter importer;

  @Mock private AgentMetricsService metricsService;

  @Mock private LockManager lockManager;

  private CodekvastDashboardSettings settings;

  private FileImportTask task;

  @BeforeEach
  public void beforeTest() {
    MockitoAnnotations.openMocks(this);
    settings = new CodekvastDashboardSettings();
    settings.setFileImportQueuePath(temporaryFolder);

    when(lockManager.acquireLock(any())).thenReturn(Optional.of(Lock.forTask("test", 60)));
    task = new FileImportTask(settings, importer, metricsService, new LockTemplate(lockManager));
  }

  @Test
  public void should_handle_empty_fileImportQueuePath() {
    // given
    // An empty fileImportQueuePath

    // when
    task.importPublicationFiles();

    // then
    verifyNoMoreInteractions(importer);
  }

  @Test
  public void should_ignore_non_ser_files() throws Exception {
    // given
    File file = createImportFile(".bar");

    // when
    task.importPublicationFiles();

    // then
    assertThat(file.exists(), is(true));
    verifyNoMoreInteractions(importer);
  }

  @Test
  public void should_delete_file_after_successful_import() throws Exception {
    // given
    settings.setDeleteImportedFiles(true);
    File file = createImportFile(".ser");
    when(importer.importPublicationFile(any(File.class))).thenReturn(true);

    // when
    task.importPublicationFiles();

    // then
    verify(importer).importPublicationFile(file);
    assertThat(file.exists(), is(false));
  }

  @Test
  public void should_not_delete_file_after_failed_import() throws Exception {
    // given
    settings.setDeleteImportedFiles(true);
    File file = createImportFile(".ser");
    when(importer.importPublicationFile(any(File.class))).thenReturn(false);

    // when
    task.importPublicationFiles();

    // then
    verify(importer).importPublicationFile(file);
    assertThat(file.exists(), is(true));
  }

  @Test
  public void should_not_delete_file_after_import() throws Exception {
    // given
    settings.setDeleteImportedFiles(false);
    File file = createImportFile(".ser");

    // when
    task.importPublicationFiles();

    // then
    verify(importer).importPublicationFile(file);
    assertThat(file.exists(), is(true));
  }

  private File createImportFile(String suffix) throws IOException {
    return File.createTempFile("import-", suffix, temporaryFolder);
  }
}
