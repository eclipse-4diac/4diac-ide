/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typeeditor;

import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.MultiPageEditorPart;

public class TypeTextMultiPageEditorSite extends TypeMultiPageEditorSite {

	public TypeTextMultiPageEditorSite(final MultiPageEditorPart multiPageEditor, final IEditorPart editor) {
		super(multiPageEditor, editor);
	}

	@Override
	public IEditorActionBarContributor getActionBarContributor() {
		if (getMultiPageEditor().getEditorSite()
				.getActionBarContributor() instanceof final TypeEditorContributor typeEditorContributor) {
			return typeEditorContributor.getTextContributor();
		}
		return null;
	}
}
