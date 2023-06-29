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
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.nat;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.Messages;

public class AdapterSelectionTreeContentProvider extends TypeSelectionTreeContentProvider {

	@Override
	protected Object[] createTree(final HashMap<String, List<String>> inputElement) {
		final TypeNode adapters = new TypeNode(Messages.DataTypeDropdown_Adapter_Types);

		final TypeLibrary typeLib = TypeLibraryManager.INSTANCE.getTypeLibrary(getCurrentProject());
		final List<AdapterTypeEntry> adapterTypes = typeLib.getAdapterTypesSorted();

		inputElement.forEach((key, listValue) -> {
			listValue.forEach(typeName -> {
				final Optional<AdapterTypeEntry> type = adapterTypes.stream()
						.filter(dataType -> dataType.getTypeName().equals(typeName)).findFirst();
				if (type.isPresent()) {
					final TypeNode newNode = new TypeNode(type.get().getTypeName(), type.get().getType());
					adapters.addChild(newNode);
				}
			});
		});

		if (adapters.getChildren().isEmpty()) {
			return adapters.getChildren().toArray();
		}

		return new TypeNode[] { adapters };
	}
}
