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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

public abstract class AbstractLibraryProvider implements ILibraryProvider {

	protected static final String PLUGIN_ID = "org.eclipse.fordiac.ide.library"; //$NON-NLS-1$

	private static final Comparator<LibraryDescriptor> comparator = (o1, o2) -> o1.version().compareTo(o2.version());

	@Override
	public Optional<LibraryDescriptor> getLatest(final String symbolicName) {
		return getAll(symbolicName).stream().sorted(comparator).findFirst();
	}

	@Override
	public Optional<LibraryDescriptor> getLatest(final String symbolicName, final VersionRange range) {
		return getAll(symbolicName).stream().filter(lib -> range.includes(lib.version())).findFirst();
	}

	@Override
	public Optional<LibraryDescriptor> getLibrary(final String symbolicName, final Version version) {
		return getLatest(symbolicName, VersionComparator.parseVersionRange(version.toString()));
	}

	@Override
	public List<LibraryDescriptor> getAll(final String symbolicName) {
		return getLibraryLookup().getOrDefault(symbolicName, Collections.emptyList());
	}

	@Override
	public Map<String, List<LibraryDescriptor>> getAll() {
		return getLibraryLookup();
	}

	@Override
	public IStatus refresh(final IProgressMonitor progress, final boolean fetchDependencies) {
		return Status.OK_STATUS;
	}

	protected abstract Map<String, List<LibraryDescriptor>> getLibraryLookup();

}
