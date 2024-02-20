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
 *   Michael Oberlehner -
 *   	initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search;

import java.util.Objects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class LiveSearchTypeEntry {

	private final TypeEntry typeEntry;

	public LiveSearchTypeEntry(final TypeEntry typeEntry) {
		this.typeEntry = Objects.requireNonNull(typeEntry);
	}

	/**
	 * this method searches the type, it checks whether it is currently openeed in
	 * an editor or if it is inside the file system If an editor is openened this
	 * method returns the type which is bounded to the editor
	 *
	 * @return
	 */
	public LibraryElement getLiveType() {

		final IEditorPart editor = getEditor();

		if (editor == null) {
			return typeEntry.getType();
		}

		final LibraryElement adapter = editor.getAdapter(LibraryElement.class);

		final EObject root = EcoreUtil.getRootContainer(adapter);

		if (root instanceof final LibraryElement libElement) {
			return libElement;
		}

		// this should never happen
		FordiacLogHelper
				.logError("It was not possible to find a type for the typeEntry: " + typeEntry.getFullTypeName()); //$NON-NLS-1$
		return null;

	}

	public void execute(final Command cmd) {
		final IEditorPart editor = getEditor();
		if (editor != null) {
			editor.getAdapter(CommandStack.class).execute(cmd);
		} else if (cmd.canExecute()) {
			cmd.execute();
		}

	}

	public void safe() {
		final IEditorPart editor = getEditor();
		if (editor != null) {
			editor.doSave(new NullProgressMonitor());
		} else {
			saveTypeEntry();
		}

	}

	private void saveTypeEntry() {
		try {
			typeEntry.save(getLiveType());
		} catch (final CoreException e) {
			FordiacLogHelper.logError("Saving TypeEntry " + typeEntry.getFullTypeName() + " failed", e); //$NON-NLS-1$//$NON-NLS-2$
		}
	}

	private IEditorPart getEditor() {

		final IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

		if (activeWorkbenchWindow == null) {
			return null;
		}

		return activeWorkbenchWindow.getActivePage().findEditor(new FileEditorInput(typeEntry.getFile()));
	}

}
