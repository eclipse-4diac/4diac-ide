/*******************************************************************************
 * Copyright (c) 2018 TU Wien/ACIN, Johannes Kepler University
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Peter Gsellmann
 *   - initial implementation and/or initial documentation
 *   Alois Zoitl - reworked algorithm edting to reduce duplicated code
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.simplefb;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.fbtypeeditor.simplefb.widgets.AlgorithmEditingCompositeSimpleFB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.EditorPart;

public class SimpleFBEditor extends EditorPart implements IFBTEditorPart {

	private final AlgorithmEditingCompositeSimpleFB baseAlgorithm = new AlgorithmEditingCompositeSimpleFB();
	private SimpleFBType fbType;
	private CommandStack commandStack;

	public SimpleFBEditor() {
		// No initialization needed
	}

	@Override
	public void createPartControl(final Composite parent) {
		final FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		baseAlgorithm.createControls(parent, toolkit);
		baseAlgorithm.initialize(fbType, commandStack);
		baseAlgorithm.setAlgorithm(fbType.getAlgorithm());
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		setInput(input);
		if (input instanceof FBTypeEditorInput) {
			final FBTypeEditorInput untypedInput = (FBTypeEditorInput) input;
			if (untypedInput.getContent() instanceof SimpleFBType) {
				fbType = (SimpleFBType) untypedInput.getContent();
			}
		}
		setCommonCommandStack(commandStack);
		setSite(site);
		setPartName(FordiacMessages.Algorithm);
		setTitleImage(FordiacImage.ICON_ALGORITHM.getImage());
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(this);
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		commandStack.markSaveLocation();
		firePropertyChange(IEditorPart.PROP_DIRTY);

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		baseAlgorithm.setAlgorithm(fbType.getAlgorithm());

	}

	@Override
	public boolean outlineSelectionChanged(final Object selectedElement) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCommonCommandStack(final CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	@Override
	public void dispose() {
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(this);
		super.dispose();
	}

	@Override
	public void gotoMarker(final IMarker marker) {
		// For now we don't handle markers in this editor
	}

	@Override
	public boolean isMarkerTarget(final IMarker marker) {
		// For now we don't handle markers in this editor
		return false;
	}

	@Override
	public void reloadType(final FBType type) {
		if (type instanceof SimpleFBType) {
			fbType = (SimpleFBType) type;
			baseAlgorithm.setAlgorithm(fbType.getAlgorithm());
		}

	}

}
