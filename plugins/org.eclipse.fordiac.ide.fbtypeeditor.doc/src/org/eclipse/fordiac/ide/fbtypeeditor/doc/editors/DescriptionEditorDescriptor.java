/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.doc.editors;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.typeeditor.ITypeEditorPage;
import org.eclipse.fordiac.ide.typeeditor.ITypeEditorPageDescriptor;

public class DescriptionEditorDescriptor implements ITypeEditorPageDescriptor {

	@Override
	public boolean handlesType(final LibraryElement type) {
		// we should be shown for all library elements
		return true;
	}

	@Override
	public ITypeEditorPage createEditorPage() {
		return new DescriptionEditor();
	}

}
