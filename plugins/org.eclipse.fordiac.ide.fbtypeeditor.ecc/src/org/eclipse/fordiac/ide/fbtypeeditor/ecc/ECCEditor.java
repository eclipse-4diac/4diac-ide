/*******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 				 2018 - 2019 Johannes Kepler University Linz
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - changed inheritence to DiagramEditorWithFlyoutPalette, 
 *                 removed duplicated code 
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.AddECCActionAction;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.DeleteECCAction;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.ECCSelectAllAction;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.NewStateAction;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithm;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECCEditPartFactory;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.gef.ZoomUndoRedoContextMenuProvider;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.provider.ECCItemProvider;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.tools.MarqueeDragTracker;
import org.eclipse.gef.tools.MarqueeSelectionTool;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;

/**
 * The Class ECCEditor.
 */
public class ECCEditor extends DiagramEditorWithFlyoutPalette implements
		IFBTEditorPart {

	/** The fb type. */
	private BasicFBType fbType;

	public BasicFBType getFbType() {
		return fbType;
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input)
			throws PartInitException {
		setInput(input);
		if (input instanceof FBTypeEditorInput) {
			FBTypeEditorInput untypedInput = (FBTypeEditorInput) input;
			if (untypedInput.getContent() instanceof BasicFBType) {
				fbType = (BasicFBType) untypedInput.getContent();
			}
		}
		super.init(site, input);
		setPartName(Messages.ECCEditor_LABEL_ECCEditorTabName);
		setTitleImage(FordiacImage.ICON_ECC.getImage());
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		ScrollingGraphicalViewer viewer = (ScrollingGraphicalViewer) getGraphicalViewer();

		//set the control for the new state action so that it can get the correct position for state creation
		IAction action = getActionRegistry().getAction(NewStateAction.CREATE_STATE);
		((NewStateAction)action).setViewerControl((FigureCanvas)viewer.getControl());
		((NewStateAction)action).setZoomManager(getZoomManger()); 
		
		viewer.getKeyHandler().put(KeyStroke.getPressed(SWT.DEL, 127, 0),
				getActionRegistry().getAction(
						ActionFactory.DELETE.getId()));
	}
	
	
	@Override
	protected KeyHandler getCommonKeyHandler() {
		KeyHandler keyHandler = super.getCommonKeyHandler();
		
		// bind insert key press to add action action
		keyHandler.put(KeyStroke.getPressed(SWT.INSERT, 0),
				getActionRegistry().getAction(AddECCActionAction.ADD_ECC_ACTION));
		return keyHandler;
	}	
	
	
	protected ContextMenuProvider getContextMenuProvider(ScrollingGraphicalViewer viewer, ZoomManager zoomManager) {
		return new ZoomUndoRedoContextMenuProvider(
				viewer, zoomManager, getActionRegistry()) {
			@Override
			public void buildContextMenu(IMenuManager menu) {
				super.buildContextMenu(menu);
								
				IAction action = getRegistry().getAction(NewStateAction.CREATE_STATE);
				menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
				
				action = getRegistry().getAction(AddECCActionAction.ADD_ECC_ACTION);
				menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);				

				action = getRegistry().getAction(ActionFactory.DELETE.getId());
				menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);				
			}
		};
	}
	
	@Override
	protected ScalableFreeformRootEditPart createRootEditPart() {
		return new ZoomScalableFreeformRootEditPart(getSite(), getActionRegistry()){

			@Override
			public DragTracker getDragTracker(Request req) {
				MarqueeDragTracker dragTracker = new AdvancedMarqueeDragTracker();
				dragTracker.setMarqueeBehavior(MarqueeSelectionTool.BEHAVIOR_NODES_CONTAINED_AND_RELATED_CONNECTIONS);
				return dragTracker;
			}
			
		};
	}
	
	@Override
	public void selectionChanged(final IWorkbenchPart part,
			final ISelection selection) {
		super.selectionChanged(part, selection);
		updateActions(getSelectionActions());
	}


	/** The palette root. */
	private PaletteRoot paletteRoot;

	@Override
	protected PaletteRoot getPaletteRoot() {
		if(null == paletteRoot) {
			paletteRoot = ECCPaletteFactory.createPalette();
		}
		return paletteRoot;
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		getCommandStack().markSaveLocation();
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void createActions() {
		ActionRegistry registry = getActionRegistry();
		IAction action;
		
		action = new NewStateAction(this);
		registry.registerAction(action);
	
		action = new AddECCActionAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		super.createActions();
		
		//we need a special delete action that will remove ecc_actions from the list if it contains also the according state		
		action = registry.getAction(ActionFactory.DELETE.getId());
		registry.removeAction(action);
		getSelectionActions().remove(action.getId());
		
		action = new DeleteECCAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		//remove the select all action added in the graphical editor and replace it with our
		action = registry.getAction(ActionFactory.SELECT_ALL.getId());
		registry.removeAction(action);
		getSelectionActions().remove(action.getId());
		
		action = new ECCSelectAllAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
	}

	@Override
	public boolean outlineSelectionChanged(Object selectedElement) {
		Object obj = getGraphicalViewer().getEditPartRegistry().get(selectedElement);
		if(obj instanceof EditPart){
			getGraphicalViewer().select((EditPart)obj);
			return true;
		}
		if(selectedElement instanceof ECCItemProvider){
			return true;
		}
		if(selectedElement instanceof ECAction){
			obj = getGraphicalViewer().getEditPartRegistry().get(
					((ECAction)selectedElement).eContainer());
			if(null != obj){
				for (Object element : ((ECStateEditPart)obj).getCurrentChildren()) {
					if((element instanceof ECActionAlgorithm) && (selectedElement.equals(
							((ECActionAlgorithm)element).getAction()))){
						obj = getGraphicalViewer().getEditPartRegistry().get(element);
						if(null != obj){
							getGraphicalViewer().select((EditPart)obj);
						}
					}
				}
			}
			return true;
		}
		
		return false;
	}
	
	private CommandStack commandStack;
	
	@Override
	protected void setModel(IEditorInput input) {
		super.setModel(input);				
		setEditDomain(new ECCEditorEditDomain(this, commandStack));
	}
	
	@Override
	public void setCommonCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;		
	}
	
	@Override
	protected FlyoutPreferences getPalettePreferences() {
		return ECCPaletteFactory.PALETTE_PREFERENCES;
	}

	@Override
	public Object getModel() {
		return fbType.getECC();
	}

	@Override
	protected EditPartFactory getEditPartFactory() {
		return new ECCEditPartFactory(this);
	}

	@Override
	protected TransferDropTargetListener createTransferDropTargetListener() {
		// we don't need an additional transferdroptarget listener
		return null;
	}

	@Override
	public AutomationSystem getSystem() {
		return null;   //this is currently needed as the base class is targeted for system editors
	}

	@Override
	public void doSaveAs() {
		// nothing to do here		
	}
	
}
