/*******************************************************************************
 * Copyright (c) 2021, 2022 Primetals Technologies Austria GmbH
 *               2022 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians - initial API and implementation and/or initial documentation
 * 			     - update XMI exporter for FUNCTION and new STAlgorithm grammar
 *   Martin Jobst
 *     - refactor export
 *     - add comments export
 *   Fabio Gandolfi
 *     - converted to java class
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.ExportAsXMI;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class ExportXMIHandler extends AbstractHandler {
	static final String XMI_EXTENSION = "xmi"; // $NON-NLS-1$

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection)HandlerUtil.getCurrentSelection(event);
		final ExportAsXMI exporter = new ExportAsXMI();
		return exporter.export((IFile) selection.getFirstElement());
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection selection = (ISelection)HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection != null && !selection.isEmpty() && selection instanceof IStructuredSelection) {
			final IStructuredSelection structuredSelection = (IStructuredSelection)selection;
			if (structuredSelection.size() == 1 && (structuredSelection.getFirstElement() instanceof IFile)) {
				final IFile fbFile = (IFile)structuredSelection.getFirstElement();
				if (fbFile.getFullPath().getFileExtension().equals(TypeLibraryTags.FC_TYPE_FILE_ENDING)) {
					setBaseEnabled(true);
					return;
				}
				final TypeEntry entry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(fbFile);
				if (null != entry) {
					setBaseEnabled(entry.getType() instanceof SimpleFBType);
					return;
				}
			}
		}
		setBaseEnabled(false);
	}

}
