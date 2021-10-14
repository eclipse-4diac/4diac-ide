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
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.editors;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.forms.editor.FormEditor;

/* extension of the form editor allowing to close an child editor by providing the editor instance
 *
 */
public abstract class AbstractCloseAbleFormEditor extends FormEditor {

	public boolean closeChildEditor(final IEditorPart childEditor) {

		final int pageIndex = pages.indexOf(childEditor);
		if (pageIndex != -1) {
			removePage(pageIndex);
			return true;
		}
		return closeInChildren(childEditor);
	}

	private boolean closeInChildren(final IEditorPart childEditor) {
		for (final Object child : pages) {
			if ((child instanceof AbstractCloseAbleFormEditor)
					&& (((AbstractCloseAbleFormEditor) child).closeChildEditor(childEditor))) {
				return true;
			}
		}
		return false;
	}
}
