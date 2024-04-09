/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *   			   - Search partially taken from ModelSearchQuery
 *   Fabio Gandolfi - added search for transfer instance comments
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search.types;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;

public class StructManipulatorSearch extends InstanceSearch {

	public StructManipulatorSearch(final DataTypeEntry dataTypeEntry) {
		super(new StructSearchFilter(dataTypeEntry));
	}

	private static class StructSearchFilter implements SearchFilter {

		private final DataTypeEntry entry;

		public StructSearchFilter(final DataTypeEntry entry) {
			this.entry = entry;
		}

		@Override
		public boolean apply(final INamedElement searchCandidate) {
			if (searchCandidate instanceof final StructManipulator sm) {
				return sm.getDataType().getName().equalsIgnoreCase(entry.getTypeName());
			}
			return false;

		}

	}

}
