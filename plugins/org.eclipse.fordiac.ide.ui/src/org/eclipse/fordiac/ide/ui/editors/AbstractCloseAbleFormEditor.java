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

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Item;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.part.MultiPageEditorPart;

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
			if (child instanceof final AbstractCloseAbleFormEditor closeableFormEditor
					&& closeableFormEditor.closeChildEditor(childEditor)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This is a re-implementation of {@link MultiPageEditorPart#getEditor(int)}.
	 * The original method does not check if the item is already disposed. This can
	 * lead to a problem if any of the listeners is called and asked for the editor
	 * during the dispose process.
	 */
	@Override
	protected IEditorPart getEditor(final int pageIndex) {
		final Item item = ((CTabFolder) getContainer()).getItem(pageIndex); // getItem in MultiPageEditorPart is private
																			// so we have to do this
		if (item != null && !item.isDisposed() && item.getData() instanceof final IEditorPart ep) {
			return ep;
		}
		return null;
	}

	@Override
	protected void pageChange(final int newPageIndex) {
		super.pageChange(newPageIndex);
		// notify the part service that we have a new page activated. This is needed
		// that for example link with editor
		// gets notified.
		final EPartService partService = getSite().getService(EPartService.class);
		final MPart activePart = partService.getActivePart();
		partService.activate(null);
		partService.activate(activePart);
	}
}
