/*******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH,
 * 				 2018 - 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Matthias Plasch, Filip Andren,
 *   Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl - fixed copy/paste handling
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editors;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.application.actions.CopyEditPartsAction;
import org.eclipse.fordiac.ide.application.actions.CutEditPartsAction;
import org.eclipse.fordiac.ide.application.actions.DeleteFBNetworkAction;
import org.eclipse.fordiac.ide.application.actions.FBNetworkSelectAllAction;
import org.eclipse.fordiac.ide.application.actions.PasteEditPartsAction;
import org.eclipse.fordiac.ide.application.actions.UnmapAction;
import org.eclipse.fordiac.ide.application.actions.UnmapAllAction;
import org.eclipse.fordiac.ide.application.actions.UpdateFBTypeAction;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.ElementEditPartFactory;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.utilities.ApplicationEditorTemplateTransferDropTargetListener;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.preferences.PaletteFlyoutPreferences;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.systemmanagement.ISystemEditor;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.EditPartViewer;
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
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;

/**
 * The main editor for FBNetworks.
 */
public class FBNetworkEditor extends DiagramEditorWithFlyoutPalette implements ISystemEditor {

	private static final PaletteFlyoutPreferences PALETTE_PREFERENCES = new PaletteFlyoutPreferences(
			"FBNetworkPalette.Location", //$NON-NLS-1$
			"FBNetworkPalette.Size", //$NON-NLS-1$
			"FBNetworkPalette.State"); //$NON-NLS-1$

	private FBNetwork model;

	protected void setModel(FBNetwork model) {
		this.model = model;
	}

	public CommandStack getFBEditorCommandStack() {
		return getCommandStack();
	}

