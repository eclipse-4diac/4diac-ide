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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.library.download.DownloadResult;
import org.eclipse.fordiac.ide.library.download.ILibraryPackageDownloader;
import org.eclipse.fordiac.ide.library.download.ILibraryPackageDownloaderProvider;
import org.eclipse.fordiac.ide.library.download.LibraryPackageDownloaderProvider;
import org.eclipse.fordiac.ide.library.provider.OnlineLibraryProvider;
import org.junit.jupiter.api.Test;

/**
 * Exercises discovery and catalogue consumers with independently configured
 * providers. Registry integration also covers the registered single-source client
 * next to a provider contribution, without shared mock-provider configuration.
 */
@SuppressWarnings("static-method")
class LibraryPackageDownloaderProviderTest {
	private static final String TEST01 = "test01"; //$NON-NLS-1$
	private static final String ENDPOINT_A = "Endpoint A"; //$NON-NLS-1$
	private static final String ENDPOINT_B = "Endpoint B"; //$NON-NLS-1$
	private static final String UNCONFIGURED = "Unconfigured endpoint"; //$NON-NLS-1$
	private static final String INCOMPLETE = "Incomplete endpoint"; //$NON-NLS-1$

	private final MockEndpoint endpointA = new MockEndpoint(ENDPOINT_A);
	private final MockEndpoint endpointB = new MockEndpoint(ENDPOINT_B);
	private final MockDownloaderProvider source = new MockDownloaderProvider(List.of());
	private final LibraryPackageDownloaderProvider provider = new LibraryPackageDownloaderProvider(List.of(source));

	@Test
	void createsOneDownloaderPerConfiguredEndpoint() {
		source.configure(endpointA, endpointB);

		final var result = provider.getActiveDownloaders();

		assertEquals(List.of(endpointA, endpointB), result.downloaders());
		assertTrue(result.status().isOK(), result.status()::toString);
	}

	@Test
	void keepsEndpointsOfAllProviders() {
		source.configure(endpointA, endpointB);
		final MockEndpoint additional = new MockEndpoint("Additional source"); //$NON-NLS-1$
		final var combined = new LibraryPackageDownloaderProvider(List.of(source, () -> List.of(additional)));

		assertEquals(List.of(endpointA, endpointB, additional), combined.getActiveDownloaders().downloaders());
	}

	@Test
	void inactiveAdditionalProviderDoesNotHideConfiguredEndpoints() {
		source.configure(endpointA, endpointB);
		final MockEndpoint additional = new MockEndpoint("Inactive source"); //$NON-NLS-1$
		additional.setActive(false);
		final var combined = new LibraryPackageDownloaderProvider(List.of(() -> List.of(additional), source));

		assertEquals(List.of(endpointA, endpointB), combined.getActiveDownloaders().downloaders());
	}

	@Test
	void readsActivationOnEveryLookup() {
		source.configure(endpointA, endpointB);
		endpointA.setActive(false);
		assertEquals(List.of(endpointB), provider.getActiveDownloaders().downloaders());

		endpointA.setActive(true);
		assertEquals(List.of(endpointA, endpointB), provider.getActiveDownloaders().downloaders());
	}

	@Test
	void emptyConfigurationHasAnOkStatus() {
		final var result = provider.getActiveDownloaders();

		assertTrue(result.downloaders().isEmpty());
		assertTrue(result.status().isOK(), result.status()::toString);
	}

	@Test
	void registryAdaptsSingleSourceAlongsideProvider() {
		final boolean previousActive = LibraryTestFixtures.setMockDownloaderActive(true);
		try {
			final var registryProvider = new LibraryPackageDownloaderProvider();
			final var active = registryProvider.getActiveDownloaders();
			assertTrue(active.status().isOK(), active.status()::toString);
			assertEquals(Set.of(new MockDownloader().getName(), MockDownloaderProvider.FIXTURE_ENDPOINT),
					active.downloaders().stream().map(ILibraryPackageDownloader::getName).collect(Collectors.toSet()));

			LibraryTestFixtures.setMockDownloaderActive(false);
			final var inactive = registryProvider.getActiveDownloaders();
			assertTrue(inactive.status().isOK(), inactive.status()::toString);
			assertEquals(List.of(MockDownloaderProvider.FIXTURE_ENDPOINT),
					inactive.downloaders().stream().map(ILibraryPackageDownloader::getName).toList());
		} finally {
			LibraryTestFixtures.setMockDownloaderActive(previousActive);
		}
	}

	@Test
	void reportsFailingProviderAndKeepsHealthySources() {
		final var failure = new IllegalStateException("provider failure expected by the test"); //$NON-NLS-1$
		final ILibraryPackageDownloaderProvider broken = () -> {
			throw failure;
		};
		source.configure(endpointA);
		final var combined = new LibraryPackageDownloaderProvider(List.of(broken, source));

		final var result = combined.getActiveDownloaders();

		assertEquals(List.of(endpointA), result.downloaders());
		assertEquals(IStatus.ERROR, result.status().getSeverity());
		assertEquals(1, result.status().getChildren().length);
		assertSame(failure, result.status().getChildren()[0].getException());
		assertTrue(result.status().getChildren()[0].getMessage().contains(broken.getClass().getName()));
	}

	@Test
	void failedDiscoveryDiffersFromEmptyConfiguration() {
		final var broken = new LibraryPackageDownloaderProvider(List.of(() -> {
			throw new IllegalStateException("unreadable configuration"); //$NON-NLS-1$
		}));

		final var result = broken.getActiveDownloaders();

		assertTrue(result.downloaders().isEmpty());
		assertEquals(IStatus.ERROR, result.status().getSeverity());
		assertTrue(provider.getActiveDownloaders().status().isOK());
	}

