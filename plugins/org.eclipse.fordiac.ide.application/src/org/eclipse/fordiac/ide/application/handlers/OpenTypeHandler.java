/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial implementation and/or initial documentation
 *   Alois Zoitl     - reworked to open the file of the type to not lead to
 *                     accidental file parsing
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.Optional;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.ILog;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;

public class OpenTypeHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection sel = HandlerUtil.getCurrentStructuredSelection(event);
		final IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindowChecked(event).getActivePage();

		getFileForSelectedObject(sel).ifPresent(file -> {
			try {
				IDE.openEditor(page, file);
			} catch (final PartInitException e) {
				ILog.get().error(e.getMessage(), e);
			}
		});
		return null;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection sel = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		setBaseEnabled(getFileForSelectedObject(sel).isPresent());
	}

	private static Optional<IFile> getFileForSelectedObject(final ISelection sel) {
		if (!(sel instanceof final IStructuredSelection structSel) || (structSel.size() != 1)) {
			return Optional.empty();
		}
		Object obj = structSel.getFirstElement();

		if (obj instanceof final EditPart ep) {
			obj = ep.getModel();
		}

		final Optional<TypeEntry> entry = switch (obj) {
		case final FBNetwork fbn -> getTypeEntryForNetwork(fbn);
		case final FBNetworkElement fbnEl -> Optional.ofNullable(fbnEl.getTypeEntry());
		case final IInterfaceElement ie -> Optional.ofNullable(ie.getType().getTypeEntry());
		default -> Optional.empty();
		};
		return entry.map(TypeEntry::getFile);
	}

	private static Optional<TypeEntry> getTypeEntryForNetwork(final FBNetwork fbn) {
		return switch (fbn.eContainer()) {
		case final UntypedSubApp usa -> findEnclosingTypedSubAppTypeEntry(usa);
		case final TypedSubApp tsa -> Optional.ofNullable(tsa.getTypeEntry());
		case final CFBInstance cfb -> Optional.ofNullable(cfb.getTypeEntry());
		case null, default -> Optional.empty();
		};
	}

	private static Optional<TypeEntry> findEnclosingTypedSubAppTypeEntry(final EObject start) {
		for (EObject element = start.eContainer(); element != null; element = element.eContainer()) {
			if (element instanceof final TypedSubApp tsa) {
				return Optional.ofNullable(tsa.getTypeEntry());
			}
		}
		return Optional.empty();
	}

}