	@Override
	protected ScalableFreeformRootEditPart createRootEditPart() {
		return new ZoomScalableFreeformRootEditPart(getSite(), getActionRegistry()) {
			@Override
			public DragTracker getDragTracker(Request req) {
				MarqueeDragTracker dragTracker = new AdvancedMarqueeDragTracker() {
					// redefined from MarqueeSelectionTool
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
						for (Iterator iterator = marqueeSelectedEditParts.iterator(); iterator.hasNext();) {
							EditPart affectedEditPart = (EditPart) iterator.next();
							if (affectedEditPart.getSelected() == EditPart.SELECTED_NONE
									|| getCurrentSelectionMode() != TOGGLE_MODE) {
								// only add connections and FBs
								if ((affectedEditPart instanceof FBEditPart)
										|| (affectedEditPart instanceof ConnectionEditPart)
										|| (affectedEditPart instanceof SubAppForFBNetworkEditPart)) {
									editPartsToSelect.add(affectedEditPart);
								}
							} else {
								editPartsToDeselect.add(affectedEditPart);
							}
						}

						// include the current viewer selection, if not in DEFAULT mode.
						if (getCurrentSelectionMode() != DEFAULT_MODE) {
							editPartsToSelect.addAll(getCurrentViewer().getSelectedEditParts());
							editPartsToSelect.removeAll(editPartsToDeselect);
						}

						getCurrentViewer().setSelection(new StructuredSelection(editPartsToSelect.toArray()));
					}

					@Override
					public void mouseUp(MouseEvent me, EditPartViewer viewer) {
						if (0 != (me.stateMask & SWT.MOD1)) {
							showFBInsertMenu(me, viewer);
						} else {
							super.mouseUp(me, viewer);
						}
					}

					public void showFBInsertMenu(MouseEvent me, EditPartViewer viewer) {
						MenuManager mgr = new MenuManager();
						((UIFBNetworkContextMenuProvider) viewer.getContextMenu()).buildFBInsertMenu(mgr,
								new Point(me.x, me.y));
						mgr.createContextMenu(viewer.getControl()).setVisible(true);
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
	protected ContextMenuProvider getContextMenuProvider(final ScrollingGraphicalViewer viewer,
			final ZoomManager zoomManager) {
		return new UIFBNetworkContextMenuProvider(this, getActionRegistry(), zoomManager, getPalette());
	}

	protected Palette getPalette() {
		return getSystem().getPalette();
	}

	@Override
	protected TransferDropTargetListener createTransferDropTargetListener() {
		return new ApplicationEditorTemplateTransferDropTargetListener(getGraphicalViewer(), getSystem());
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		getGraphicalControl().addListener(SWT.Activate, this::handleActivationChanged);
		getGraphicalControl().addListener(SWT.Deactivate, this::handleActivationChanged);
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

		action = new UnmapAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new UnmapAllAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new CopyEditPartsAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new CutEditPartsAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new PasteEditPartsAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new UpdateFBTypeAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		super.createActions();

		// remove the select all action added in the graphical editor and replace it
		// with our
		action = registry.getAction(ActionFactory.SELECT_ALL.getId());
		registry.removeAction(action);
		getSelectionActions().remove(action.getId());
		action = new FBNetworkSelectAllAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		// we need a special delete action that will order connections before everything
		// else
		action = registry.getAction(ActionFactory.DELETE.getId());
		registry.removeAction(action);
		getSelectionActions().remove(action.getId());
		action = new DeleteFBNetworkAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
	}

	@Override
	public void dispose() {
		super.dispose();
		getEditDomain().setPaletteViewer(null);
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		// we don't need a PalletteRoot as our Pallette viewer will fill itself from the
		// System provided in createPaletteViewerProvider()
		return null;
	}

	@Override
	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new FBTypePaletteViewerProvider(getSystem().getProject(), getEditDomain(), getPalletNavigatorID());
	}

	/**
	 * Method for providing the id to be used for generating the CNF for showing the
	 * pallette.
	 *
	 * This method is to be subclassed by fbnetwork editors which would like to show
	 * different types in there pallete (e.g., Composite type editor).
	 *
	 * @return the navigator id
	 */
	@SuppressWarnings("static-method")
	protected String getPalletNavigatorID() {
		return "org.eclipse.fordiac.ide.fbpaletteviewer"; //$NON-NLS-1$
	}

	@Override
	protected FlyoutPreferences getPalettePreferences() {
		return PALETTE_PREFERENCES;
	}

	public void selectElement(Object element) {
		EditPart editPart = (EditPart) getGraphicalViewer().getEditPartRegistry().get(element);
		if (null != editPart) {
			getGraphicalViewer().select(editPart);
			getGraphicalViewer().reveal(editPart);
		}
	}

	@Override
	public void doSaveAs() {
		// empty
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		if (adapter == FBNetwork.class) {
			return getModel();
		}
		return super.getAdapter(adapter);
	}

	private void handleActivationChanged(Event event) {
		IAction copy = null;
		IAction cut = null;
		IAction paste = null;
		if (event.type == SWT.Activate) {
			copy = getActionRegistry().getAction(ActionFactory.COPY.getId());
			cut = getActionRegistry().getAction(ActionFactory.CUT.getId());
			paste = getActionRegistry().getAction(ActionFactory.PASTE.getId());
		}
		if (getEditorSite().getActionBars().getGlobalActionHandler(ActionFactory.COPY.getId()) != copy) {
			getEditorSite().getActionBars().setGlobalActionHandler(ActionFactory.COPY.getId(), copy);
		}
		if (getEditorSite().getActionBars().getGlobalActionHandler(ActionFactory.CUT.getId()) != cut) {
			getEditorSite().getActionBars().setGlobalActionHandler(ActionFactory.CUT.getId(), cut);
		}
		if (getEditorSite().getActionBars().getGlobalActionHandler(ActionFactory.PASTE.getId()) != paste) {
			getEditorSite().getActionBars().setGlobalActionHandler(ActionFactory.PASTE.getId(), paste);
		}
		getEditorSite().getActionBars().updateActionBars();
	}

}
