/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.edit;

import java.text.MessageFormat;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;

public abstract class AbstractTypeEntryAdapter extends AdapterImpl {
	private boolean reloadPending;

	protected abstract void reloadEditorType();

	protected abstract IEditorPart getEditor();

	public void checkFileReload() {
		if (reloadPending) {
			performReload();
		}
	}

	protected void handleFileContentChange() {
		if (getEditor().equals(getEditor().getSite().getPage().getActiveEditor())) {
			performReload();
		} else {
			reloadPending = true;
		}
	}

	protected boolean editorClosed() {
		return getEditor().getSite().getPage() == null;
	}

	private void performReload() {
		Display.getDefault().asyncExec(() -> {
			if (!editorClosed() && (!getEditor().isDirty() || openFileChangedDialog() == 0)) {
				reloadEditorType();
			}
		});
		reloadPending = false;
	}

	protected int openFileChangedDialog() {
		final String message = MessageFormat.format(Messages.TypeEntryEditor_FileChanged_message,
				getEditor().getEditorInput().getName());
		final MessageDialog dialog = new MessageDialog(getEditor().getSite().getShell(),
				Messages.TypeEntryEditor_FileChangedTitle, null, message, MessageDialog.QUESTION,
				new String[] { Messages.TypeEntryEditor_replace_button_label,
						Messages.TypeEntryEditor_dontreplace_button_label },
				0);

		return dialog.open();
	}
}