	@Test
	void reportsNullDownloaderList() {
		final var broken = new LibraryPackageDownloaderProvider(List.of(() -> null));

		assertEquals(IStatus.ERROR, broken.getActiveDownloaders().status().getSeverity());
	}

	@Test
	void brokenSourceDoesNotLeavePartiallyExpandedEndpoints() {
		final var broken = new LibraryPackageDownloaderProvider(List.of(() -> Arrays.asList(endpointA, null)));

		final var result = broken.getActiveDownloaders();

		assertTrue(result.downloaders().isEmpty());
		assertEquals(IStatus.ERROR, result.status().getSeverity());
	}

	@Test
	void cancellationIsNotReportedAsAProviderFailure() {
		final var cancellation = new OperationCanceledException();
		final var cancelled = new LibraryPackageDownloaderProvider(List.of(() -> {
			throw cancellation;
		}));

		assertSame(cancellation, assertThrows(OperationCanceledException.class, cancelled::getActiveDownloaders));
	}

	@Test
	void readsConfigurationOnEveryLookupAndKeepsPreviousResult() {
		source.configure(endpointA);
		final var first = provider.getActiveDownloaders();
		source.configure(endpointB);

		assertEquals(List.of(endpointB), provider.getActiveDownloaders().downloaders());
		assertEquals(List.of(endpointA), first.downloaders());
		assertThrows(UnsupportedOperationException.class, () -> first.downloaders().clear());
	}

	@Test
	void injectedProvidersHaveIndependentConfiguration() {
		source.configure(endpointA);
		final var other = new LibraryPackageDownloaderProvider(List.of(new MockDownloaderProvider(List.of(endpointB))));

		assertEquals(List.of(endpointA), provider.getActiveDownloaders().downloaders());
		assertEquals(List.of(endpointB), other.getActiveDownloaders().downloaders());
	}

	@Test
	void catalogueListsConfiguredActiveEndpointsOnly() {
		endpointB.setActive(false);
		source.configure(endpointA, endpointB, new MockEndpoint(UNCONFIGURED, DownloadResult.Status.NO_CONFIG));
		final OnlineLibraryProvider catalogue = new OnlineLibraryProvider(provider);

		final IStatus status = catalogue.refresh(new NullProgressMonitor(), false);

		assertTrue(status.getSeverity() < IStatus.WARNING, status::toString);
		assertEquals(List.of(ENDPOINT_A + ": OK"), getMessages(status)); //$NON-NLS-1$
		assertTrue(catalogue.getAll().containsKey(TEST01));
	}

	@Test
	void catalogueReportsIncompleteEndpoint() {
		source.configure(endpointA, new MockEndpoint(INCOMPLETE, DownloadResult.Status.CONFIG_ERROR));
		final OnlineLibraryProvider catalogue = new OnlineLibraryProvider(provider);

		final IStatus status = catalogue.refresh(new NullProgressMonitor(), false);

		assertEquals(IStatus.ERROR, status.getSeverity(), status::toString);
		assertTrue(Stream.of(status.getChildren())
				.anyMatch(child -> child.getSeverity() == IStatus.ERROR && child.getMessage().startsWith(INCOMPLETE)),
				status::toString);
		assertTrue(catalogue.getAll().containsKey(TEST01), "the configured endpoint is still listed"); //$NON-NLS-1$
	}

	@Test
	void cataloguePropagatesDiscoveryFailureAlongsideAvailableLibraries() {
		source.configure(endpointA);
		final var combined = new LibraryPackageDownloaderProvider(List.of(source, () -> {
			throw new IllegalStateException("unreadable configuration"); //$NON-NLS-1$
		}));
		final OnlineLibraryProvider catalogue = new OnlineLibraryProvider(combined);

		final IStatus status = catalogue.refresh(new NullProgressMonitor(), false);

		assertEquals(IStatus.ERROR, status.getSeverity());
		assertTrue(catalogue.getAll().containsKey(TEST01));
		assertTrue(getMessages(status).stream().anyMatch(message -> message.contains("unreadable configuration"))); //$NON-NLS-1$
	}

	@Test
	void catalogueDoesNotReportFailedDiscoveryAsMissingConfiguration() {
		final var broken = new LibraryPackageDownloaderProvider(List.of(() -> {
			throw new IllegalStateException("unreadable configuration"); //$NON-NLS-1$
		}));
		final OnlineLibraryProvider catalogue = new OnlineLibraryProvider(broken);

		final IStatus status = catalogue.refresh(new NullProgressMonitor(), false);

		assertEquals(IStatus.ERROR, status.getSeverity());
		assertFalse(getMessages(status)
				.contains(org.eclipse.fordiac.ide.library.Messages.OnlineLibraryProvider_NoLibrarySource));
	}

	@Test
	void catalogueReportsMissingSource() {
		final OnlineLibraryProvider catalogue = new OnlineLibraryProvider(provider);
		assertMissingSource(catalogue.refresh(new NullProgressMonitor(), false));

		source.configure(new MockEndpoint(UNCONFIGURED, DownloadResult.Status.NO_CONFIG));
		assertMissingSource(catalogue.refresh(new NullProgressMonitor(), false));
		assertTrue(catalogue.getAll().isEmpty());
	}

	private static void assertMissingSource(final IStatus status) {
		assertEquals(IStatus.INFO, status.getSeverity(), status::toString);
		assertEquals(List.of(org.eclipse.fordiac.ide.library.Messages.OnlineLibraryProvider_NoLibrarySource),
				getMessages(status));
	}

	private static List<String> getMessages(final IStatus status) {
		return Stream.of(status.getChildren()).map(IStatus::getMessage).toList();
	}
}
