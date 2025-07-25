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
package org.eclipse.fordiac.ide.bulkeditor.editors;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.fordiac.ide.model.edit.AbstractTypeEntryAdapter;
import org.eclipse.fordiac.ide.model.edit.Messages;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IPartService;

public class BulkEditorTypeEntryAdapter extends AbstractTypeEntryAdapter {
	private final Set<String> changedFiles = new HashSet<>();

	public BulkEditorTypeEntryAdapter(final BulkEditor editor, final IPartService partService) {
		super(editor, partService);
	}

	@Override
	public void notifyChanged(final Notification notification) {
		super.notifyChanged(notification);

		String feature = ""; //$NON-NLS-1$
		if (notification.getFeature() instanceof final String string) {
			feature = string;
		}

		// make sure type was reloaded and not just loaded
		if ((feature.equals(TypeEntry.TYPE_ENTRY_TYPE_FEATURE)
				|| feature.equals(TypeEntry.TYPE_ENTRY_TYPE_EDITABLE_FEATURE))
				&& (notification.getOldValue() != null)) {
			if (notification.getNotifier() instanceof final TypeEntry tEntry) {
				changedFiles.add(tEntry.getFile().getFullPath().toOSString());
			}
			handleFileContentChange();
		}
	}

	@Override
	protected void reloadEditorType() {
		getEditor().reloadType();
		changedFiles.clear();
	}

	@Override
	protected BulkEditor getEditor() {
		return (BulkEditor) super.getEditor();
	}

	@Override
	protected int openFileChangedDialog() {
		final String message;
		final String title;
		if (changedFiles.size() == 1) {
			title = Messages.TypeEntryEditor_FileChangedTitle;
			message = MessageFormat.format(Messages.TypeEntryEditor_FileChanged_message, changedFiles.toArray()[0]);
		} else {
			title = Messages.TypeEntryEditor_FilesChangedTitle;
			message = MessageFormat.format(Messages.TypeEntryEditor_FilesChanged_message,
					String.join("\n", changedFiles)); //$NON-NLS-1$
		}

		final MessageDialog dialog = new MessageDialog(
				getEditor().getSite().getShell(), title, null, message, MessageDialog.QUESTION, new String[] {
						Messages.TypeEntryEditor_RedoSearch_label, Messages.TypeEntryEditor_dontreplace_button_label },
				0);

		return dialog.open();
	}
}
