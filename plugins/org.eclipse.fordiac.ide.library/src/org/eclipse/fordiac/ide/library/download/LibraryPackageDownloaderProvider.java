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
package org.eclipse.fordiac.ide.library.download;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.Messages;

/**
 * Provides the active downloaders of all configured library sources for downloads,
 * the online catalogue and the GitLab sources in the import wizard.
 *
 * <p>
 * Registry contributions and their current configuration are read on every call.
 * Single-source {@code client} contributions are adapted to the same provider
 * contract as {@code provider} contributions. A failing contribution is reported
 * in the result without hiding the downloaders of other sources.
 */
public final class LibraryPackageDownloaderProvider {

	private static final String PLUGIN_ID = "org.eclipse.fordiac.ide.library"; //$NON-NLS-1$

	private final Function<MultiStatus, List<Contribution>> contributions;

	/** Creates a provider backed by the Eclipse extension registry. */
	public LibraryPackageDownloaderProvider() {
		contributions = LibraryPackageDownloaderProvider::loadContributions;
	}

	/**
	 * Creates a provider with explicitly supplied sources instead of registry
	 * contributions. Their configuration is still read on every call.
	 *
	 * @param providers the sources in lookup order
	 */
	public LibraryPackageDownloaderProvider(final List<ILibraryPackageDownloaderProvider> providers) {
		final List<Contribution> sources = providers.stream()
				.map(provider -> new Contribution(provider.getClass().getName(), provider)).toList();
		contributions = _ -> sources;
	}

	/**
	 * Returns active downloaders in contribution and configuration order, together
	 * with any errors encountered while loading or querying the sources. An empty
	 * configuration has an OK status; a failing source has an ERROR status.
	 *
	 * @return the current downloaders and their discovery status
	 */
	public Result getActiveDownloaders() {
		final MultiStatus status = new MultiStatus(PLUGIN_ID, IStatus.OK,
				Messages.LibraryPackageDownloaderProvider_DiscoveryStatus, null);
		final List<ILibraryPackageDownloader> downloaders = new ArrayList<>();
		for (final Contribution contribution : contributions.apply(status)) {
			try {
				// Collect each source before adding it, so a broken contract cannot leave a
				// partially expanded source in the result.
				downloaders.addAll(contribution.provider().getConfiguredDownloaders().stream()
						.filter(ILibraryPackageDownloader::isActive).toList());
			} catch (final OperationCanceledException e) {
				throw e;
			} catch (final RuntimeException e) {
				status.add(sourceError(contribution.name(), e));
			}
		}
		return new Result(downloaders, status);
	}

	private static List<Contribution> loadContributions(final MultiStatus status) {
		final List<Contribution> sources = new ArrayList<>();
		for (final IConfigurationElement element : Platform.getExtensionRegistry()
				.getConfigurationElementsFor(LibraryManager.DOWNLOADER_EXTENSION)) {
			final String name = element.getContributor().getName() + ": " + element.getAttribute("class"); //$NON-NLS-1$ //$NON-NLS-2$
			try {
				final ILibraryPackageDownloaderProvider provider = switch (element.getName()) {
				case "client" -> { //$NON-NLS-1$
					final ILibraryPackageDownloader downloader = (ILibraryPackageDownloader) element
							.createExecutableExtension("class"); //$NON-NLS-1$
					yield () -> List.of(downloader);
				}
				case "provider" -> (ILibraryPackageDownloaderProvider) element.createExecutableExtension("class"); //$NON-NLS-1$ //$NON-NLS-2$
				default -> throw new IllegalArgumentException(
						MessageFormat.format(Messages.LibraryPackageDownloaderProvider_UnknownElement, element.getName()));
				};
				sources.add(new Contribution(name, provider));
			} catch (final OperationCanceledException e) {
				throw e;
			} catch (final CoreException | RuntimeException e) {
				status.add(sourceError(name, e));
			}
		}
		return sources;
	}

	private static IStatus sourceError(final String name, final Exception exception) {
		return Status.error(MessageFormat.format(Messages.LibraryPackageDownloaderProvider_SourceError, name,
				exception.getMessage()), exception);
	}

	private record Contribution(String name, ILibraryPackageDownloaderProvider provider) {
	}

	/** The immutable downloader list and the status of the source discovery. */
	public record Result(List<ILibraryPackageDownloader> downloaders, IStatus status) {
		public Result {
			downloaders = List.copyOf(downloaders);
			Objects.requireNonNull(status);
		}
	}
}
