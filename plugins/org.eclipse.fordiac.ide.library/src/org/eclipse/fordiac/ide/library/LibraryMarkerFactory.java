/*******************************************************************************
 * Copyright (c) 2025, 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *   Mario Kastner - extracted code from LibraryManager + add broken link marker
 *******************************************************************************/
package org.eclipse.fordiac.ide.library;

import java.text.MessageFormat;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFolder;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;

public class LibraryMarkerFactory {

	public static final String MARKER_ATTRIBUTE = "LIB"; //$NON-NLS-1$

	/**
	 * Creates error marker based on dependency and resolved node
	 *
	 * @param manifest manifest to attach marker
	 * @param rnode    resolved node
	 * @param dnode    dependency node
	 * @return {@link ErrorMarkerBuilder} for error
	 */
	public static ErrorMarkerBuilder createDependencyMarker(final Manifest manifest, final ResolveNode rnode,
			final DependencyNode dnode) {
		return createLibraryMarker(MessageFormat.format(rnode.getError(), rnode.getSymbolicName(),
				VersionComparator.formatVersionRange(dnode.getRange()), String.join(", ", dnode.getCauses().keySet()))) //$NON-NLS-1$
				.setTarget(manifest.getDependencies())
				.addAdditionalAttributes(Map.of(MARKER_ATTRIBUTE, rnode.getSymbolicName()));
	}

	/**
	 * Creates version range error marker based on dependency node
	 *
	 * @param manifest manifest to attach marker
	 * @param dnode    dependency node
	 * @return {@link ErrorMarkerBuilder} for error
	 */
	public static ErrorMarkerBuilder createDependencyMarker(final Manifest manifest, final DependencyNode dnode) {
		final String causedBy = dnode.getCauses().entrySet().stream()
				.map(entry -> entry.getKey() + ": " + VersionComparator.formatVersionRange(entry.getValue())) //$NON-NLS-1$
				.collect(Collectors.joining(", ")); //$NON-NLS-1$

		return createLibraryMarker(
				MessageFormat.format(Messages.ErrorMarkerVersionRangeEmpty, dnode.getSymbolicName(), causedBy))
				.setTarget(manifest.getDependencies())
				.addAdditionalAttributes(Map.of(MARKER_ATTRIBUTE, dnode.getSymbolicName()));
	}

	/**
	 * Creates broken link error marker based on library folder handle
	 *
	 * @param libFolder the folder
	 * @return {@link ErrorMarkerBuilder} for error
	 */
	public static ErrorMarkerBuilder createBrokenLinkMarker(final IFolder libFolder) {
		return createLibraryMarker(Messages.LibraryManager_BrokenLink)
				.setLocation(MessageFormat.format("Library: {0} - Version: {1}", libFolder.getName(), //$NON-NLS-1$
						LibraryManager.parseLibraryVersion(libFolder)));
	}

	/**
	 * Creates invalid version error marker
	 *
	 * @param symbolicName of the library
	 * @param version      description of the library
	 * @return {@link ErrorMarkerBuilder} for error
	 */
	public static ErrorMarkerBuilder createInvalidVersionMarker(final String symbolicName, final String version) {
		return createLibraryMarker(
				MessageFormat.format(Messages.VersionValidator_DeclarationError, version, symbolicName));
	}

	private static ErrorMarkerBuilder createLibraryMarker(final String message) {
		return ErrorMarkerBuilder.createErrorMarkerBuilder(message).setType(FordiacErrorMarker.LIBRARY_MARKER);
	}

	private LibraryMarkerFactory() {
		throw new UnsupportedOperationException();
	}

}
