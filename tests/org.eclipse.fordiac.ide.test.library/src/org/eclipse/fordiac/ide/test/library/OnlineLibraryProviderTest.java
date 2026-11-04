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
import java.util.stream.Stream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.library.download.DownloadResult;
import org.eclipse.fordiac.ide.library.download.LibraryPackageDownloaderProvider;
import org.eclipse.fordiac.ide.library.provider.OnlineLibraryProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.osgi.framework.Version;

/**
 * Reads the dependencies of the online library catalogue from the manifests of
 * an injected endpoint.
 */
class OnlineLibraryProviderTest {
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
}
