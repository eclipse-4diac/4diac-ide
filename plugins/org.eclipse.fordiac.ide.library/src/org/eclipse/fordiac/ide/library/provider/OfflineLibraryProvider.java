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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.library.LibraryManager;

public class OfflineLibraryProvider extends AbstractLibraryProvider {

	Map<String, List<LibraryDescriptor>> availableLibs = Map.of();

	@Override
	public IStatus refresh(final IProgressMonitor progress, final boolean fetchDependencies) {
		availableLibs = Stream
				.concat(LibraryManager.INSTANCE.getExtractedLibraries().entrySet().stream(),
						LibraryManager.INSTANCE.getStandardLibraries().entrySet().stream())
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream()
						.map(e -> LibraryDescriptor.fromRecord(e, fetchDependencies)).toList()));

		return Status.OK_STATUS;
	}

	@Override
	protected Map<String, List<LibraryDescriptor>> getLibraryLookup() {
		return availableLibs;
	}

}
