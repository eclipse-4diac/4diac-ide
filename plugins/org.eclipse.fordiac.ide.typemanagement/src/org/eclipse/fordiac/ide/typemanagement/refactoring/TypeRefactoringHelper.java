/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.typemanagement.refactoring.edit.DataTypeEditBuilder;

public final class TypeRefactoringHelper {

	public static void addModelEditsForType(final List<ModelEdit<?>> modelEdits, final TypeEntry typeEntry,
			final IPath newPath) {
		if (typeEntry instanceof final DataTypeEntry dtEntry) {
			DataTypeEditBuilder.createStructuredDataTypeChanges(dtEntry, modelEdits,
					DataTypeEditBuilder.getFullTypeName(newPath));
		} else {
			addInstanceChanges(modelEdits, typeEntry);
		}
	}

	private static void addInstanceChanges(final List<ModelEdit<?>> modelEdits, final TypeEntry typeEntry) {
		final List<? extends EObject> result = new BlockTypeInstanceSearch(typeEntry).performSearch();

		for (final EObject eObject : result) {
			if (eObject instanceof final BlockFBNetworkElement elem) {
				modelEdits.add(new UpdateFBTypeModelEdit(elem, typeEntry));
			}
		}
	}

	private TypeRefactoringHelper() {
		throw new UnsupportedOperationException();
	}
}
