/*******************************************************************************
 * Copyright (c) 2023, 2026 Martin Erich Jobst
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Sebastian Hollersbacher - Changed to use all FB-Types
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.nat;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.Messages;

public class FBSelectionTreeContentProvider extends TypeSelectionTreeContentProvider {

	public static final FBSelectionTreeContentProvider INSTANCE = new FBSelectionTreeContentProvider();

	protected FBSelectionTreeContentProvider() {
	}

	@Override
	protected List<TypeNode> createTree(final TypeLibrary typeLibrary) {
		if (typeLibrary.getFbTypes().isEmpty() && typeLibrary.getSubAppTypes().isEmpty()) {
			return Collections.emptyList();
		}

		final TypeNode fbTypes = new TypeNode(Messages.DataTypeDropdown_FB_Types);
		addPathSubtree(fbTypes,
				Stream.concat(typeLibrary.getFbTypes().stream(), typeLibrary.getSubAppTypes().stream()).toList());
		fbTypes.sortChildren();

		return fbTypes.getChildren();
	}

	@Override
	public String getTitle() {
		return Messages.DataTypeDropdown_FB_Types;
	}
}
