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
package org.eclipse.fordiac.ide.test.library;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.download.AbstractLibraryPackageDownloader;
import org.eclipse.fordiac.ide.library.download.DownloadResult;
import org.eclipse.fordiac.ide.library.download.DownloadResult.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

/**
 * Reads a static layout from a loopback server through the common downloader
 * base. The archive is redirected to a second loopback origin, which must not
 * receive the credentials.
 */
class StaticLayoutDownloaderTest {

	private static final String AUTHORIZATION = "Authorization"; //$NON-NLS-1$
	private static final String PRIVATE_TOKEN = "PRIVATE-TOKEN"; //$NON-NLS-1$
	private static final String USER = "fixture-user"; //$NON-NLS-1$
	private static final String PASSWORD = "fixture-password"; //$NON-NLS-1$
	private static final String TOKEN = "fixture-token"; //$NON-NLS-1$
	private static final String LIBRARY = "httpstub"; //$NON-NLS-1$
	private static final String LOOPBACK_HOST = "127.0.0.1"; //$NON-NLS-1$
	private static final String PREFIX = "/libraries/"; //$NON-NLS-1$
	private static final String ARCHIVE_PATH = "/storage/httpstub-1.1.0.zip"; //$NON-NLS-1$
	private static final String MANIFEST = "<Manifest/>"; //$NON-NLS-1$
	private static final byte[] ARCHIVE = "loopback archive".getBytes(StandardCharsets.UTF_8); //$NON-NLS-1$
	private static final String INDEX = """
			{
			  "formatVersion": 1,
			  "libraries": {
			    "httpstub": ["1.1.0", "1.0.0", "1.1"]
			  }
			}
			"""; //$NON-NLS-1$

	private final List<Headers> storageRequests = new CopyOnWriteArrayList<>();
	private final List<Headers> sourceRequests = new CopyOnWriteArrayList<>();
	private HttpServer source;
	private HttpServer storage;
	private String credentialHeader = AUTHORIZATION;
	private String credentialValue = ""; //$NON-NLS-1$
	private volatile boolean sendChallenge = true;

	@BeforeEach
	void startServers() throws IOException {
		storage = HttpServer.create(new InetSocketAddress(LOOPBACK_HOST, 0), 0);
		storage.createContext("/", exchange -> { //$NON-NLS-1$
			storageRequests.add(exchange.getRequestHeaders());
			respond(exchange, ARCHIVE_PATH.equals(exchange.getRequestURI().getPath()) ? 200 : 404, ARCHIVE);
		});
		storage.start();
		source = HttpServer.create(new InetSocketAddress(LOOPBACK_HOST, 0), 0);
		source.createContext(PREFIX, this::serveSource);
		source.start();
	}

	@AfterEach
	void stopServers() throws IOException {
		source.stop(0);
		storage.stop(0);
		final Path folder = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toFile().toPath()
				.resolve(LibraryManager.PACKAGE_DOWNLOAD_DIRECTORY).resolve("httpstub-1.1.0"); //$NON-NLS-1$
		if (Files.exists(folder)) {
			try (Stream<Path> paths = Files.walk(folder)) {
				for (final Path path : paths.sorted(Comparator.reverseOrder()).toList()) {
					Files.delete(path);
				}
			}
		}
	}

	@ParameterizedTest
	@ValueSource(strings = { AUTHORIZATION, PRIVATE_TOKEN })
	void sendsCredentialsOnlyToTheSource(final String header) throws IOException {
		final Consumer<HttpRequest.Builder> credentials = useCredentials(header);
		final StaticLayoutDownloader downloader = new StaticLayoutDownloader(sourceAddress(), credentials);

		final var catalogue = downloader.availableLibrariesAndVersions(null);
		final DownloadResult<Path> manifest = downloader.downloadManifest(LIBRARY, new Version(1, 1, 0), null);
		final DownloadResult<Path> archive = downloader.downloadLibrary(LIBRARY, null, null, null);

		assertEquals(Status.OK, catalogue.status(), catalogue::message);
		assertEquals(Map.of(LIBRARY, List.of("1.1.0", "1.0.0")), catalogue.result()); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(Status.OK, manifest.status(), manifest::message);
		assertEquals(MANIFEST, Files.readString(manifest.result()));
		assertEquals(Status.OK, archive.status(), archive::message);
		assertArrayEquals(ARCHIVE, Files.readAllBytes(archive.result()));
		// the catalogue is loaded once: index, manifest and archive
		assertEquals(3, sourceRequests.size());
		assertEquals(1, storageRequests.size());
		assertNull(storageRequests.getFirst().getFirst(AUTHORIZATION));
		assertNull(storageRequests.getFirst().getFirst(PRIVATE_TOKEN));
	}

