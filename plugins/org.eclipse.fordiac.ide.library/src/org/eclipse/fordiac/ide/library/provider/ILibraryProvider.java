/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.fordiac.ide.library.LibraryRecord;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

/**
 * Provides access to available library versions from a specific source (e.g.
 * local workspace, remote repository).
 * <p>
 * Implementations are responsible for maintaining a cache of available
 * libraries and providing version lookup operations.
 * </p>
 */
public interface ILibraryProvider {

	/**
	 * Returns the latest available version of the given library.
	 *
	 * @param symbolicName the symbolic name of the library
	 * @return the latest available library
	 */
	Optional<LibraryDescriptor> getLatest(final String symbolicName);

	/**
	 * Returns the latest available version of the given library that satisfies the
	 * specified version range.
	 *
	 * @param symbolicName the symbolic name of the library
	 * @param range        the required version range
	 * @return the latest matching library descriptor
	 */
	Optional<LibraryDescriptor> getLatest(final String symbolicName, VersionRange range);

	/**
	 * Returns all available versions of the specified library.
	 *
	 * @param symbolicName the symbolic name of the library
	 * @return a list of available versions, or an empty list if the library is not
	 *         available
	 */
	List<LibraryDescriptor> getAll(final String symbolicName);

	/**
	 * Returns all available libraries known to this provider.
	 *
	 * @return a map containing all available libraries grouped by symbolic name
	 */
	Map<String, List<LibraryDescriptor>> getAll();

	/**
	 * Returns a library with the specified version if available
	 *
	 * @return the library
	 */
	Optional<LibraryDescriptor> getLibrary(final String symbolicName, Version version);

	/**
	 * Refreshes the provider's internal cache.
	 *
	 * @param progress          monitor used to report progress and cancellation
	 * @param fetchDependencies {@code true} if dependency information should be
	 *                          loaded, {@code false} if only library names and
	 *                          versions should be retrieved
	 * @return the status of the refresh operation
	 */
	IStatus refresh(IProgressMonitor progress, boolean fetchDependencies);

	/**
	 * Describes a concrete library version together with its declared dependencies.
	 *
	 * @param symbolicName the symbolic name of the library
	 * @param version      the library version
	 * @param dependencies the required dependencies keyed by symbolic name
	 */
	public record LibraryDescriptor(String symbolicName, Version version, Map<String, VersionRange> dependencies) {

		/**
		 * Creates a {@link LibraryDescriptor} from the given library record.
		 * <p>
		 * If dependency retrieval is enabled, the library manifest is loaded and all
		 * required dependencies are extracted.
		 * </p>
		 *
		 * @param rec               the library record
		 * @param fetchDependencies {@code true} to load dependency information from the
		 *                          manifest, {@code false} to create a descriptor
		 *                          without dependencies
		 * @return the created library descriptor
		 */
		public static LibraryDescriptor fromRecord(final LibraryRecord rec, final boolean fetchDependencies) {
			if (!fetchDependencies) {
				return new LibraryDescriptor(rec.symbolicName(), rec.version(), Map.of());
			}

			final Manifest manifest = ManifestHelper.getFolderManifest(rec.path());
			final Map<String, VersionRange> dependencies = new HashMap<>();
			if (manifest != null && manifest.getDependencies() != null) {
				manifest.getDependencies().getRequired().forEach(req -> dependencies.put(req.getSymbolicName(),
						VersionComparator.parseVersionRange(req.getVersion())));
			}

			return new LibraryDescriptor(rec.symbolicName(), rec.version(), dependencies);
		}
	}

}
