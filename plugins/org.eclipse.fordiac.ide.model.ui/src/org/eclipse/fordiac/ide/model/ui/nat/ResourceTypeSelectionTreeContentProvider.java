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

import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.Messages;

public class ResourceTypeSelectionTreeContentProvider extends TypeSelectionTreeContentProvider {

	public static final ResourceTypeSelectionTreeContentProvider INSTANCE = new ResourceTypeSelectionTreeContentProvider();

	protected ResourceTypeSelectionTreeContentProvider() {
	}

	@Override
	protected List<TypeNode> createTree(final TypeLibrary typeLibrary) {
		if (typeLibrary.getResourceTypes().isEmpty()) {
			return Collections.emptyList();
		}

		final TypeNode resourceTypes = new TypeNode(Messages.ResourceTypeSelectionTreeContentProvider_ResourceTypes);
		addPathSubtree(resourceTypes, typeLibrary.getResourceTypes().values());
		resourceTypes.sortChildren();

		return resourceTypes.getChildren();
	}

	@Override
	public String getTitle() {
		return Messages.ResourceTypeSelectionTreeContentProvider_ResourceTypes;
	}
}
