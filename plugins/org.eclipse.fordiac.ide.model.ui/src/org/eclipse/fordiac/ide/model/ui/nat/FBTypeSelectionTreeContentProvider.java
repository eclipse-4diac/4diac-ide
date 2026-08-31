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
import java.util.function.Predicate;

import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.Messages;

public class FBTypeSelectionTreeContentProvider extends TypeSelectionTreeContentProvider {

	public static final FBTypeSelectionTreeContentProvider INSTANCE = new FBTypeSelectionTreeContentProvider();

	private final Predicate<FBTypeEntry> filter;

	protected FBTypeSelectionTreeContentProvider() {
		this(_ -> true);
	}

	public FBTypeSelectionTreeContentProvider(final Predicate<FBTypeEntry> filter) {
		this.filter = filter;
	}

	@Override
	protected List<TypeNode> createTree(final TypeLibrary typeLibrary) {
		if (typeLibrary.getFbTypes().findAny().isEmpty()) {
			return Collections.emptyList();
		}

		final TypeNode fbTypes = new TypeNode(Messages.DataTypeDropdown_FB_Types);
		addPathSubtree(fbTypes, typeLibrary.getFbTypes().filter(filter));
		fbTypes.sortChildren();

		return fbTypes.getChildren();
	}

	@Override
	public String getTitle() {
		return Messages.DataTypeDropdown_FB_Types;
	}
}
