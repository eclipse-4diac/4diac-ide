/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dario Romano - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search.types;

import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;

public class SubAppSearch extends InstanceSearch {

	public static SubAppSearch createSubappSearch(final StructuredType entry) {
		return new SubAppSearch(new SubAppSearchFilter((DataTypeEntry) entry.getTypeEntry()));
	}

	public SubAppSearch(final DataTypeEntry dataTypeEntry) {
		super(new SubAppSearchFilter(dataTypeEntry));
	}

	private SubAppSearch(final SearchFilter filter) {
		super(filter);
	}

	private static class SubAppSearchFilter implements SearchFilter {

		private final DataTypeEntry entry;

		public SubAppSearchFilter(final DataTypeEntry entry) {
			this.entry = entry;
		}

		@Override
		public boolean apply(final INamedElement searchCandiate) {
			if (searchCandiate instanceof final SubApp subapp) {
				return subapp.getInterface().getAllInterfaceElements().stream()
						.anyMatch(i -> i.getTypeName().equals(entry.getTypeName()));
			}
			return false;
		}
	}
}
