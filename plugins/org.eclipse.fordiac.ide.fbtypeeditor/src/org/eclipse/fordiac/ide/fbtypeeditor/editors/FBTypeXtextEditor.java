/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 * 				 2022 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 * 	 Christoph Binder - Extracted code from StructuredTextFBTypeEditor, to enable possibility to reuse this class for multiple xtexteditors
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.part.MultiPageEditorSite;
import org.eclipse.xtext.ui.editor.XtextEditor;

public abstract class FBTypeXtextEditor extends XtextEditor implements IFBTEditorPart{
	
	@Override
	public void init(final IEditorSite site, IEditorInput input) throws PartInitException {
		if (input instanceof FBTypeEditorInput) {
			input = new FileEditorInput(((FBTypeEditorInput) input).getTypeEntry().getFile());
		}
		super.init(site, input);
	}

	@Override
	public void createPartControl(final Composite parent) {
		super.createPartControl(parent);
		installFBTypeUpdater();
	}

	@Override
	protected void doSetInput(IEditorInput input) throws CoreException {
		if (input instanceof FBTypeEditorInput) {
			input = new FileEditorInput(((FBTypeEditorInput) input).getTypeEntry().getFile());
		}
		removeFBTypeUpdater();
		super.doSetInput(input);
	}
	
	@Override
	public void dispose() {
		removeFBTypeUpdater();
		super.dispose();
	}

	@Override
	public void reveal(final int offset, final int length) {
		revealEditor();
		super.reveal(offset, length);
	}

	@Override
	protected void selectAndReveal(final int selectionStart, final int selectionLength, final int revealStart,
			final int revealLength) {
		revealEditor();
		super.selectAndReveal(selectionStart, selectionLength, revealStart, revealLength);
	}
	
	protected void revealEditor() {
		final IEditorSite editorSite = getEditorSite();
		if (editorSite instanceof MultiPageEditorSite) {
			final MultiPageEditorSite multiPageEditorSite = (MultiPageEditorSite) editorSite;
			final MultiPageEditorPart multiPageEditor = multiPageEditorSite.getMultiPageEditor();
			if (multiPageEditor.getSelectedPage() != this) {
				multiPageEditor.setActiveEditor(this);
			}
		}
	}
	
	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		// do nothing
	}

	@Override
	public boolean outlineSelectionChanged(final Object selectedElement) {
		return selectAndReveal(selectedElement, false);
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
	
	protected abstract void removeFBTypeUpdater();
	
	protected abstract void installFBTypeUpdater();
	
	protected abstract boolean selectAndReveal(Object selectedElement, boolean b);
}
