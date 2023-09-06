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
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search.types;

import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;

public class FBTypeSearch extends InstanceSearch {

	public static FBTypeSearch createFBTypeSearch(final FBType type) {
		return new FBTypeSearch(new FBMemberFilter(type));
	}

	public FBTypeSearch(final SearchFilter filter) {
		super(filter);
	}

	private static class FBMemberFilter implements SearchFilter {

		private final FBType entry;

		public FBMemberFilter(final FBType entry) {
			this.entry = entry;
		}

		@Override
		public boolean apply(final INamedElement searchCandiate) {
			if (searchCandiate instanceof final FB fb && fb.getTypeEntry() != null
					&& fb.getTypeEntry().getTypeEditable() != null) {
				if (fb.getTypeEntry().getTypeEditable() instanceof BasicFBType && entry instanceof BasicFBType
						&& fb.getTypeEntry().getTypeName().equals(entry.getName())) {
					return true;
				}
				if (fb.getTypeEntry().getTypeEditable() instanceof SimpleFBType && entry instanceof SimpleFBType
						&& fb.getTypeEntry().getTypeName().equals(entry.getName())) {
					return true;
				}
				if (fb.getTypeEntry().getTypeEditable() instanceof CompositeFBType && entry instanceof CompositeFBType
						&& fb.getTypeEntry().getTypeName().equals(entry.getName())) {
					return true;
				}
				if (fb.getTypeEntry().getTypeEditable() instanceof ServiceInterfaceFBType
						&& entry instanceof ServiceInterfaceFBType
						&& fb.getTypeEntry().getTypeName().equals(entry.getName())) {
					return true;
				}
			} else if (searchCandiate instanceof final SubApp subApp && subApp.getTypeEntry() != null
					&& (subApp.getTypeEntry().getTypeEditable() instanceof SubAppType && entry instanceof SubAppType
							&& subApp.getTypeEntry().getTypeName().equals(entry.getName()))) {
				return true;
			}
			return false;
		}
	}

}
