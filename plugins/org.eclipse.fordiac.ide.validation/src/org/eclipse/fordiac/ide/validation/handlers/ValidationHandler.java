/*******************************************************************************
 * Copyright (c) 2020 Sandor Bacsi
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sandor Bacsi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.validation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class ValidationHandler extends AbstractHandler {
	@Override
	public Object execute(final ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException {
		final INamedElement selectedElement = getSelectedElement(
				(StructuredSelection) HandlerUtil.getCurrentSelection(event));
		ValidationHelper.validate(selectedElement);
		return null;
	}

	private static INamedElement getSelectedElement(final StructuredSelection currentSelection) {
		final Object obj = currentSelection.getFirstElement();
		if (obj instanceof final IFile file) {
			return checkSelectedFile(file);
		}
		return (obj instanceof final INamedElement ne) ? ne : null;
	}

	private static INamedElement checkSelectedFile(final IFile file) {
		final TypeEntry entry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
		return (entry instanceof final FBTypeEntry typeEntry) ? typeEntry.getType() : null;
	}
}