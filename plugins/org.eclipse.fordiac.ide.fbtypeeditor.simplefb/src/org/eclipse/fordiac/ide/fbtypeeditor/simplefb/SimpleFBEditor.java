/*******************************************************************************
 * Copyright (c) 2018 TU Wien/ACIN, Johannes Kepler University
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Peter Gsellmann 
 *   - initial implementation and/or initial documentation
 *   Alois Zoitl - reworked algorithm edting to reduce duplicated code
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.simplefb;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.fbtypeeditor.simplefb.widgets.AlgorithmEditingCompositeSimpleFB;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
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
	public void createPartControl(Composite parent) {
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		baseAlgorithm.createControls(parent, toolkit);
		baseAlgorithm.initialize(fbType, commandStack);
		baseAlgorithm.setAlgorithm(fbType.getAlgorithm());
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setInput(input);
		if (input instanceof FBTypeEditorInput) {
			FBTypeEditorInput untypedInput = (FBTypeEditorInput) input;
			if (untypedInput.getContent() instanceof SimpleFBType) {
				fbType = (SimpleFBType) untypedInput.getContent();
			}
		}
		setCommonCommandStack(commandStack);
		setSite(site);
		setPartName("Algorithm");
		setTitleImage(FordiacImage.ICON_ALGORITHM.getImage());
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(this);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
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
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		baseAlgorithm.setAlgorithm(fbType.getAlgorithm());

	}

	@Override
	public boolean outlineSelectionChanged(Object selectedElement) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCommonCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	@Override
	public void dispose() {
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(this);
		super.dispose();
	}
}
