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

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.actions.DeleteFBNetworkAction;
import org.eclipse.fordiac.ide.application.actions.FBNetworkSelectAllAction;
import org.eclipse.fordiac.ide.application.actions.UnmapAction;
import org.eclipse.fordiac.ide.application.actions.UnmapAllAction;
import org.eclipse.fordiac.ide.application.editors.FBTypePaletteViewerProvider;
import org.eclipse.fordiac.ide.application.utilities.ApplicationEditorTemplateTransferDropTargetListener;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.resourceediting.editparts.ResourceDiagramEditPartFactory;
import org.eclipse.fordiac.ide.systemmanagement.ISystemEditor;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.util.ColorManager;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;

/**
 * The main editor for ResourceDiagramEditors (mapping and resource editing).
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class ResourceDiagramEditor extends DiagramEditorWithFlyoutPalette  implements ISystemEditor{
	private Resource resource;
	private EContentAdapter resourceAdapter = new EContentAdapter(){

		@Override
		public void notifyChanged(Notification notification) {
			Object feature = notification.getFeature();
			if ((LibraryElementPackage.eINSTANCE.getINamedElement_Name().equals(feature)) && 
					(resource.equals(notification.getNotifier()))){				
				setPartName(ResourceEditorInput.getResourceEditorName(resource));
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
		
	@Override
	public CommandStack getCommandStack(){
		return SystemManager.INSTANCE.getCommandStack(getSystem());
	}
	
	@Override
	public FBNetwork getModel() {
		Assert.isNotNull(resource);
		return resource.getFBNetwork();
	}

	/**
	 * Instantiates a new resource diagram editor.
	 */
	public ResourceDiagramEditor() {
		// empty constructor
	}

	@Override
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();
		
		// listen for dropped parts
		TransferDropTargetListener listener = createTransferDropTargetListener();
		if (listener != null) {
			viewer.addDropTargetListener(createTransferDropTargetListener());
		}
		
		getGraphicalViewer().addDropTargetListener((TransferDropTargetListener) new TemplateTransferDropTargetListener(getGraphicalViewer()));		
		updateGridColor();
	}

	@Override
	protected EditPartFactory getEditPartFactory() {
		return new ResourceDiagramEditPartFactory(this, getGraphicalViewer(), getZoomManger());
	}

	@Override
	protected ContextMenuProvider getContextMenuProvider(
			final ScrollingGraphicalViewer viewer,
			final ZoomManager zoomManager) {
		return new ResourceDiagramEditorContextMenuProvider(viewer, zoomManager, getActionRegistry());
	}

	@Override
	protected TransferDropTargetListener createTransferDropTargetListener() {		
		Palette palette = resource.getPaletteEntry().getGroup().getPallete();
		return new ApplicationEditorTemplateTransferDropTargetListener(
				getGraphicalViewer(), SystemManager.INSTANCE.getSystemForName(palette.getAutomationSystem().getName()));
	}

	@Override
	protected void setModel(final IEditorInput input) {
		if (input instanceof org.eclipse.fordiac.ide.util.PersistableUntypedEditorInput) {
			org.eclipse.fordiac.ide.util.PersistableUntypedEditorInput untypedInput = (org.eclipse.fordiac.ide.util.PersistableUntypedEditorInput) input;
			Object content = untypedInput.getContent();
			if (content instanceof Resource) {
				resource = (Resource) content;				
				resource.eAdapters().add(resourceAdapter);
				resource.getDevice().eAdapters().add(colorChangeListener);
				if (input.getName() != null) {
					setPartName(input.getName());
				}
			}
			setEditDomain(new DefaultEditDomain(this));
			// use one "System - Wide" command stack to avoid inconsistencies due to undo redo
			getEditDomain().setCommandStack(
					SystemManager.INSTANCE.getCommandStack(getSystem()));
		}
	}
	
	@Override
	public AutomationSystem getSystem() {
		return resource.getAutomationSystem();
	}


	@SuppressWarnings("unchecked")
	@Override
	protected void createActions() {
		ActionRegistry registry = getActionRegistry();
		IAction action;

		action = new UnmapAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		action = new UnmapAllAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		super.createActions();
		
		//TODO duplicated code from FBNetworkEditor
		//remove the select all action added in the graphical editor and replace it with our
		action = registry.getAction(ActionFactory.SELECT_ALL.getId());
		registry.removeAction(action);		
		getSelectionActions().remove(action.getId());
		action = new FBNetworkSelectAllAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		//we need a special delete action that will order connections before everything else		
		action = registry.getAction(ActionFactory.DELETE.getId());
		registry.removeAction(action);
		getSelectionActions().remove(action);		
		action = new DeleteFBNetworkAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// // TODO __gebenh error handling if save fails!
		SystemManager.INSTANCE.saveSystem(getSystem());
		getCommandStack().markSaveLocation();
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@Override
	public void dispose() {		
		if(null != resource){
			resource.eAdapters().remove(resourceAdapter);
			resource.getDevice().eAdapters().remove(colorChangeListener);
		}

		super.dispose();
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		return null;  //we are filling a the palette directly in the viewer so we don't need it here
	}

	
	@Override
	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new FBTypePaletteViewerProvider(getSystem().getProject(), getEditDomain());
	}

	@Override
	public void doSaveAs() {
		// empty
	}
	
	private void updateGridColor(){
		if(null != resource){
			IFigure layer = ((ZoomScalableFreeformRootEditPart)getViewer().getRootEditPart()).getLayer(LayerConstants.GRID_LAYER);
			if(null != layer){
				Color devColor = resource.getDevice().getColor();
				if(null != devColor){
					org.eclipse.swt.graphics.Color newColor = ColorManager.getColor(new RGB(devColor.getRed(), devColor.getGreen(), devColor.getBlue()));
					layer.setForegroundColor(newColor);				
				}			
			}
		}
	}
}
