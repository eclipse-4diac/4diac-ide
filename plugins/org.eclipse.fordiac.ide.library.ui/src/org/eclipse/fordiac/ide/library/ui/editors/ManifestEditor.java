/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner
 *   	- initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.editor.FormEditor;

public class ManifestEditor extends FormEditor {
	private TextEditor textEditor;

	@Override
	protected void addPages() {
		textEditor = new TextEditor();
		final ManifestUpdateFormPage updatePage = new ManifestUpdateFormPage(this, "updateFormPage", //$NON-NLS-1$
				"Update Dependencies"); //$NON-NLS-1$

		try {
			final int index = addPage(textEditor, getEditorInput());
			setPageText(index, textEditor.getTitle());
			setPageImage(index, textEditor.getTitleImage());

			addPage(updatePage);

		} catch (final PartInitException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		textEditor.doSave(monitor);
	}

	@Override
	public void doSaveAs() {
		// do nothing
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}
}
