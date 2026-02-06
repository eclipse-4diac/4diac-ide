/*******************************************************************************
 * Copyright (c) 2025, 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.ui.editors;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.model.ui.Messages;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class MultiLibraryElementActivationListener extends LibraryElementActivationListener {

	private final Set<IEditorInput> editorInputs;

	public MultiLibraryElementActivationListener(final IEditorPart editorPart, final Set<IEditorInput> editorInputs) {
		super(editorPart);
		this.editorInputs = editorInputs;
	}

	@Override
	protected void checkEditorActivated() {
		final List<IEditorInput> changed = editorInputs.stream()
				.filter(Predicate.not(LibraryElementProvider.INSTANCE::isSynchronized))
				.sorted(Comparator.comparing(IEditorInput::getToolTipText)).toList();
		if (changed.size() == 1) {
			handleEditorInputChanged(changed.getFirst());
		} else if (!changed.isEmpty()) {
			handleEditorInputsChanged(changed);
		}
	}

	protected void handleEditorInputsChanged(final List<IEditorInput> editorInputs) {
		final ListSelectionDialog replaceContentDialog = ListSelectionDialog.of(editorInputs)
				.title(Messages.LibraryElementActivationListener_FileChangedTitle)
				.message(Messages.MultiLibraryElementActivationListener_FilesChangedMessage)
				.okButtonText(Messages.MultiLibraryElementActivationListener_ReplaceContentButton)
				.okButtonTextWhenNoSelection(Messages.LibraryElementActivationListener_IgnoreChangeButton)
				.labelProvider(new WorkbenchLabelProvider()).preselect(editorInputs.toArray()).canCancel(false)
				.asSheet(true).create(getEditorPart().getSite().getShell());
		if (replaceContentDialog.open() != IDialogConstants.OK_ID) {
			return;
		}

		final MultiStatus status = new MultiStatus(getClass(), IStatus.OK,
				Messages.MultiLibraryElementActivationListener_SyncErrorMessage);
		final List<IEditorInput> selected = Stream.of(replaceContentDialog.getResult())
				.filter(IEditorInput.class::isInstance).map(IEditorInput.class::cast).toList();
		for (final IEditorInput editorInput : selected) {
			try {
				LibraryElementProvider.INSTANCE.synchronize(editorInput, new NullProgressMonitor());
			} catch (final CoreException e) {
				status.add(e.getStatus());
			}
		}
		if (!status.isOK()) {
			ErrorDialog.openError(getEditorPart().getSite().getShell(),
					Messages.LibraryElementActivationListener_SyncErrorTitle,
					Messages.MultiLibraryElementActivationListener_SyncErrorMessage, status);
		}
	}
}
