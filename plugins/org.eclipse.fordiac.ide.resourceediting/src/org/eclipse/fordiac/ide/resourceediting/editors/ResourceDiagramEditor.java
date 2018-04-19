/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.resourceediting.editors;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.resourceediting.editparts.ResourceDiagramEditPartFactory;
import org.eclipse.fordiac.ide.util.ColorManager;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IEditorInput;

/**
 * The main editor for ResourceDiagramEditors (mapping and resource editing).
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class ResourceDiagramEditor extends FBNetworkEditor {

	private EContentAdapter resourceAdapter = new EContentAdapter(){

		@Override
		public void notifyChanged(Notification notification) {
			Object feature = notification.getFeature();
			if ((LibraryElementPackage.eINSTANCE.getINamedElement_Name().equals(feature)) && 
					(getResource().equals(notification.getNotifier()))){				
				setPartName(ResourceEditorInput.getResourceEditorName(getResource()));
			}
			super.notifyChanged(notification);
		}
		
	};
	
	private EContentAdapter colorChangeListener = new EContentAdapter() {
		@Override
		public void notifyChanged(Notification notification) {
			if (notification.getFeature() == LibraryElementPackage.eINSTANCE.getColorizableElement_Color()) {
				updateGridColor();
			}
		}
	};
		
	private Resource getResource() {
		return (Resource)getModel().eContainer();
	}


	@Override
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		updateGridColor();
	}

	@Override
	protected EditPartFactory getEditPartFactory() {
		return new ResourceDiagramEditPartFactory(this, getZoomManger());
	}

	@Override
	protected ContextMenuProvider getContextMenuProvider(
			final ScrollingGraphicalViewer viewer,
			final ZoomManager zoomManager) {
		return new ResourceDiagramEditorContextMenuProvider(this, getActionRegistry(), zoomManager, getPalette());
	}

	@Override
	protected void setModel(final IEditorInput input) {
		if (input instanceof org.eclipse.fordiac.ide.util.PersistableUntypedEditorInput) {
			org.eclipse.fordiac.ide.util.PersistableUntypedEditorInput untypedInput = (org.eclipse.fordiac.ide.util.PersistableUntypedEditorInput) input;
			Object content = untypedInput.getContent();
			if (content instanceof Resource) {
				setModel(((Resource) content).getFBNetwork());				
				getResource().eAdapters().add(resourceAdapter);
				getResource().getDevice().eAdapters().add(colorChangeListener);
				if (input.getName() != null) {
					setPartName(input.getName());
				}
			}
		}
		super.setModel(input);
	}
	
	@Override
	public void dispose() {		
		if(null != getResource()){
			getResource().eAdapters().remove(resourceAdapter);
			getResource().getDevice().eAdapters().remove(colorChangeListener);
		}

		super.dispose();
	}

	private void updateGridColor(){
		if(null != getResource()){
			IFigure layer = ((ZoomScalableFreeformRootEditPart)getViewer().getRootEditPart()).getLayer(LayerConstants.GRID_LAYER);
			if(null != layer){
				Color devColor = getResource().getDevice().getColor();
				if(null != devColor){
					org.eclipse.swt.graphics.Color newColor = ColorManager.getColor(new RGB(devColor.getRed(), devColor.getGreen(), devColor.getBlue()));
					layer.setForegroundColor(newColor);				
				}			
			}
		}
	}
}
