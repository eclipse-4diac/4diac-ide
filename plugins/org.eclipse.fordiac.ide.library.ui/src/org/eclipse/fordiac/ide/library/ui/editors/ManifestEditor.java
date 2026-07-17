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
 *   Mario Kastner
 *   	- redesign of manifest editor
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.editor.FormEditor;

public class ManifestEditor extends FormEditor {
	private TextEditor textEditor;
	private ManifestEditorDependencyPage dependencyPage;
	// TODO add product editor page

	private boolean isDirty;

	private static final String DEPENDENCY_PAGE_ID = "fordiac.ide.library.ui.editors.manifestEditorDependencyPage"; //$NON-NLS-1$

	@Override
	protected void addPages() {
		textEditor = new TextEditor();
		dependencyPage = new ManifestEditorDependencyPage(this, DEPENDENCY_PAGE_ID, "Update Dependencies"); //$NON-NLS-1$

		try {
			int index = addPage(textEditor, getEditorInput());
			setPageText(index, textEditor.getTitle());
			setPageImage(index, textEditor.getTitleImage());

			index = addPage(dependencyPage);
			setPageText(index, dependencyPage.getTitle());
			setPageImage(index, dependencyPage.getTitleImage());

			isDirty = false;
		} catch (final PartInitException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		dependencyPage.doSave(monitor);
		setDirty(false);
	}

	public void setDirty(final boolean dirty) {
		if (this.isDirty != dirty) {
			this.isDirty = dirty;
			firePropertyChange(PROP_DIRTY);
		}
	}

	@Override
	public boolean isDirty() {
		return isDirty;
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
