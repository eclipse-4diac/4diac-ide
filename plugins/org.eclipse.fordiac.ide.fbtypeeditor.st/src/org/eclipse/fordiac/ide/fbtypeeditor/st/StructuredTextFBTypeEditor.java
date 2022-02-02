/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.fbtypeeditor.st;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtext.ui.editor.XtextEditor;

public class StructuredTextFBTypeEditor extends XtextEditor implements IFBTEditorPart {

	public StructuredTextFBTypeEditor() {
	}

	@Override
	public void init(final IEditorSite site, IEditorInput input) throws PartInitException {
		if (input instanceof FBTypeEditorInput) {
			input = new FileEditorInput(((FBTypeEditorInput) input).getPaletteEntry().getFile());
		}
		super.init(site, input);
	}

	@Override
	protected void doSetInput(IEditorInput input) throws CoreException {
		if (input instanceof FBTypeEditorInput) {
			input = new FileEditorInput(((FBTypeEditorInput) input).getPaletteEntry().getFile());
		}
		super.doSetInput(input);
		setPartName(FordiacMessages.Algorithm);
		setTitleImage(FordiacImage.ICON_ALGORITHM.getImage());
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		// do nothing
	}

	@Override
	public boolean outlineSelectionChanged(final Object selectedElement) {
		if (selectedElement instanceof STAlgorithm) {
			return true;
		}
		return false;
	}

	@Override
	public void setCommonCommandStack(final CommandStack commandStack) {
		// not implemented
	}

	@Override
	public boolean isMarkerTarget(final IMarker marker) {
		return false;
	}

	@Override
	public void reloadType(final FBType type) {
		doRevertToSaved();
	}

	@Override
	public Object getSelectableEditPart() {
		return null;
	}
}
