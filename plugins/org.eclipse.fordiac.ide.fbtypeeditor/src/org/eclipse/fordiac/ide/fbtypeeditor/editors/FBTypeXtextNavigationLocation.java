/*******************************************************************************
 * Copyright (c) 2023, 2024 Martin Erich Jobst
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

import java.util.Objects;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.part.MultiPageEditorSite;
import org.eclipse.ui.texteditor.TextSelectionNavigationLocation;

public class FBTypeXtextNavigationLocation extends TextSelectionNavigationLocation {

	private final IWorkbenchPage page;
	private final IEditorInput multiPageEditorInput;
	private final String multiPageEditorId;

	public FBTypeXtextNavigationLocation(final FBTypeXtextEditor part, final boolean initialize) {
		super(part, initialize);
		page = part.getSite().getPage();
		if (part.getEditorSite() instanceof final MultiPageEditorSite multiPageEditorSite) {
			final MultiPageEditorPart multiPageEditor = multiPageEditorSite.getMultiPageEditor();
			multiPageEditorInput = multiPageEditor.getEditorInput();
			multiPageEditorId = multiPageEditor.getEditorSite().getId();
		} else {
			multiPageEditorInput = null;
			multiPageEditorId = null;
		}
	}

	@Override
	public IEditorInput getInput() {
		return (IEditorInput) super.getInput();
	}

	@Override
	protected IEditorPart getEditorPart() {
		final IEditorPart editorPart = super.getEditorPart();
		if (editorPart == null && getMultiPageEditorPart() instanceof final MultiPageEditorPart multiPageEditorPart) {
			for (final IEditorPart subEditor : multiPageEditorPart.findEditors(getInput())) {
				if (Objects.equals(subEditor.getEditorSite().getId(), getId())) {
					return subEditor;
				}
			}
		}
		return editorPart;
	}

	protected IEditorPart getMultiPageEditorPart() {
		final IEditorReference[] editorReferences = page.findEditors(multiPageEditorInput, multiPageEditorId,
				(multiPageEditorInput != null ? IWorkbenchPage.MATCH_INPUT : 0)
						| (multiPageEditorId != null ? IWorkbenchPage.MATCH_INPUT : 0));
		if (editorReferences.length > 0) {
			return editorReferences[0].getEditor(false);
		}
		return null;
	}
}
