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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.library.download.DownloadResult;
import org.eclipse.fordiac.ide.library.download.ILibraryPackageDownloader;
import org.eclipse.fordiac.ide.library.download.LibraryPackageDownloaderProvider;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.library.provider.OnlineLibraryProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.osgi.framework.Version;

/**
 * Reads the dependencies of the online library catalogue from the manifests of
 * an injected endpoint, and reuses the library sources of another catalogue.
 */
class OnlineLibraryProviderTest {
	private static final long TIMEOUT_SECONDS = 10;
	private static final String ENDPOINT = "Endpoint"; //$NON-NLS-1$
	private static final String TEST01 = "test01"; //$NON-NLS-1$
	private static final String TEST02 = "test02"; //$NON-NLS-1$
	private static final Version V1_0_0 = new Version(1, 0, 0);
	private static final Version V1_1_0 = new Version(1, 1, 0);
	// test02 1.1.0 requires test01 [1.0.0-2.0.0), here the closing bracket is missing
	private static final String VALID_RANGE = "[1.0.0-2.0.0)"; //$NON-NLS-1$
	private static final String INVALID_RANGE = "[1.0.0-2.0.0"; //$NON-NLS-1$

	private final MockDownloaderProvider source = new MockDownloaderProvider(List.of());
	private final OnlineLibraryProvider catalogue = new OnlineLibraryProvider(
			new LibraryPackageDownloaderProvider(List.of(source)));

	@AfterAll
	static void cleanupAfterClass() throws IOException {
		MockDownloader.cleanUp();
	}

	@Test
	void skipsVersionWhoseManifestHasAnInvalidDependencyRange() throws IOException {
		final Path fixture = new MockDownloader().downloadManifest(TEST02, V1_1_0, null).result();
		final Path invalid = Files.createTempFile("fordiac-invalid-range", ".MF"); //$NON-NLS-1$ //$NON-NLS-2$
		try {
			Files.writeString(invalid, Files.readString(fixture).replace(VALID_RANGE, INVALID_RANGE));
			source.configure(new MockEndpoint(ENDPOINT) {
				@Override
				public DownloadResult<Path> downloadManifest(final String symbolicName, final Version version,
						final IProgressMonitor monitor) {
					if (TEST02.equals(symbolicName) && V1_1_0.equals(version)) {
						return new DownloadResult<>(invalid);
					}
					return super.downloadManifest(symbolicName, version, monitor);
				}
			});

			final IStatus status = catalogue.refresh(new NullProgressMonitor(), true);

			assertEquals(IStatus.ERROR, status.getSeverity(), status::toString);
			assertTrue(Stream.of(status.getChildren()).map(IStatus::getMessage)
					.anyMatch(message -> message.startsWith(ENDPOINT) && message.contains(TEST02 + " " + V1_1_0) //$NON-NLS-1$
							&& message.contains(INVALID_RANGE)),
					status::toString);
			assertTrue(catalogue.getLibrary(TEST02, V1_1_0).isEmpty(), "the version with the invalid manifest"); //$NON-NLS-1$
			assertTrue(catalogue.getLibrary(TEST02, V1_0_0).isPresent(), "other versions of the library"); //$NON-NLS-1$
			assertTrue(catalogue.getLibrary(TEST01, V1_0_0).isPresent(), "other libraries"); //$NON-NLS-1$
		} finally {
			Files.delete(invalid);
		}
	}

	@SuppressWarnings("static-method")
	@Test
	void reusesTheSourcesOfAnotherCatalogue() {
		final AtomicInteger lookups = new AtomicInteger();
		final OnlineLibraryProvider editor = new OnlineLibraryProvider(
				countingSources(lookups, new MockEndpoint(ENDPOINT)));
		editor.refresh(new NullProgressMonitor(), false);

		final OnlineLibraryProvider wizard = new OnlineLibraryProvider(editor);
		final IStatus status = wizard.refresh(new NullProgressMonitor(), true);

		assertTrue(status.getSeverity() < IStatus.WARNING, status::toString);
		assertEquals(1, lookups.get(), "the downloaders of the other catalogue are reused"); //$NON-NLS-1$
		assertEquals(Map.of(TEST01, VersionComparator.parseVersionRange(VALID_RANGE)),
				wizard.getLibrary(TEST02, V1_1_0).orElseThrow().dependencies());
	}

