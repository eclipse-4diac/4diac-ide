/*******************************************************************************
 * Copyright (c) 2026 Osoft e.U.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gitlab.management;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.gitlab.Messages;
import org.eclipse.fordiac.ide.gitlab.preferences.GitLabEndpoint;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.download.AbstractLibraryPackageDownloader;
import org.eclipse.fordiac.ide.library.download.DownloadResult;
import org.eclipse.fordiac.ide.library.download.DownloadResult.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

/**
 * Reads the GitLab REST API through the common downloader base, with local
 * fixtures instead of a GitLab server.
 */
class GitLabDownloaderTest {

	private static final String TOKEN = "glpat-fixture-token"; //$NON-NLS-1$
	private static final String ENDPOINT = "https://127.0.0.1:8443/gitlab"; //$NON-NLS-1$
	private static final String BASE = ENDPOINT + "/api/v4/projects"; //$NON-NLS-1$
	private static final String PROJECTS_PAGE = BASE + "?membership=true&per_page=100&page="; //$NON-NLS-1$
	private static final String PACKAGES_11 = BASE + "/11/packages?per_page=100&page=1"; //$NON-NLS-1$
	private static final String PACKAGES_13 = BASE + "/13/packages?per_page=100&page=1"; //$NON-NLS-1$
	private static final String FILES_101 = BASE + "/11/packages/101/package_files?per_page=100&page=1"; //$NON-NLS-1$
	private static final String MOTOR_1_0 = BASE + "/11/packages/generic/motor/1.0/"; //$NON-NLS-1$
	private static final String ARCHIVE_NAME = "motor-1.0.zip"; //$NON-NLS-1$
	private static final String STORAGE = "https://localhost:9443/storage/motor-1.0.zip?X-Amz-Signature=fixture"; //$NON-NLS-1$
	private static final String NEXT_PAGE = "X-Next-Page"; //$NON-NLS-1$
	private static final String LOCATION = "Location"; //$NON-NLS-1$
	private static final String PRIVATE_TOKEN = "PRIVATE-TOKEN"; //$NON-NLS-1$
	private static final String MOTOR = "motor"; //$NON-NLS-1$
	private static final String ROBOT = "robot"; //$NON-NLS-1$
	private static final VersionRange BELOW_2 = new VersionRange("[1.0.0,2.0.0)"); //$NON-NLS-1$
	private static final byte[] ARCHIVE = "fixture archive".getBytes(StandardCharsets.UTF_8); //$NON-NLS-1$
	private static final String MANIFEST = "<Manifest/>"; //$NON-NLS-1$

	private static final String PROJECTS_1 = """
			[
			  {
			    "id": 11,
			    "name": "Motors",
			    "packages_enabled": true,
			    "package_registry_access_level": "enabled"
			  },
			  {
			    "id": 12,
			    "name": "Without package registry",
			    "packages_enabled": false
			  }
			]
			"""; //$NON-NLS-1$

	private static final String PROJECTS_2 = """
			[
			  {
			    "id": 13,
			    "name": "Robots",
			    "package_registry_access_level": "private"
			  },
			  {
			    "id": 14,
			    "name": "Disabled package registry",
			    "package_registry_access_level": "disabled"
			  }
			]
			"""; //$NON-NLS-1$

	private static final String MOTOR_PACKAGES = """
			[
			  { "id": 101, "name": "motor", "version": "1.0", "package_type": "generic" },
			  { "id": 102, "name": "motor", "version": "2.0.0", "package_type": "generic" }
			]
			"""; //$NON-NLS-1$

	private static final String ROBOT_PACKAGES = """
			[
			  { "id": 131, "name": "robot", "version": "3.0.1", "package_type": "generic" }
			]
			"""; //$NON-NLS-1$

	private final FakeTransport transport = new FakeTransport();
	private final GitLabDownloader downloader = downloader(ENDPOINT, TOKEN);

	private Path downloadFolder;

