/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.utils;

import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class ExportFilterUtil {

	/* Configuration element attributes */
	public static final String FILTER_ID = "id"; //$NON-NLS-1$
	private static final String SORT_INDEX = "sortIndex"; //$NON-NLS-1$
	private static final IConfigurationElement[] filters = getAvailableExportFilters();

	public static Optional<IConfigurationElement> getExportFilter(final String id) {
		return Stream.of(filters).filter(expf -> expf.getAttribute(FILTER_ID).equals(id)).findFirst();
	}

	public static IConfigurationElement[] getExportFilters() {
		return filters;
	}

	private static IConfigurationElement[] getAvailableExportFilters() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IConfigurationElement[] elems = registry
				.getConfigurationElementsFor("org.eclipse.fordiac.ide.export.exportFilter"); //$NON-NLS-1$
		return Stream.of(elems).sorted((o1, o2) -> {
			try {
				final int sortIndex1 = Integer.parseInt(o1.getAttribute(SORT_INDEX));
				final int sortIndex2 = Integer.parseInt(o2.getAttribute(SORT_INDEX));
				return sortIndex1 - sortIndex2;
			} catch (final NumberFormatException e2) {
				FordiacLogHelper.logError(e2.getMessage(), e2);
			}
			return 0;
		}).toArray(IConfigurationElement[]::new);
	}

	private ExportFilterUtil() {
		throw new UnsupportedOperationException();
	}

}
