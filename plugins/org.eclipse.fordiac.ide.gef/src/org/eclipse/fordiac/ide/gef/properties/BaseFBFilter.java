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
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class BaseFBFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		return getFBTypeFromSelectedElement(toTest) != null;
	}

	static BaseFBType getFBTypeFromSelectedElement(final Object element) {
		Object retval = element;
		if (element instanceof TextSelection) {
			retval = getTypeFromActiveEditor();
		}
		if (retval instanceof EditPart) {
			retval = ((EditPart) retval).getModel();
		}
		if (retval instanceof ECC) {
			return ((ECC) retval).getBasicFBType();
		}
		return (retval instanceof BaseFBType) ? (BaseFBType) retval : null;
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
