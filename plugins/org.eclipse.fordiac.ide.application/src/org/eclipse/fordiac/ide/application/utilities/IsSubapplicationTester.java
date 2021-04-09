/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Lukas Wais - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.utilities;

import java.util.List;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.EditPart;
import org.eclipse.ui.IEditorPart;

public class IsSubapplicationTester extends PropertyTester {

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		final IEditorPart editor = EditorUtils.getCurrentActiveEditor();

		if (null != editor) {
			final FBNetwork fbnetwork = editor.getAdapter(FBNetwork.class);
			return ((null != fbnetwork) && fbnetwork.isSubApplicationNetwork()) || isSelectionMovable(receiver);
		}
		return false;
	}

	private static boolean isSelectionMovable(final Object receiver) {
		if (receiver instanceof List) {
			final List<?> selection = (List<?>) receiver;
			return !selection.isEmpty() && selection.stream().allMatch(IsSubapplicationTester::isNestedInSubApp);
		}
		return false;
	}

	private static boolean isNestedInSubApp(final Object obj) {
		if (obj instanceof EditPart) {
			final Object model = ((EditPart) obj).getModel();
			if (model instanceof FBNetworkElement) {
				return ((FBNetworkElement) model).isNestedInSubApp();
			}
		}
		return false;}


}