	@SuppressWarnings("static-method")
	@Test
	void looksUpTheSourcesIfTheOtherCatalogueWasNotRefreshed() {
		final AtomicInteger lookups = new AtomicInteger();
		final OnlineLibraryProvider editor = new OnlineLibraryProvider(
				countingSources(lookups, new MockEndpoint(ENDPOINT)));

		final OnlineLibraryProvider wizard = new OnlineLibraryProvider(editor);
		final IStatus status = wizard.refresh(new NullProgressMonitor(), false);

		assertTrue(status.getSeverity() < IStatus.WARNING, status::toString);
		assertEquals(1, lookups.get(), "the sources are looked up as the other catalogue would"); //$NON-NLS-1$
		assertTrue(wizard.getLibrary(TEST02, V1_1_0).isPresent(), status::toString);
	}

	@SuppressWarnings("static-method")
	@Test
	void waitsForARunningRefreshOfTheOtherCatalogue() throws Exception {
		final CountDownLatch catalogueRequested = new CountDownLatch(1);
		final CountDownLatch releaseCatalogue = new CountDownLatch(1);
		final AtomicInteger catalogueRequests = new AtomicInteger();
		final AtomicInteger lookups = new AtomicInteger();
		final MockEndpoint slowEndpoint = new MockEndpoint(ENDPOINT) {
			@Override
			public DownloadResult<Map<String, List<String>>> availableLibrariesAndVersions(
					final IProgressMonitor monitor) {
				catalogueRequests.incrementAndGet();
				catalogueRequested.countDown();
				try {
					releaseCatalogue.await(TIMEOUT_SECONDS, TimeUnit.SECONDS);
				} catch (final InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				return super.availableLibrariesAndVersions(monitor);
			}
		};
		final OnlineLibraryProvider editor = new OnlineLibraryProvider(countingSources(lookups, slowEndpoint));
		final OnlineLibraryProvider wizard = new OnlineLibraryProvider(editor);
		final AtomicReference<Thread> wizardThread = new AtomicReference<>();
		final ExecutorService executor = Executors.newFixedThreadPool(2);
		try {
			final Future<IStatus> editorRefresh = executor
					.submit(() -> editor.refresh(new NullProgressMonitor(), false));
			assertTrue(catalogueRequested.await(TIMEOUT_SECONDS, TimeUnit.SECONDS),
					"the editor loads its catalogue"); //$NON-NLS-1$
			final Future<IStatus> wizardRefresh = executor.submit(() -> {
				wizardThread.set(Thread.currentThread());
				return wizard.refresh(new NullProgressMonitor(), true);
			});

			awaitBlocked(wizardThread);
			assertEquals(1, catalogueRequests.get(), "only the editor has requested the catalogue"); //$NON-NLS-1$

			releaseCatalogue.countDown();
			final IStatus editorStatus = editorRefresh.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
			final IStatus wizardStatus = wizardRefresh.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
			assertTrue(editorStatus.getSeverity() < IStatus.WARNING, editorStatus::toString);
			assertTrue(wizardStatus.getSeverity() < IStatus.WARNING, wizardStatus::toString);
			assertEquals(1, lookups.get(), "the wizard reuses the downloaders of the editor"); //$NON-NLS-1$
			assertTrue(wizard.getLibrary(TEST02, V1_1_0).isPresent(), wizardStatus::toString);
		} finally {
			releaseCatalogue.countDown();
			executor.shutdownNow();
		}
	}

	private static LibraryPackageDownloaderProvider countingSources(final AtomicInteger lookups,
			final MockEndpoint endpoint) {
		return new LibraryPackageDownloaderProvider(List.of(() -> {
			lookups.incrementAndGet();
			return List.<ILibraryPackageDownloader>of(endpoint);
		}));
	}

	/** Waits until the thread blocks, e.g. on the lock of another catalogue. */
	private static void awaitBlocked(final AtomicReference<Thread> thread) throws InterruptedException {
		final long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(TIMEOUT_SECONDS);
		while (thread.get() == null || thread.get().getState() != Thread.State.BLOCKED) {
			assertTrue(System.nanoTime() < deadline,
					"the refresh does not wait for the other catalogue"); //$NON-NLS-1$
			Thread.sleep(10);
		}
	}
}
