/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class FBTypePropertiesFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		final Object retval = getFBTypeFromSelectedElement(toTest);
		return ((retval != null)) && !(retval instanceof SubAppType);
	}

	static FBType getFBTypeFromSelectedElement(final Object element) {
		Object retval = element;
		if (element instanceof TextSelection) {
			retval = getTypeFromActiveEditor();

		}

		if (retval instanceof EditPart) {
			retval = ((EditPart) retval).getModel();
		}

		if ((retval instanceof FBNetwork) && (((FBNetwork) retval).eContainer() instanceof FBType)) {
			retval = ((FBNetwork) retval).eContainer();
		}

		return (retval instanceof FBType) ? (FBType) retval : null;
	}

	private static Object getTypeFromActiveEditor() {
		final IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		if (activeEditor != null) {
			return activeEditor.getAdapter(FBType.class);
		}
		return null;
	}

}
