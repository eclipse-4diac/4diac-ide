/*******************************************************************************
 * Copyright (c) 2025 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Stemmer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.nat;

import java.util.List;

import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.Messages;

public class AttributeSelectionTreeContentProvider extends TypeSelectionTreeContentProvider {

	public static final AttributeSelectionTreeContentProvider INSTANCE = new AttributeSelectionTreeContentProvider();

	@Override
	protected List<TypeNode> createTree(final TypeLibrary typeLibrary) {
		final TypeNode attributeTypes = new TypeNode(Messages.AttributeTypeDropdown_Attribute_Types);
		addPathSubtree(attributeTypes, typeLibrary.getAttributeTypes());
		attributeTypes.sortChildren();

		return List.of(attributeTypes);
	}

}
