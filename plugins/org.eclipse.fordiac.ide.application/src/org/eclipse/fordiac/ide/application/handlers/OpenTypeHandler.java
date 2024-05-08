/*******************************************************************************
 * Copyright (c) 2021, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

public class OpenTypeHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection sel = HandlerUtil.getCurrentStructuredSelection(event);
		final IFile typeFile = getSelectedTypeFile(sel);
		if (typeFile != null) {
			openTypeEditor(typeFile);
		}
		return Status.OK_STATUS;
	}

	private static void openTypeEditor(final IFile file) {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (null != workbench) {
			final IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
			if (null != activeWorkbenchWindow) {
				final IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
				final IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry()
						.getDefaultEditor(file.getName());
				try {
					activePage.openEditor(new FileEditorInput(file), desc.getId());
				} catch (final Exception e) {
					FordiacLogHelper.logError(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection sel = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		setBaseEnabled(getSelectedTypeFile(sel) != null);
	}

	private static IFile getSelectedTypeFile(final ISelection sel) {
		if ((sel instanceof final IStructuredSelection structSel) && !sel.isEmpty() && (structSel.size() == 1)) {
			Object obj = structSel.getFirstElement();
			if (obj instanceof final EditPart ep) {
				obj = ep.getModel();
			}

			final LibraryElement type = switch (obj) {
			case final FBNetworkElement fbnEl -> fbnEl.getType();
			case final IInterfaceElement ie -> ie.getType();
			default -> null;
			};

			if ((type != null) && (type.getTypeEntry() != null)) {
				return type.getTypeEntry().getFile();
			}
		}
		return null;
	}

}