	@ParameterizedTest
	@ValueSource(strings = { "?scope=public", "#public", "?scope=public/#section" }) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	void resolvesCatalogueWithinBasePathWithQueryOrFragment(final String suffix) {
		final String address = sourceAddress();
		final var downloader = new StaticLayoutDownloader(address.substring(0, address.length() - 1) + suffix,
				useCredentials(PRIVATE_TOKEN));

		final var result = downloader.availableLibraries(null);
		assertEquals(Status.OK, result.status(), result::message);
		assertEquals(List.of(LIBRARY), result.result());
		assertEquals(1, sourceRequests.size());
	}

	@Test
	void doesNotDownloadForAnEmptyVersionRange() {
		final var downloader = new StaticLayoutDownloader(sourceAddress(), useCredentials(PRIVATE_TOKEN));
		final VersionRange range = new VersionRange("(1.0.0,1.0.0)"); //$NON-NLS-1$

		final var result = downloader.downloadLibrary(LIBRARY, range, null, null);
		assertEquals(Status.NOT_FOUND, result.status(), result::message);
		assertNull(result.result());
		assertEquals(1, sourceRequests.size());
		assertTrue(storageRequests.isEmpty());
	}

	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	void reportsRejectedCredentialsAsConfigurationError(final boolean challenge) {
		sendChallenge = challenge;
		useCredentials(AUTHORIZATION);
		final StaticLayoutDownloader downloader = new StaticLayoutDownloader(sourceAddress(),
				StaticLayoutDownloader.basic(USER, "wrong-password")); //$NON-NLS-1$

		final var result = downloader.availableLibraries(null);

		assertEquals(Status.CONFIG_ERROR, result.status(), result::message);
		assertNull(result.result());
		assertFalse(result.message().contains("wrong-password"), result::message); //$NON-NLS-1$
	}

	@Test
	void reportsChallengedPrivateTokenRejectionAsConfigurationError() {
		useCredentials(PRIVATE_TOKEN);
		final String rejectedToken = "rejected-fixture-token"; //$NON-NLS-1$
		final var downloader = new StaticLayoutDownloader(sourceAddress(),
				request -> request.header(PRIVATE_TOKEN, rejectedToken));

		final var result = downloader.availableLibraries(null);
		assertEquals(Status.CONFIG_ERROR, result.status(), result::message);
		assertNull(result.result());
		assertTrue(result.message().contains("HTTP 401"), result::message); //$NON-NLS-1$
		assertFalse(result.message().contains(rejectedToken), result::message);
		assertEquals(1, sourceRequests.size());
	}

	@Test
	void downloadsArchiveWithLongFilename() throws IOException {
		final String symbolicName = "a".repeat(245); //$NON-NLS-1$
		final String version = "1.0.0"; //$NON-NLS-1$
		final String folderName = symbolicName + '-' + version;
		final String fileName = folderName + ".zip"; //$NON-NLS-1$
		final Path folder = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toFile().toPath()
				.resolve(LibraryManager.PACKAGE_DOWNLOAD_DIRECTORY).resolve(folderName);
		final Path archivePath = folder.resolve(fileName);
		assertFalse(Files.exists(folder), folder::toString);

		final String index = """
				{"formatVersion":1,"libraries":{"%s":["%s"]}}
				""".formatted(symbolicName, version); //$NON-NLS-1$
		source.createContext(PREFIX + "index.json", //$NON-NLS-1$
				exchange -> respond(exchange, 200, index.getBytes(StandardCharsets.UTF_8)));
		source.createContext(PREFIX + symbolicName + '/' + version + '/' + fileName,
				exchange -> respond(exchange, 200, ARCHIVE));
		final var downloader = new StaticLayoutDownloader(sourceAddress(), useCredentials(PRIVATE_TOKEN));

		try {
			final var result = downloader.downloadLibrary(symbolicName, null, null, null);
			assertEquals(Status.OK, result.status(), result::message);
			assertEquals(archivePath, result.result());
			assertArrayEquals(ARCHIVE, Files.readAllBytes(archivePath));
		} finally {
			Files.deleteIfExists(archivePath);
			Files.deleteIfExists(folder);
		}
	}

