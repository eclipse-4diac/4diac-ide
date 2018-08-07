/*******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							 Johannes Kepler University
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
package org.eclipse.fordiac.ide.fbtypeeditor.ecc;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.fbtypeeditor.FBTypeEditDomain;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.AddECCActionAction;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.DeleteECCAction;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.ECCSelectAllAction;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.NewStateAction;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithm;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECCEditPartFactory;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.gef.ZoomUndoRedoContextMenuProvider;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.ruler.FordiacRulerComposite;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.provider.ECCItemProvider;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.tools.MarqueeDragTracker;
import org.eclipse.gef.tools.MarqueeSelectionTool;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.AlignmentAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.gef.ui.rulers.RulerComposite;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;

/**
 * The Class ECCEditor.
 */
public class ECCEditor extends GraphicalEditorWithFlyoutPalette implements
		IFBTEditorPart {

	/** The fb type. */
	private BasicFBType fbType;

	public BasicFBType getFbType() {
		return fbType;
	}

	/** The shared key handler. */
	private KeyHandler sharedKeyHandler;

	private final EContentAdapter adapter = new EContentAdapter() {

		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);

			if (Notification.REMOVING_ADAPTER != notification.getEventType()) {
				if (((notification.getNewValue() == null) && (notification
						.getNewValue() != notification.getOldValue()))
						|| ((notification.getNewValue() != null) && !(notification
								.getNewValue().equals(notification
								.getOldValue())))) {

				}
			}
		}
	};

	/**
	 * Instantiates a new eCC editor.
	 */
	public ECCEditor() {
	}

	private RulerComposite rulerComp;

	@Override
	protected void createGraphicalViewer(final Composite parent) {
		rulerComp = new FordiacRulerComposite(parent, SWT.NONE);
		super.createGraphicalViewer(rulerComp);
		rulerComp.setGraphicalViewer((ScrollingGraphicalViewer) getGraphicalViewer());
	}

	@Override
	protected Control getGraphicalControl() {
		return rulerComp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.ui.parts.GraphicalEditor#init(org.eclipse.ui.IEditorSite,
	 * org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(final IEditorSite site, final IEditorInput input)
			throws PartInitException {
		setInput(input);
		if (input instanceof FBTypeEditorInput) {
			FBTypeEditorInput untypedInput = (FBTypeEditorInput) input;
			if (untypedInput.getContent() instanceof BasicFBType) {
				fbType = (BasicFBType) untypedInput.getContent();
				if (fbType.getECC() != null) { // TODO: BasicFB should have ECC
												// - check why SimpleFBs are
												// also BasicFBs without ECC?
					fbType.getECC().eAdapters().add(adapter);
				}
			}
		}
		setSite(site);
		setEditDomain(new FBTypeEditDomain(this, commandStack));
		setPartName(Messages.ECCEditor_LABEL_ECCEditorTabName);
		setTitleImage(FordiacImage.ICON_ECC.getImage());
		super.init(site, input);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#configureGraphicalViewer()
	 */
	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		ScrollingGraphicalViewer viewer = (ScrollingGraphicalViewer) getGraphicalViewer();
		
		ScalableFreeformRootEditPart root = createRootEditPart();
		
		viewer.setRootEditPart(root);
		viewer.setEditPartFactory(new ECCEditPartFactory(this));

		//set the control for the new state action so that it can get the correct postion for state creation
		IAction action = getActionRegistry().getAction(NewStateAction.CREATE_STATE);
		((NewStateAction)action).setViewerControl(viewer.getControl());
		
		// configure the context menu provider
		ContextMenuProvider cmProvider = new ZoomUndoRedoContextMenuProvider(
				viewer, root.getZoomManager(), getActionRegistry()) {
			@Override
			public void buildContextMenu(IMenuManager menu) {
				super.buildContextMenu(menu);

				IAction action = registry.getAction(ActionFactory.DELETE
						.getId());
				menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);				
								
				action = registry.getAction(NewStateAction.CREATE_STATE);
				menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
				
				action = registry.getAction(AddECCActionAction.ADD_ECC_ACTION);
				menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);				
			}
		};
		viewer.setContextMenu(cmProvider);

		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1),
				MouseWheelZoomHandler.SINGLETON);

		KeyHandler viewerKeyHandler = new GraphicalViewerKeyHandler(viewer)
				.setParent(getCommonKeyHandler());
		viewer.setKeyHandler(viewerKeyHandler);
	}
	
	/** Override so that we can add a template transferdragsourcelistener for drag and drop
	 */
	@Override
	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new PaletteViewerProvider(getEditDomain()) {
			@Override
			protected void configurePaletteViewer(final PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);
				viewer.addDragSourceListener(new TemplateTransferDragSourceListener(
						viewer));
			}
		};
	}

	private ScalableFreeformRootEditPart createRootEditPart() {
		return new ZoomScalableFreeformRootEditPart(getSite(), getActionRegistry()){

			@Override
			public DragTracker getDragTracker(Request req) {
				MarqueeDragTracker dragTracker = new AdvancedMarqueeDragTracker();
				dragTracker.setMarqueeBehavior(MarqueeSelectionTool.BEHAVIOR_NODES_CONTAINED_AND_RELATED_CONNECTIONS);
				return dragTracker;
			}
			
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#
	 * initializeGraphicalViewer()
	 */
	@Override
	protected void initializeGraphicalViewer() {
		GraphicalViewer viewer = getGraphicalViewer();
		// enable drag from palette
		viewer.addDropTargetListener(new TemplateTransferDropTargetListener(viewer));
		if (fbType.getECC() != null) {
			viewer.setContents(fbType.getECC());
		}
	}

	/**
	 * Gets the common key handler.
	 * 
	 * @return the common key handler
	 */
	protected KeyHandler getCommonKeyHandler() {
		if (sharedKeyHandler == null) {
			sharedKeyHandler = new KeyHandler();
			sharedKeyHandler
					.put(KeyStroke.getPressed(SWT.DEL, 127, 0),
							getActionRegistry().getAction(
									ActionFactory.DELETE.getId()));
			sharedKeyHandler.put(
					KeyStroke.getPressed(SWT.F2, 0),
					getActionRegistry().getAction(
							GEFActionConstants.DIRECT_EDIT));
			sharedKeyHandler.put(/* CTRL + '=' */
			KeyStroke.getPressed('+', 0x3d, SWT.CTRL), getActionRegistry()
					.getAction(GEFActionConstants.ZOOM_IN));

		}
		return sharedKeyHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.ui.parts.GraphicalEditor#selectionChanged(org.eclipse
	 * .ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(final IWorkbenchPart part,
			final ISelection selection) {
		super.selectionChanged(part, selection);
		updateActions(getSelectionActions());
	}

	/** The palette root. */
	private PaletteRoot paletteRoot;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#getPaletteRoot
	 * ()
	 */
	@Override
	protected PaletteRoot getPaletteRoot() {
		paletteRoot = ECCPaletteFactory.createPalette();
		return paletteRoot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	public void doSave(final IProgressMonitor monitor) {
		getCommandStack().markSaveLocation();
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@Override
	public void createPartControl(final Composite parent) {
		SashForm s = new SashForm(parent, SWT.VERTICAL | SWT.SMOOTH);
		Composite graphicaEditor = new Composite(s, SWT.NONE);
		graphicaEditor.setLayout(new FillLayout());
		super.createPartControl(graphicaEditor);
		getSite().setSelectionProvider(getGraphicalViewer());
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
		
		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.LEFT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.RIGHT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.TOP);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.BOTTOM);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.CENTER);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.MIDDLE);
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
	public void dispose() {
		super.dispose();
		if (fbType != null && fbType.getECC() != null) {
			fbType.getECC().eAdapters().remove(adapter);
		}
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
	public void setCommonCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;
	}
	
	@Override
	protected FlyoutPreferences getPalettePreferences() {
		return ECCPaletteFactory.createPalettePreferences();
	}
}
