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

import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.Messages;

public class DataTypeSelectionTreeContentProvider extends TypeSelectionTreeContentProvider {

	public static final DataTypeSelectionTreeContentProvider INSTANCE = new DataTypeSelectionTreeContentProvider();

	protected DataTypeSelectionTreeContentProvider() {
	}

	@Override
	protected List<TypeNode> createTree(final TypeLibrary typeLibrary) {
		final TypeNode elementaryTypes = new TypeNode(Messages.DataTypeDropdown_Elementary_Types);
		ElementaryTypes.getAllElementaryType().stream().map(TypeNode::new).forEachOrdered(elementaryTypes::addChild);
		GenericTypes.getAllGenericTypes().stream().map(TypeNode::new).forEachOrdered(elementaryTypes::addChild);
		elementaryTypes.sortChildren();

		final TypeNode structuredTypes = new TypeNode(Messages.DataTypeDropdown_STRUCT_Types);
		addPathSubtree(structuredTypes, typeLibrary.getDataTypeLibrary().getDerivedDataTypes());
		structuredTypes.sortChildren();

		return List.of(elementaryTypes, structuredTypes);
	}
}
