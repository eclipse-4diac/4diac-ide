/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.fbtypeeditor.editors;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.TextSelectionNavigationLocation;

public class FBTypeXtextNavigationLocation extends TextSelectionNavigationLocation {

	private final String editorId;

	public FBTypeXtextNavigationLocation(final ITextEditor part, final boolean initialize) {
		super(part, initialize);
		this.editorId = part.getEditorSite().getId();
	}

	@Override
	public IEditorInput getInput() {
		return (IEditorInput) super.getInput();
	}

	@Override
	protected IEditorPart getEditorPart() {
		final IEditorPart editorPart = super.getEditorPart();
		if (editorPart instanceof MultiPageEditorPart) {
			for (final IEditorPart editor : ((MultiPageEditorPart) editorPart).findEditors(getInput())) {
				if (editorId.equals(editor.getEditorSite().getId())) {
					return editor;
				}
			}
		}
		return editorPart;
	}
}
