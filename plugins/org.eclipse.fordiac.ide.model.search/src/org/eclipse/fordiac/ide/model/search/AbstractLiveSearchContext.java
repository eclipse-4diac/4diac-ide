/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.search;

import java.util.Objects;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public abstract class AbstractLiveSearchContext implements ISearchContext {

	private final TypeLibrary typelib;

	protected AbstractLiveSearchContext(final TypeLibrary typelib) {
		this.typelib = typelib;
	}

	protected AbstractLiveSearchContext(final IProject project) {
		this(Objects.requireNonNull(TypeLibraryManager.INSTANCE.getTypeLibrary(project)));
	}

	@Override
	public LibraryElement getLibraryElement(final URI uri) {
		final TypeEntry typeEntry = Objects.requireNonNull(TypeLibraryManager.INSTANCE.getTypeEntryForURI(uri));
		return getLiveType(typeEntry);
	}

	public static void executeAndSave(final Command cmd, final EObject modelObj, final IProgressMonitor pm) {
		final EObject rootContainer = EcoreUtil.getRootContainer(EcoreUtil.getRootContainer(modelObj));
		if (rootContainer instanceof final LibraryElement elem) {
			final TypeEntry entry = elem.getTypeEntry();
			final IEditorPart editor = getEditor(entry);
			execute(cmd, editor);
			save(entry, elem, editor, pm);
		}
	}

	public static void execute(final Command cmd, final TypeEntry typeEntry) {
		final IEditorPart editor = getEditor(typeEntry);
		execute(cmd, editor);

	}

	public static void save(final TypeEntry typeEntry) {
		final IEditorPart editor = getEditor(typeEntry);
		save(typeEntry, getLiveType(typeEntry), editor, new NullProgressMonitor());

	}

	protected TypeLibrary getTypelib() {
		return typelib;
	}

	/**
	 * this method searches the type, it checks whether it is currently openeed in
	 * an editor or if it is inside the file system If an editor is openened this
	 * method returns the type which is bounded to the editor
	 *
	 * @return
	 */
	private static LibraryElement getLiveType(final TypeEntry typeEntry) {

		final IEditorPart editor = getEditor(typeEntry);

		if (editor == null) {
			return typeEntry.getType();
		}

		final LibraryElement libElement = editor.getAdapter(LibraryElement.class);

		if (libElement != null) {
			return libElement;
		}

		// this should never happen
		FordiacLogHelper
				.logError("It was not possible to find a type for the typeEntry: " + typeEntry.getFullTypeName()); //$NON-NLS-1$
		return null;

	}

	private static IEditorPart getEditor(final TypeEntry typeEntry) {
		final IWorkbench workbench = PlatformUI.getWorkbench();

		return Display.getDefault().syncCall(() -> {
			final IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();

			if (activeWorkbenchWindow == null) {
				final IWorkbenchWindow[] workbenchWindows = workbench.getWorkbenchWindows();
				if (workbench.getWorkbenchWindows().length > 0 && workbenchWindows[0].getActivePage() != null) {
					final IWorkbenchPage activePage = workbenchWindows[0].getActivePage();
					for (final IEditorReference ref : activePage.getEditorReferences()) {
						try {
							final IEditorInput editorInput = ref.getEditorInput();
							if (editorInput instanceof final FileEditorInput fileInput
									&& fileInput.getFile().equals(typeEntry.getFile())) {
								return ref.getEditor(true);
							}
						} catch (final PartInitException e) {
							FordiacLogHelper.logWarning("Could not open Editor for type entry in search", e); //$NON-NLS-1$
						}
					}
				}

				return null;
			}

			return activeWorkbenchWindow.getActivePage().findEditor(new FileEditorInput(typeEntry.getFile()));
		});
	}

	private static void execute(final Command cmd, final IEditorPart editor) {
		if (editor != null) {
			// when an editor is open execute in display thread to protect any ui updates
			Display.getDefault().syncExec(() -> editor.getAdapter(CommandStack.class).execute(cmd));
		} else if (cmd.canExecute()) {
			cmd.execute();
		}
	}

	private static void save(final TypeEntry typeEntry, final LibraryElement libraryElement, final IEditorPart editor,
			final IProgressMonitor pm) {
		try {
			typeEntry.save(libraryElement, pm);
			if (editor != null) {
				// if we have an editor mark the save location in the command stack to tell the
				// editor that it is not dirty anymore
				Display.getDefault().syncExec(() -> editor.getAdapter(CommandStack.class).markSaveLocation());
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError("Saving TypeEntry " + typeEntry.getFullTypeName() + " failed", e); //$NON-NLS-1$//$NON-NLS-2$
		}
	}

}