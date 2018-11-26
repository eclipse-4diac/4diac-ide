/*******************************************************************************
 * Copyright (c) 2013 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.viewer;

import java.util.EventObject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.application.viewer.composite.CompositeInstanceViewerInput;
import org.eclipse.fordiac.ide.gef.DiagramEditor;
import org.eclipse.fordiac.ide.gef.ZoomUndoRedoContextMenuProvider;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.util.AdvancedPanningSelectionTool;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.ui.IEditorInput;

public class CompositeInstanceViewer extends DiagramEditor {

	private FB fb;
	private CompositeFBType cfbt;
	private FBEditPart fbEditPart;
	

	@Override
	protected EditPartFactory getEditPartFactory() {
		return new CompositeViewerEditPartFactory(this, fb, fbEditPart, getZoomManger());
	}

	@Override
	protected ContextMenuProvider getContextMenuProvider(ScrollingGraphicalViewer viewer,
			ZoomManager zoomManager) {
		return new ZoomUndoRedoContextMenuProvider(getGraphicalViewer(), zoomManager, getActionRegistry());
	}

	@Override
	protected TransferDropTargetListener createTransferDropTargetListener() {
		//
		return null;
	}

	@Override
	public AutomationSystem getSystem() {
		return null;
	}

	@Override
	public String getFileName() {
		return null;
	}

	@Override
	protected void setModel(IEditorInput input) {

		setEditDomain(new DefaultEditDomain(this));
		getEditDomain().setDefaultTool(new AdvancedPanningSelectionTool());
		getEditDomain().setActiveTool(getEditDomain().getDefaultTool());

		if (input instanceof CompositeInstanceViewerInput) {
			CompositeInstanceViewerInput untypedInput = (CompositeInstanceViewerInput) input;
			Object content = untypedInput.getContent();
			if ((content instanceof FB) && (((FB) content).getType() instanceof CompositeFBType)) {
				fb = (FB) content;
				setPartName(getNameHierarchy());
				//we need to copy the type so that we have an instance specific network TODO consider using here the type
				//cfbt = EcoreUtil.copy((CompositeFBType) fb.getFBType()); 
				cfbt = (CompositeFBType) fb.getType();
				this.fbEditPart = untypedInput.getFbEditPart();
			}
		}
	}


	@Override
	public FBNetwork getModel(){
		return cfbt.getFBNetwork();
	}
	
	@Override
	protected void initializeGraphicalViewer() {
		GraphicalViewer viewer = getGraphicalViewer();
		if (cfbt.getFBNetwork() != null) {			
			viewer.setContents(getModel());
		}
	}
	
	@Override
	public void commandStackChanged(EventObject event) {
		// nothing to do as its a viewer!
	}
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		// nothing to do as its a viewer!
	}
	
	private String getNameHierarchy() {
		//TODO mabye a nice helper function to be put into the fb model
		StringBuilder retVal =  new StringBuilder(fb.getName());
		EObject cont = fb.eContainer().eContainer();
		while(cont instanceof INamedElement){
			retVal.insert(0, ((INamedElement)cont).getName() + "."); //$NON-NLS-1$
			if(cont instanceof Application) {
				break;
			}
			cont = cont.eContainer().eContainer();
		}		
		return retVal.toString();
	}

}
