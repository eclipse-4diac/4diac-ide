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

public class AdapterTypeSelectionTreeContentProvider extends TypeSelectionTreeContentProvider {

	public static final AdapterTypeSelectionTreeContentProvider INSTANCE = new AdapterTypeSelectionTreeContentProvider();

	protected AdapterTypeSelectionTreeContentProvider() {
	}

	@Override
	protected List<TypeNode> createTree(final TypeLibrary typeLibrary) {
		if (typeLibrary.getAdapterTypes().isEmpty()) {
			return Collections.emptyList();
		}

		final TypeNode adapters = new TypeNode(Messages.DataTypeDropdown_Adapter_Types);
		addPathSubtree(adapters, typeLibrary.getAdapterTypes().values());
		adapters.sortChildren();

		return List.of(adapters);
	}
}
