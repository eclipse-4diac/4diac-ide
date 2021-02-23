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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.helpers;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.ui.IEditorInput;

public class FBNetworkElementHelper {

	public static boolean isContainedInTypedInstance(final FBNetworkElement element) {
		switch (EditorUtils.getEditorKind()) {
		case TYPE_EDITOR:
			return handleTypeEditor(element);
		case AUTOMATION_SYSTEM_EDITOR:
			return handleAutomationSystemEditor(element);
		default:
			return false;

		}
	}

	private static boolean handleAutomationSystemEditor(final FBNetworkElement element) {
		return isInSubAppType(element);
	}

	private static boolean isInSubAppType(final FBNetworkElement element) {
		EObject obj = element;
		while (obj.eContainer() != null) {
			obj = obj.eContainer();
			if (obj instanceof CompositeFBType) {
				return true;
			}
		}
		return false;
	}

	private static boolean handleTypeEditor(final FBNetworkElement element) {

		final IEditorInput editorInput = EditorUtils.getCurrentActiveEditor().getEditorInput();
		final EObject rootContainer = EcoreUtil.getRootContainer(element);

		if (rootContainer instanceof CompositeFBType) {
			final IFile typeFile = editorInput.getAdapter(IFile.class);
			final PaletteEntry paletteEntryForFile = TypeLibrary.getPaletteEntryForFile(typeFile);
			final LibraryElement type = paletteEntryForFile.getType();
			if (rootContainer.equals(type)) {
				return false;
			}
		}

		return isInSubAppType(element);
	}

}
