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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.document.STAlgorithmDocument;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.document.STAlgorithmDocumentFBTypeUpdater;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmMapper;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
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
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.util.ITextRegion;

import com.google.inject.Inject;

public class StructuredTextFBTypeEditor extends XtextEditor implements IFBTEditorPart {

	@Inject
	private STAlgorithmMapper algorithmMapper;

	@Inject
	private ILocationInFileProvider locationProvider;

	@Inject
	private STAlgorithmDocumentFBTypeUpdater fbTypeUpdater;

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
	public void createPartControl(final Composite parent) {
		super.createPartControl(parent);
		installFBTypeUpdater();
	}

	@Override
	protected void doSetInput(IEditorInput input) throws CoreException {
		if (input instanceof FBTypeEditorInput) {
			input = new FileEditorInput(((FBTypeEditorInput) input).getPaletteEntry().getFile());
		}
		removeFBTypeUpdater();
		super.doSetInput(input);
		setPartName(FordiacMessages.Algorithm);
		setTitleImage(FordiacImage.ICON_ALGORITHM.getImage());
	}

	@Override
	public void dispose() {
		removeFBTypeUpdater();
		super.dispose();
	}

	protected void installFBTypeUpdater() {
		if (getDocument() instanceof STAlgorithmDocument) {
			fbTypeUpdater.install((STAlgorithmDocument) getDocument());
		}
	}

	protected void removeFBTypeUpdater() {
		fbTypeUpdater.uninstall();
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

	protected boolean selectAndReveal(final Object element, final boolean revealEditor) {
		final ITextRegion location = getDocument().priorityReadOnly(resource -> {
			if (resource != null) {
				final EObject object = algorithmMapper.fromModel(resource, element);
				if (object != null) {
					return locationProvider.getSignificantTextRegion(object);
				}
			}
			return null;
		});
		if (location != null) {
			selectAndReveal(location.getOffset(), location.getLength());
			if (revealEditor) {
				revealEditor();
			}
		}
		return location != null;
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
}
