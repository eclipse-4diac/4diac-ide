/*******************************************************************************
 * Copyright (c) 2023, 2024 Johannes Kepler University
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
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.PlatformUI;

public class GlobalConstantsAttributeSection extends AttributeSection {
	@Override
	protected ConfigurableObject getInputType(final Object input) {
		if (input instanceof ITextSelection) {
			final var editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			if (editor != null && editor.getAdapter(LibraryElement.class) instanceof final GlobalConstants gc) {
				return gc;
			}
		}
		return null;
	}
}
