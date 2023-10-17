/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.nat;

import java.util.List;

import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.Messages;

public class StructuredTypeSelectionTreeContentProvider extends TypeSelectionTreeContentProvider {

	public static final StructuredTypeSelectionTreeContentProvider INSTANCE = new StructuredTypeSelectionTreeContentProvider();

	protected StructuredTypeSelectionTreeContentProvider() {
	}

	@Override
	protected List<TypeNode> createTree(final TypeLibrary typeLibrary) {
		final TypeNode structuredTypes = new TypeNode(Messages.DataTypeDropdown_STRUCT_Types);
		addPathSubtree(structuredTypes, typeLibrary.getDataTypeLibrary().getDerivedDataTypes());
		structuredTypes.sortChildren();

		return structuredTypes.getChildren();
	}

	@Override
	public String getTitle() {
		return Messages.DataTypeDropdown_STRUCT_Types;
	}

}
