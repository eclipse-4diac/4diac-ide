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
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.utilities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.EditPart;
import org.eclipse.ui.IEditorPart;

public class IsNoReadOnlyEditorTester extends PropertyTester {

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {

		final List<Object> selection = getList(receiver);

		for (final Object selectionObject : selection) {
			if (!(selectionObject instanceof final EditPart ep) || (ep.getRoot() == null)) {
				return false;
			}
			final IEditorPart editor = EditorUtils.getCurrentActiveEditor();
			if (editor.getAdapter(FBNetwork.class) == null) {
				return false;
			}
		}
		return true;
	}

	private static List<Object> getList(final Object reciever) {
		if (reciever instanceof final List<?> list) {
			return Collections.unmodifiableList(list);
		}
		return Arrays.asList(reciever);
	}

}