	@Test
	void preservesNotFoundStatusWithLargeErrorBody() {
		final var downloader = new StaticLayoutDownloader(sourceAddress(), useCredentials(PRIVATE_TOKEN));
		final var catalogue = downloader.availableLibraries(null);
		assertEquals(Status.OK, catalogue.status(), catalogue::message);
		source.createContext(PREFIX + "httpstub/1.1.0/MANIFEST.MF", //$NON-NLS-1$
				exchange -> respond(exchange, 404, new byte[4096]));
		final String key = AbstractLibraryPackageDownloader.MAX_METADATA_BYTES_PROPERTY;
		final String previous = System.setProperty(key, "64"); //$NON-NLS-1$
		try {
			assertEquals(Status.NOT_FOUND, downloader.downloadManifest(LIBRARY, new Version(1, 1, 0), null).status());
		} finally {
			if (previous == null) {
				System.clearProperty(key);
			} else {
				System.setProperty(key, previous);
			}
		}
	}

	private Consumer<HttpRequest.Builder> useCredentials(final String header) {
		credentialHeader = header;
		if (AUTHORIZATION.equals(header)) {
			final Consumer<HttpRequest.Builder> basic = StaticLayoutDownloader.basic(USER, PASSWORD);
			final HttpRequest.Builder probe = HttpRequest.newBuilder(URI.create(sourceAddress()));
			basic.accept(probe);
			credentialValue = probe.build().headers().firstValue(AUTHORIZATION).orElseThrow();
			return basic;
		}
		credentialValue = TOKEN;
		return request -> request.header(PRIVATE_TOKEN, TOKEN);
	}

	private String sourceAddress() {
		return "http://" + LOOPBACK_HOST + ':' + source.getAddress().getPort() + PREFIX; //$NON-NLS-1$
	}

	private void serveSource(final HttpExchange exchange) throws IOException {
		sourceRequests.add(exchange.getRequestHeaders());
		if (!credentialValue.equals(exchange.getRequestHeaders().getFirst(credentialHeader))) {
			if (sendChallenge) {
				exchange.getResponseHeaders().add("WWW-Authenticate", "Basic realm=\"fixture\""); //$NON-NLS-1$ //$NON-NLS-2$
			}
			respond(exchange, 401, new byte[0]);
			return;
		}
		switch (exchange.getRequestURI().getPath().substring(PREFIX.length())) {
		case "index.json" -> respond(exchange, 200, INDEX.getBytes(StandardCharsets.UTF_8)); //$NON-NLS-1$
		case "httpstub/1.1.0/MANIFEST.MF" -> respond(exchange, 200, MANIFEST.getBytes(StandardCharsets.UTF_8)); //$NON-NLS-1$
		case "httpstub/1.1.0/httpstub-1.1.0.zip" -> { //$NON-NLS-1$
			exchange.getResponseHeaders().add("Location", //$NON-NLS-1$
					"http://" + LOOPBACK_HOST + ':' + storage.getAddress().getPort() + ARCHIVE_PATH); //$NON-NLS-1$
			respond(exchange, 302, new byte[0]);
		}
		default -> respond(exchange, 404, new byte[0]);
		}
	}

	private static void respond(final HttpExchange exchange, final int status, final byte[] body)
			throws IOException {
		exchange.sendResponseHeaders(status, body.length == 0 ? -1 : body.length);
		if (body.length > 0) {
			try (OutputStream out = exchange.getResponseBody()) {
				out.write(body);
			}
		}
		exchange.close();
	}
}
