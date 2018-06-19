/*******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH,
 * 							 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Matthias Plasch, Filip Andren,
 *   Monika Wenger 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editors;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.actions.CopyEditPartsAction;
import org.eclipse.fordiac.ide.application.actions.DeleteFBNetworkAction;
import org.eclipse.fordiac.ide.application.actions.FBNetworkSelectAllAction;
import org.eclipse.fordiac.ide.application.actions.PasteEditPartsAction;
import org.eclipse.fordiac.ide.application.actions.SaveAsSubApplicationTypeAction;
import org.eclipse.fordiac.ide.application.actions.UnmapAction;
import org.eclipse.fordiac.ide.application.actions.UnmapAllAction;
import org.eclipse.fordiac.ide.application.actions.UpdateFBTypeAction;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.ElementEditPartFactory;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.utilities.ApplicationEditorTemplateTransferDropTargetListener;
import org.eclipse.fordiac.ide.application.utilities.FBNetworkFlyoutPreferences;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.systemmanagement.ISystemEditor;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.tools.MarqueeDragTracker;
import org.eclipse.gef.tools.MarqueeSelectionTool;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;

/**
 * The main editor for FBNetworks.
 */
public class FBNetworkEditor extends DiagramEditorWithFlyoutPalette  implements ISystemEditor{

