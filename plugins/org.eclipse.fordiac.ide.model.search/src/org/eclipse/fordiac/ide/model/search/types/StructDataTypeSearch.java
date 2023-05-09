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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search.types;

import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class StructDataTypeSearch extends InstanceSearch {

	
	public static StructDataTypeSearch createStructMemberSearch(final StructuredType entry) {
		return new StructDataTypeSearch(new StructMemberFilter(entry));
	}
	
	public static StructDataTypeSearch createStructInterfaceSearch(final StructuredType entry) {
		return new StructDataTypeSearch(new StructInterfaceFilter(entry));
	}
	
	private StructDataTypeSearch(SearchFilter filter) {
		super(filter);
	}

	private static class StructMemberFilter implements SearchFilter {

		private final StructuredType entry;

		public StructMemberFilter(final StructuredType entry) {
			this.entry = entry;
		}

		@Override
		public boolean apply(final INamedElement searchCandiate) {
			if (searchCandiate instanceof StructuredType structuredType) {
				for (final VarDeclaration varDecl : structuredType.getMemberVariables()) {
					if (varDecl.getTypeName().equals(entry.getName())) {
						return true;
					}
				}
			}
			return false;
		}
	}
	
	private static class StructInterfaceFilter implements SearchFilter{

		private final StructuredType entry;

		public StructInterfaceFilter(final StructuredType entry) {
			this.entry = entry;
		}
		
		@Override
		public boolean apply(INamedElement searchCanditate) {
			if(searchCanditate instanceof FBType type) {
				return type.getInterfaceList().getAllInterfaceElements().stream().anyMatch(e -> e.getTypeName().equals(entry.getName()));
			}
			return false;
		}
		
	}
	
}
