/*******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH,
 * 				 2018 - 2020 Johannes Kepler University
 * 				 2020 Primetals Technologies Germany GmbH
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
 *               - extracted FBNetworkRootEditPart from FBNetworkEditor
 *               - extracted panning and selection tool
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.application.actions.CopyEditPartsAction;
import org.eclipse.fordiac.ide.application.actions.CutEditPartsAction;
import org.eclipse.fordiac.ide.application.actions.DeleteFBNetworkAction;
import org.eclipse.fordiac.ide.application.actions.FBNetworkSelectAllAction;
import org.eclipse.fordiac.ide.application.actions.PasteEditPartsAction;
import org.eclipse.fordiac.ide.application.actions.UpdateFBTypeAction;
import org.eclipse.fordiac.ide.application.editparts.ElementEditPartFactory;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkRootEditPart;
import org.eclipse.fordiac.ide.application.tools.FBNetworkPanningSelectionTool;
import org.eclipse.fordiac.ide.application.utilities.ApplicationEditorTemplateTransferDropTargetListener;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.gef.preferences.PaletteFlyoutPreferences;
import org.eclipse.fordiac.ide.gef.tools.AdvancedPanningSelectionTool;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.systemmanagement.ISystemEditor;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.swt.SWT;
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
		return new FBNetworkRootEditPart(getModel(), getPalette(), getSite(), getActionRegistry());
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
		return new ElementEditPartFactory(this);
	}

	@Override
	protected ContextMenuProvider getContextMenuProvider(final ScrollingGraphicalViewer viewer,
			final ZoomManager zoomManager) {
		return new FBNetworkContextMenuProvider(this, getActionRegistry(), zoomManager, getPalette());
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
		SystemManager.saveSystem(getSystem());
		getCommandStack().markSaveLocation();
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void createActions() {
		ActionRegistry registry = getActionRegistry();
		IAction action;

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
		return new FBTypePaletteViewerProvider(getSystem().getSystemFile().getProject(), getEditDomain(),
				getPaletteNavigatorID());
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
	protected String getPaletteNavigatorID() {
		return "org.eclipse.fordiac.ide.fbpaletteviewer"; //$NON-NLS-1$
	}

	@Override
	protected FlyoutPreferences getPalettePreferences() {
		return PALETTE_PREFERENCES;
	}

	public void selectElement(Object element) {
		EditPart editPart = (EditPart) getGraphicalViewer().getEditPartRegistry().get(element);
		if (null != editPart) {
			getGraphicalViewer().flush();
			getGraphicalViewer().selectAndRevealEditPart(editPart);
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

	@Override
	protected AdvancedPanningSelectionTool createDefaultTool() {
		return new FBNetworkPanningSelectionTool();
	}

}