	/** The adapter. */
	EContentAdapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(
				final Notification notification) {
			int type = notification.getEventType();
			int featureId = notification.getFeatureID(Application.class);

			switch (type) {
			case Notification.SET:
				if (featureId == LibraryElementPackage.INAMED_ELEMENT__NAME) {
					setPartName(getModel().getApplication().getName());
				}
				break;
			default:
				break;
			}
		}
	};

	private FBNetwork model;
	
	protected void setModel(FBNetwork model) {
		this.model = model;
	}

	
	public CommandStack getFBEditorCommandStack() {
		return getCommandStack();
	}
	
	@Override
	protected ScalableFreeformRootEditPart createRootEditPart() {
		return new ZoomScalableFreeformRootEditPart(getSite(), getActionRegistry()){
			@Override	
			public DragTracker getDragTracker(Request req) {
				MarqueeDragTracker dragTracker = new AdvancedMarqueeDragTracker(){
					//redefined from MarqueeSelectionTool
					static final int DEFAULT_MODE = 0;
					static final int TOGGLE_MODE = 1;
					
					@SuppressWarnings({ "rawtypes", "unchecked" })
					@Override
					protected void performMarqueeSelect() {
						// determine which edit parts are affected by the current marquee
						// selection
						Collection marqueeSelectedEditParts = calculateMarqueeSelectedEditParts();

						// calculate nodes/connections that are to be selected/deselected,
						// dependent on the current mode of the tool
						Collection editPartsToSelect = new LinkedHashSet();
						Collection editPartsToDeselect = new HashSet();
						for (Iterator iterator = marqueeSelectedEditParts.iterator(); iterator
								.hasNext();) {
							EditPart affectedEditPart = (EditPart) iterator.next();
							if (affectedEditPart.getSelected() == EditPart.SELECTED_NONE
									|| getCurrentSelectionMode() != TOGGLE_MODE){
								//only add connections and FBs
								if((affectedEditPart instanceof FBEditPart) || 
										(affectedEditPart instanceof ConnectionEditPart) ||
										(affectedEditPart instanceof SubAppForFBNetworkEditPart)){
									editPartsToSelect.add(affectedEditPart);
								}
							}
							else{
								editPartsToDeselect.add(affectedEditPart);
							}
						}

						// include the current viewer selection, if not in DEFAULT mode.
						if (getCurrentSelectionMode() != DEFAULT_MODE) {
							editPartsToSelect.addAll(getCurrentViewer().getSelectedEditParts());
							editPartsToSelect.removeAll(editPartsToDeselect);
						}

						getCurrentViewer().setSelection(
								new StructuredSelection(editPartsToSelect.toArray()));
					}
				};
				
				dragTracker.setMarqueeBehavior(MarqueeSelectionTool.BEHAVIOR_NODES_CONTAINED_AND_RELATED_CONNECTIONS);
				return dragTracker;
			}
		};
	}
		
	
	@Override
	public FBNetwork getModel() {
		return model;
	}

	/**
	 * Instantiates a new fB network editor.
	 */
	public FBNetworkEditor() {
		// empty constructor
	}

	@Override
	protected EditPartFactory getEditPartFactory() {
		return new ElementEditPartFactory(this, getZoomManger());
	}

	@Override
	protected ContextMenuProvider getContextMenuProvider(
			final ScrollingGraphicalViewer viewer,
			final ZoomManager zoomManager) {
		ContextMenuProvider cmp = new UIFBNetworkContextMenuProvider(this,
				getActionRegistry(), zoomManager, getPalette());
		return cmp;
	}

	protected Palette getPalette() {
		return getSystem().getPalette();
	}

	@Override
	protected TransferDropTargetListener createTransferDropTargetListener() {
		return new ApplicationEditorTemplateTransferDropTargetListener(
				getGraphicalViewer(), getSystem());
	}


	@Override
	protected void setModel(final IEditorInput input) {
		if (input instanceof org.eclipse.fordiac.ide.util.PersistableUntypedEditorInput) {
			org.eclipse.fordiac.ide.util.PersistableUntypedEditorInput untypedInput = (org.eclipse.fordiac.ide.util.PersistableUntypedEditorInput) input;
			Object content = untypedInput.getContent();
			if (content instanceof Application) {
//TODO model refactoring - consider moving this to the base class
				Application app = ((Application) content); 
				model = app.getFBNetwork();
				
				// register EContentAdapter to be informed on changes of the
				// application name
				app.eAdapters().add(adapter);
			}
			if (input.getName() != null) {
				setPartName(input.getName());
			}
		}
		super.setModel(input);
	}

	@Override
	protected void configureGraphicalViewer() {

		super.configureGraphicalViewer();
		final CopyEditPartsAction copy = (CopyEditPartsAction) getActionRegistry()
				.getAction(ActionFactory.COPY.getId());

		getGraphicalViewer().addSelectionChangedListener(copy);
	}

	@Override
	public AutomationSystem getSystem() {
		return getModel().getAutomationSystem();
	}


	@Override
	public void doSave(final IProgressMonitor monitor) {
		// TODO __gebenh error handling if save fails!
		SystemManager.INSTANCE.saveSystem(getSystem());
		getCommandStack().markSaveLocation();
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void createActions() {
		ActionRegistry registry = getActionRegistry();
		IAction action;
		
		action = new SaveAsSubApplicationTypeAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new UnmapAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		action = new UnmapAllAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new CopyEditPartsAction(this);
		registry.registerAction(action);
		getEditorSite().getActionBars().setGlobalActionHandler(
				ActionFactory.COPY.getId(), action);

		action = new PasteEditPartsAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		getEditorSite().getActionBars().setGlobalActionHandler(
				ActionFactory.PASTE.getId(), action);
		
		action = new UpdateFBTypeAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		super.createActions();
		
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
		getSelectionActions().remove(action.getId());		
		action = new DeleteFBNetworkAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
	}

	@Override
	public void dispose() {
		if (adapter != null && getModel() != null && getModel().eAdapters().contains(adapter)) {
				getModel().eAdapters().remove(adapter);
		}
		super.dispose();
		getEditDomain().setPaletteViewer(null);
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		//we don't need a PalletteRoot as our Pallette viewer will fill itself from the System provided in createPaletteViewerProvider()
		return null;
	}
	
	@Override
	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new FBTypePaletteViewerProvider(getSystem().getProject(), getEditDomain(), getPalletNavigatorID());
	}
	
	/** Method for providing the id to be used for generating the CNF for showing the pallette.
	 * 
	 * This method is to be subclassed by fbnetwork editors which would like to show different types in there 
	 * pallete (e.g., Composite type editor). 
	 * 
	 * @return the navigator id
	 */
	protected String getPalletNavigatorID() {
		return "org.eclipse.fordiac.ide.fbpaletteviewer"; //$NON-NLS-1$;
	}

	@Override
	protected FlyoutPreferences getPalettePreferences(){
		return FBNetworkFlyoutPreferences.INSTANCE;
	}

	public void selectFB(FB fb) {
		EditPart editPart = (EditPart)getGraphicalViewer().getEditPartRegistry().get(fb);
		if(null != editPart){
			getGraphicalViewer().select(editPart);
			getGraphicalViewer().reveal(editPart);
		}
	}

	@Override
	public void doSaveAs() {
		// empty
	}
	
	

}
