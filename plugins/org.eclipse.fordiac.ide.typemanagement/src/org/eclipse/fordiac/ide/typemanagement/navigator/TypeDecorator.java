/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.typemanagement.navigator;

import java.util.Objects;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.ui.FordiacStringUtils;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class TypeDecorator implements ILightweightLabelDecorator {

	@Override
	public void addListener(final ILabelProviderListener listener) {
		// currently nothing to do here
	}

	@Override
	public void dispose() {
		// currently nothing to do here
	}

	@Override
	public boolean isLabelProperty(final Object element, final String property) {
		return false;
	}

	@Override
	public void removeListener(final ILabelProviderListener listener) {
		// currently nothing to do here

	}

	@Override
	public void decorate(final Object element, final IDecoration decoration) {
		if (element instanceof final IFile file) {
			final String comment = getComment(file);
			if (comment != null) {
				decoration.addSuffix(" [" + FordiacStringUtils.getShortComment(comment) + "]"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}

	private static String getComment(final IFile file) {
		final TypeEntry entry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
		if (entry != null) {
			// try to load comment from editor
			final String editorComment = Display.getDefault().syncCall(() -> {
				final IEditorPart editor = findEditor(file);
				if (editor != null) {
					final LibraryElement libraryElement = editor.getAdapter(LibraryElement.class);
					if (libraryElement != null) {
						return libraryElement.getComment();
					}
				}
				return null;
			});
			// fall back to (cached) comment from type entry
			return Objects.requireNonNullElse(editorComment, entry.getComment());
		}
		return null;
	}

	private static IEditorPart findEditor(final IFile file) {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null) {
			return null;
		}
		final IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null) {
			return null;
		}
		final IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
		if (activePage == null) {
			return null;
		}
		return activePage.findEditor(new FileEditorInput(file));
	}
}