	@BeforeEach
	void checkDownloadFolder() {
		final Path folder = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toFile().toPath()
				.resolve(LibraryManager.PACKAGE_DOWNLOAD_DIRECTORY).resolve("motor-1.0.0"); //$NON-NLS-1$
		assertFalse(Files.exists(folder), folder::toString);
		downloadFolder = folder;
	}

	@AfterEach
	void deleteDownloads() throws IOException {
		if (downloadFolder != null) {
			Files.deleteIfExists(downloadFolder.resolve("motor-1.0.0.zip")); //$NON-NLS-1$
			Files.deleteIfExists(downloadFolder.resolve(LibraryManager.MANIFEST));
			Files.deleteIfExists(downloadFolder);
		}
	}

	@Test
	void listsAllPagesOfFormattedResponses() {
		givenCatalogue();

		final var result = downloader.availableLibrariesAndVersions(null);

		assertEquals(Status.OK, result.status(), result::message);
		assertEquals(Map.of(MOTOR, List.of("1.0.0", "2.0.0"), ROBOT, List.of("3.0.1")), result.result()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		// no package request for projects without package registry
		assertEquals(List.of(PROJECTS_PAGE + 1, PROJECTS_PAGE + 2, PACKAGES_11, PACKAGES_13), requestedUris());
		assertTrue(transport.requests().stream()
				.allMatch(request -> request.headers().firstValue(PRIVATE_TOKEN).equals(Optional.of(TOKEN))));
	}

	@Test
	void skipsEntriesWithMissingOrInvalidFields() {
		transport.respond(PROJECTS_PAGE + 1, 200, """
				[
				  { "id": 11, "name": "Motors" },
				  { "name": "Without id" }
				]
				"""); //$NON-NLS-1$
		transport.respond(PACKAGES_11, 200, """
				[
				  { "id": 101, "name": "motor", "version": "1.0", "package_type": "generic" },
				  { "id": 102, "version": "2.0.0", "package_type": "generic" },
				  { "id": 103, "name": "motor", "version": "../2.1.0", "package_type": "generic" },
				  { "id": 104, "name": "motor", "version": "2.2.0", "package_type": "generic/.." },
				  { "id": 105, "name": "motor", "version": "two", "package_type": "generic" },
				  { "id": 106, "name": "motor.ext", "version": "1.0.0", "package_type": "generic" },
				  { "name": "motor", "version": "2.3.0", "package_type": "generic" }
				]
				"""); //$NON-NLS-1$

		final var result = downloader.availableLibrariesAndVersions(null);

		assertEquals(Status.OK, result.status(), result::message);
		assertEquals(Map.of(MOTOR, List.of("1.0.0")), result.result()); //$NON-NLS-1$
		assertEquals(List.of("1.0"), downloader.getPackagesAndLeaves().get(MOTOR).stream() //$NON-NLS-1$
				.map(leaf -> leaf.getVersion()).toList());
	}

	@Test
	void reportsOneWarningForInvalidPackagesAcrossProjects() {
		givenCatalogue();
		transport.respond(PACKAGES_11, 200, "[null]"); //$NON-NLS-1$
		transport.respond(PACKAGES_13, 200, "[null]"); //$NON-NLS-1$

		final List<IStatus> warnings = new ArrayList<>();
		final ILogListener listener = (status, _) -> {
			if (status.getSeverity() == IStatus.WARNING) {
				warnings.add(status);
			}
		};
		Platform.addLogListener(listener);
		try {
			final var result = downloader.availableLibraries(null);
			assertEquals(Status.OK, result.status(), result::message);
			assertEquals(List.of(), result.result());
		} finally {
			Platform.removeLogListener(listener);
		}

		assertEquals(List.of(MessageFormat.format(Messages.Skipped_Invalid_Packages, downloader.getName(), 2)),
				warnings.stream().map(IStatus::getMessage).toList());
	}

	@ParameterizedTest
	@ValueSource(strings = { "[ { \"id\": 11, ", "{ \"message\": \"401 Unauthorized\" }", "", "null" })
	void reportsInvalidJsonAsError(final String body) {
		transport.respond(PROJECTS_PAGE + 1, 200, body);

		assertError(downloader.availableLibraries(null));
	}

	@Test
	void reportsFailedRequestAsErrorInsteadOfPartialCatalogue() {
		givenCatalogue();
		transport.respond(PACKAGES_13, 500, ""); //$NON-NLS-1$

		assertError(downloader.availableLibrariesAndVersions(null));
		assertTrue(downloader.getProjectsAndPackages().isEmpty());
	}

	@Test
	void skipsProjectWhosePackageListIsForbidden() {
		givenCatalogue();
		transport.respond(PROJECTS_PAGE + 2, 200, "[{\"id\":13,\"name\":\"Robots\"}]"); //$NON-NLS-1$
		transport.respond(PACKAGES_13, 403, ""); //$NON-NLS-1$

		final var result = downloader.availableLibrariesAndVersions(null);

		assertEquals(Status.OK, result.status(), result::message);
		assertEquals(Map.of(MOTOR, List.of("1.0.0", "2.0.0")), result.result()); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@ParameterizedTest
	@ValueSource(booleans = { false, true })
	void reportsForbiddenPackagesForEnabledRegistryOrLaterPage(final boolean secondPage) {
		givenCatalogue();
		if (secondPage) {
			transport.respond(PROJECTS_PAGE + 1, 200, "[{\"id\":11,\"name\":\"Motors\"}]"); //$NON-NLS-1$
			transport.respond(PACKAGES_11, 200, MOTOR_PACKAGES, NEXT_PAGE, "2"); //$NON-NLS-1$
		}
		transport.respond(secondPage ? BASE + "/11/packages?per_page=100&page=2" : PACKAGES_11, 403, ""); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(Status.CONFIG_ERROR, downloader.availableLibraries(null).status());
		assertTrue(downloader.getProjectsAndPackages().isEmpty());
	}

	@Test
	void downloadsSelectedArchiveWithoutTokenAfterRedirectToOtherOrigin() throws IOException {
		givenCatalogue();
		givenArchiveFiles();
		transport.respond(MOTOR_1_0 + ARCHIVE_NAME, 302, "", LOCATION, STORAGE); //$NON-NLS-1$
		transport.respond(STORAGE, 200, ARCHIVE);

		final DownloadResult<Path> result = downloader.downloadLibrary(MOTOR, BELOW_2, null, null);

		assertEquals(Status.OK, result.status(), result::message);
		assertArrayEquals(ARCHIVE, Files.readAllBytes(result.result()));
		assertEquals("motor-1.0.0.zip", result.result().getFileName().toString()); //$NON-NLS-1$
		assertEquals(Optional.of(TOKEN), request(MOTOR_1_0 + ARCHIVE_NAME).headers().firstValue(PRIVATE_TOKEN));
		assertTrue(request(STORAGE).headers().firstValue(PRIVATE_TOKEN).isEmpty());
	}

	@Test
	void refusesRedirectToUnencryptedAddress() {
		givenCatalogue();
		givenArchiveFiles();
		transport.respond(MOTOR_1_0 + ARCHIVE_NAME, 302, "", LOCATION, //$NON-NLS-1$
				"http://localhost:9080/storage/motor-1.0.zip"); //$NON-NLS-1$

		assertError(downloader.downloadLibrary(MOTOR, BELOW_2, null, null));
		assertTrue(requestedUris().stream().noneMatch(uri -> uri.startsWith("http:"))); //$NON-NLS-1$
	}

	@Test
	void findsManifestOfVersionWithoutMicroPart() throws IOException {
		givenCatalogue();
		transport.respond(MOTOR_1_0 + LibraryManager.MANIFEST, 200, MANIFEST);

		final DownloadResult<Path> result = downloader.downloadManifest(MOTOR, new Version(1, 0, 0), null);

		assertEquals(Status.OK, result.status(), result::message);
		assertEquals(MANIFEST, Files.readString(result.result()));
	}

	@Test
	void reportsMissingManifestAsNotFound() {
		givenCatalogue();
		transport.respond(MOTOR_1_0 + LibraryManager.MANIFEST, 404, ""); //$NON-NLS-1$

		assertEquals(Status.NOT_FOUND, downloader.downloadManifest(MOTOR, new Version(1, 0, 0), null).status());
	}

	@Test
	void limitsResponseSize() {
		givenCatalogue();
		final String key = AbstractLibraryPackageDownloader.MAX_METADATA_BYTES_PROPERTY;
		final String previous = System.setProperty(key, "64"); //$NON-NLS-1$
		try {
			assertError(downloader.availableLibraries(null));
		} finally {
			restoreProperty(key, previous);
		}
	}

	@Test
	void limitsNumberOfPages() {
		givenCatalogue();
		final String key = GitLabDownloader.MAX_PAGES_PROPERTY;
		final String previous = System.setProperty(key, "1"); //$NON-NLS-1$
		try {
			assertError(downloader.availableLibraries(null));
			assertEquals(List.of(PROJECTS_PAGE + 1), requestedUris());
		} finally {
			restoreProperty(key, previous);
		}
	}

	@ParameterizedTest
	@CsvSource({ "401, CONFIG_ERROR, HTTP 401", "403, CONFIG_ERROR, HTTP 403", "407, ERROR, HTTP 407",
			"429, ERROR, retry after 30 seconds", "500, ERROR, HTTP 500", "503, ERROR, HTTP 503", "418, ERROR, HTTP status 418" })
	void reportsUnsuccessfulStatus(final int status, final Status expected, final String message) {
		transport.respond(PROJECTS_PAGE + 1, status, "", "Retry-After", "30"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		final var result = downloader.availableLibraries(null);

		assertEquals(expected, result.status(), result::message);
		assertNull(result.result());
		assertTrue(result.message().contains(message), result::message);
		assertFalse(result.message().contains(TOKEN), result::message);
	}

	@Test
	void rejectsInvalidNextPage() {
		transport.respond(PROJECTS_PAGE + 1, 200, PROJECTS_1, NEXT_PAGE, "2&per_page=1"); //$NON-NLS-1$

		assertError(downloader.availableLibraries(null));
	}

	@Test
	void stopsListingWhenCanceled() {
		givenCatalogue();
		final NullProgressMonitor monitor = new NullProgressMonitor();
		transport.onRequest(() -> monitor.setCanceled(true));

		assertThrows(OperationCanceledException.class, () -> downloader.availableLibraries(monitor));
		assertEquals(List.of(PROJECTS_PAGE + 1), requestedUris());
	}

	@Test
	void incompleteConfigurationSendsNoRequest() {
		final GitLabDownloader withoutToken = downloader(ENDPOINT, ""); //$NON-NLS-1$

		assertEquals(Status.CONFIG_ERROR, withoutToken.availableLibraries(null).status());
		assertTrue(transport.requests().isEmpty());
	}

	@Test
	void rejectsUserInformationInEndpointAddress() {
		final GitLabDownloader withUserInfo = downloader("https://user:secret-password@127.0.0.1:8443/gitlab", //$NON-NLS-1$
				TOKEN);

		final var result = withUserInfo.availableLibraries(null);

		assertEquals(Status.CONFIG_ERROR, result.status());
		assertFalse(result.message().contains("secret-password")); //$NON-NLS-1$
		assertTrue(transport.requests().isEmpty());
	}

	@Test
	void rejectsInvalidCredentialHeader() {
		final var result = downloader(ENDPOINT, TOKEN + "\ninvalid").availableLibraries(null); //$NON-NLS-1$
		assertEquals(Status.CONFIG_ERROR, result.status(), result::message);
		assertFalse(result.message().contains(TOKEN));
		assertTrue(transport.requests().isEmpty());
	}

	@Test
	void rejectsNonBooleanRegistryFlag() {
		transport.respond(PROJECTS_PAGE + 1, 200, """
				[{ "id": 11, "name": "Motors", "packages_enabled": "unknown" }]
				"""); //$NON-NLS-1$
		assertError(downloader.availableLibraries(null));
	}

	@Test
	void usesOnlyArchivesMatchingThePackageIdentity() throws IOException {
		givenCatalogue();
		transport.respond(FILES_101, 200, """
				[{ "file_name": "motor~linux.zip" }, { "file_name": "motor-2.0.0.zip" },
				 { "file_name": "motor-1.0.zip" }, { "file_name": "motor-1.0.0.zip" }]
				"""); //$NON-NLS-1$
		final String archiveName = "motor-1.0.0.zip"; //$NON-NLS-1$
		transport.respond(MOTOR_1_0 + archiveName, 200, ARCHIVE);

		final var result = downloader.downloadLibrary(MOTOR, BELOW_2, null, null);
		assertEquals(Status.OK, result.status(), result::message);
		final var entry = downloader.getProjectsAndPackages().entrySet().iterator().next();
		final Path wizardDownload = downloader.packageDownloader(entry.getKey(), entry.getValue().getFirst(),
				GitLabDownloader.FileFilter.ZIP);
		assertEquals(result.result(), wizardDownload);
		assertArrayEquals(ARCHIVE, Files.readAllBytes(wizardDownload));
		assertEquals(List.of(MOTOR_1_0 + archiveName, MOTOR_1_0 + archiveName),
				requestedUris().stream().filter(uri -> uri.endsWith(".zip")).toList()); //$NON-NLS-1$
	}

	@ParameterizedTest
	@CsvSource({ "'[{\"file_name\":\"motor~linux.zip\"}]', NOT_FOUND", "'[{}]', ERROR" })
	void distinguishesUnrelatedArchivesFromInvalidFileEntries(final String files, final Status expected) {
		givenCatalogue();
		transport.respond(FILES_101, 200, files);
		final var result = downloader.downloadLibrary(MOTOR, BELOW_2, null, null);
		assertEquals(expected, result.status(), result::message);
		assertTrue(requestedUris().stream().noneMatch(uri -> uri.endsWith(".zip"))); //$NON-NLS-1$
	}

	@Test
	void resolvesLocatorAtPositiveLongLimit() throws IOException {
		final String id = Long.toString(Long.MAX_VALUE);
		transport.respond(PROJECTS_PAGE + 1, 200, """
				[{ "id": %s, "name": "Motors" }]
				""".formatted(id)); //$NON-NLS-1$
		transport.respond(BASE + '/' + id + "/packages?per_page=100&page=1", 200, """
				[{ "id": %s, "name": "motor", "version": "1.0", "package_type": "generic" }]
				""".formatted(id)); //$NON-NLS-1$ //$NON-NLS-2$
		transport.respond(BASE + '/' + id + "/packages/generic/motor/1.0/MANIFEST.MF", 200, MANIFEST); //$NON-NLS-1$
		final var result = downloader.downloadManifest(MOTOR, new Version(1, 0, 0), null);
		assertEquals(Status.OK, result.status(), result::message);
		assertEquals(MANIFEST, Files.readString(result.result()));
	}

	@ParameterizedTest
	@ValueSource(ints = { 200, 204, 206 })
	void keepsPreviousArchiveOnEmptyOrPartialResponse(final int status) throws IOException {
		givenCatalogue();
		givenArchiveFiles();
		final String archive = MOTOR_1_0 + ARCHIVE_NAME;
		transport.respond(archive, 200, ARCHIVE);
		final var valid = downloader.downloadLibrary(MOTOR, BELOW_2, null, null);
		assertEquals(Status.OK, valid.status(), valid::message);
		transport.respond(archive, status, status == 206 ? "partial" : ""); //$NON-NLS-1$ //$NON-NLS-2$

		assertError(downloader.downloadLibrary(MOTOR, BELOW_2, null, null));
		assertArrayEquals(ARCHIVE, Files.readAllBytes(valid.result()));
	}

	@Test
	void keepsPreviousArchiveWhenCanceledAsBodyCompletes() throws IOException {
		givenCatalogue();
		givenArchiveFiles();
		final String archive = MOTOR_1_0 + ARCHIVE_NAME;
		transport.respond(archive, 200, ARCHIVE);
		final var valid = downloader.downloadLibrary(MOTOR, BELOW_2, null, null);
		assertEquals(Status.OK, valid.status(), valid::message);

		final NullProgressMonitor monitor = new NullProgressMonitor();
		transport.respond(archive, 200, "replacement"); //$NON-NLS-1$
		transport.onRequest(() -> {
			if (requestedUris().getLast().equals(archive)) {
				monitor.setCanceled(true);
			}
		});
		assertThrows(OperationCanceledException.class, () -> downloader.downloadLibrary(MOTOR, BELOW_2, null, monitor));
		assertArrayEquals(ARCHIVE, Files.readAllBytes(valid.result()));
	}

	@Test
	void reportsInvalidRedirectWithSanitizedLocation() {
		givenCatalogue();
		givenArchiveFiles();
		final String fragment = "fixture-signature%ZZ"; //$NON-NLS-1$
		transport.respond(MOTOR_1_0 + ARCHIVE_NAME, 302, "", LOCATION, "/archive.zip?signature=" + fragment); //$NON-NLS-1$ //$NON-NLS-2$
		final List<IStatus> logged = new ArrayList<>();
		final ILogListener listener = (status, _) -> logged.add(status);
		Platform.addLogListener(listener);
		final DownloadResult<Path> result;
		try {
			result = downloader.downloadLibrary(MOTOR, BELOW_2, null, null);
		} finally {
			Platform.removeLogListener(listener);
		}
		assertError(result);
		assertFalse(result.message().contains(fragment), result::message);
		final IStatus loggedStatus = logged.stream().filter(status -> status.getMessage().equals(result.message()))
				.findFirst().orElseThrow();
		assertNull(loggedStatus.getException());
	}

	@Test
	void followsQueryOnlyRedirect() throws IOException {
		givenCatalogue();
		givenArchiveFiles();
		final String archive = MOTOR_1_0 + ARCHIVE_NAME;
		transport.respond(archive, 302, "", LOCATION, "?download=1"); //$NON-NLS-1$ //$NON-NLS-2$
		transport.respond(archive + "?download=1", 200, ARCHIVE); //$NON-NLS-1$

		final var result = downloader.downloadLibrary(MOTOR, BELOW_2, null, null);
		assertEquals(Status.OK, result.status(), result::message);
		assertArrayEquals(ARCHIVE, Files.readAllBytes(result.result()));
	}

	private static void restoreProperty(final String key, final String value) {
		if (value == null) {
			System.clearProperty(key);
		} else {
			System.setProperty(key, value);
		}
	}

	private GitLabDownloader downloader(final String url, final String token) {
		return new GitLabDownloader(new GitLabEndpoint("Fixture", url, token), transport); //$NON-NLS-1$
	}

	private void givenCatalogue() {
		transport.respond(PROJECTS_PAGE + 1, 200, PROJECTS_1, NEXT_PAGE, "2") //$NON-NLS-1$
				.respond(PROJECTS_PAGE + 2, 200, PROJECTS_2, NEXT_PAGE, "") //$NON-NLS-1$
				.respond(PACKAGES_11, 200, MOTOR_PACKAGES).respond(PACKAGES_13, 200, ROBOT_PACKAGES);
	}

	private void givenArchiveFiles() {
		transport.respond(FILES_101, 200, """
				[
				  { "file_name": "MANIFEST.MF" },
				  { "file_name": "motor-1.0.zip" }
				]
				"""); //$NON-NLS-1$
	}

	private List<String> requestedUris() {
		return transport.requests().stream().map(request -> request.uri().toString()).toList();
	}

	private HttpRequest request(final String uri) {
		return transport.requests().stream().filter(request -> request.uri().toString().equals(uri)).findFirst()
				.orElseThrow();
	}

	private static void assertError(final DownloadResult<?> result) {
		assertEquals(Status.ERROR, result.status(), result::message);
		assertNull(result.result());
		assertFalse(result.message().contains(TOKEN), result::message);
	}
}
