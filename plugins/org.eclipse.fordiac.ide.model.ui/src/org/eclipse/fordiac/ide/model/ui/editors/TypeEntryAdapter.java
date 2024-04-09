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
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.editors;

import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.ui.Messages;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.FileEditorInput;

public class TypeEntryAdapter extends AdapterImpl {

	private final ITypeEntryEditor editor;
	private boolean reloadPending;

	public TypeEntryAdapter(final ITypeEntryEditor editor) {
		this.editor = editor;
	}

	@Override
	public void notifyChanged(final Notification notification) {
		super.notifyChanged(notification);

		String feature = "";
		if (notification.getFeature() instanceof final String string) {
			feature = string;
		}

		switch (feature) {
		case TypeEntry.TYPE_ENTRY_FILE_CONTENT_FEATURE:
			handleFileContentChange();
			break;
		case TypeEntry.TYPE_ENTRY_FILE_FEATURE:
			Display.getDefault().asyncExec(() -> {
				if (notification.getNewValue() instanceof final IFile newFile) {
					editor.setInput(new FileEditorInput(newFile));
				}
			});
			break;
		case TypeEntry.TYPE_ENTRY_EDITOR_INSTANCE_UPDATE_FEATURE:
			// if there is no typeEntry inside, then the notification is used wrong, and for
			// that we want to know that early
			Assert.isTrue(notification.getNotifier() instanceof TypeEntry);
			Assert.isTrue(notification.getNewValue() instanceof TypeEntry);
			final TypeEntry typeEntry = (TypeEntry) notification.getNewValue();
			editor.updateInstances(typeEntry);
			break;
		}
	}

	public void checkFileReload() {
		if (reloadPending) {
			performReload();
		}
	}

	private void handleFileContentChange() {
		if (editor.equals(editor.getSite().getPage().getActiveEditor())) {
			performReload();
		} else {
			reloadPending = true;
		}

	}

	private void performReload() {
		Display.getDefault().asyncExec(() -> {
			if (!editor.isDirty() || openFileChangedDialog() == 0) {
				editor.reloadType();
			}
		});
		reloadPending = false;
	}

	private int openFileChangedDialog() {
		final String message = MessageFormat.format(Messages.TypeEntryEditor_filedchanged_message,
				editor.getEditorInput().getName());
		final MessageDialog dialog = new MessageDialog(editor.getSite().getShell(),
				Messages.TypeEntryEditor_FileChangedTitle, null, message, MessageDialog.QUESTION,
				new String[] { Messages.TypeEntryEditor_replace_button_label,
						Messages.TypeEntryEditor_dontreplace_button_label },
				0);

		return dialog.open();
	}
}