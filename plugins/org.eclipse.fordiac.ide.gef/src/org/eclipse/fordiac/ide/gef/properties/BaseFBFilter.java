/*******************************************************************************
 * Copyright (c) 2022, 2025 Primetals Technologies Austria GmbH
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
		final Object retval = switch (element) {
		case final TextSelection textSel -> getTypeFromActiveEditor();
		case final EditPart ep -> ep.getModel();
		case final ECC ecc -> ecc.getBasicFBType();
		default -> element;
		};

		return (retval instanceof final BaseFBType b) ? b : null;
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
