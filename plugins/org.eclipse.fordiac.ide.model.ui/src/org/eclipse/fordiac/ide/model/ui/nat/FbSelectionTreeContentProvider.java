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
 *   Prankur Agarwal - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.nat;

import java.util.HashMap;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.Messages;

public class FbSelectionTreeContentProvider extends TypeSelectionTreeContentProvider {

	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof HashMap<?, ?>) {
			final HashMap<String, List<String>> map = (HashMap<String, List<String>>) inputElement;
			return createTree(map);
		}
		return new Object[0];
	}

	@Override
	protected Object[] createTree(final HashMap<String, List<String>> inputElement) {
		final TypeNode fbs = new TypeNode(Messages.DataTypeDropdown_FB_Types);
		if (inputElement.containsKey(Messages.DataTypeDropdown_FB_Types)) {
			inputElement.get(Messages.DataTypeDropdown_FB_Types).forEach(typeName -> {
				final FBTypeEntry type = getTypeEntryForName(
						TypeLibraryManager.INSTANCE.getTypeLibrary(getCurrentProject()), typeName);

				if (type != null) {
					if (null != type.getType().getTypeEntry()) {
						final IPath parentPath = type.getType().getTypeEntry().getFile().getParent()
								.getProjectRelativePath();
						createSubdirectories(fbs, type.getType(), parentPath);
					} else {
						final TypeNode runtimeNode = new TypeNode(type.getTypeName(), type.getType());
						runtimeNode.setParent(fbs);
						fbs.addChild(runtimeNode);
					}
				}
			});

			if (!fbs.getChildren().isEmpty()) {
				return fbs.getChildren().toArray();
			}
		}

		return new TypeNode[0];
	}

	protected static FBTypeEntry getTypeEntryForName(final TypeLibrary typeLibrary, final String typeName) {
		return typeLibrary.getFbTypes().values().stream().filter(type -> type.getTypeName().equals(typeName))
				.findFirst().orElse(null);
	}
}