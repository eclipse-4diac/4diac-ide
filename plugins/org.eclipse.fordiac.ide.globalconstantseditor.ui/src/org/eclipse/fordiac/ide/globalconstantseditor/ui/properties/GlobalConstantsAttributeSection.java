/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.globalconstantseditor.ui.properties;

import org.eclipse.fordiac.ide.gef.properties.AttributeSection;
import org.eclipse.fordiac.ide.globalconstantseditor.ui.document.GlobalConstantsDocument;
import org.eclipse.fordiac.ide.globalconstantseditor.ui.editor.GlobalConstantsEditor;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;

public class GlobalConstantsAttributeSection extends AttributeSection {
	@Override
	protected ConfigurableObject getInputType(final Object input) {
		if (input instanceof final GlobalConstantsEditor editor
				&& editor.getDocument() instanceof final GlobalConstantsDocument document) {
			return document.getResourceLibraryElement();
		}
		return null;
	}

	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		commandStack = getCommandStack(part, null);
		if (null == commandStack) {
			setInputCode();
		}
		setType(part);
		setInputInit();
	}
}
