/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - inherited FBInterface editor from the common diagram editor to
 *   				to reduce code duplication and more common look and feel
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.fbtypeeditor.FBInterfacePaletteFactory;
import org.eclipse.fordiac.ide.fbtypeeditor.FBTypeEditDomain;
import org.eclipse.fordiac.ide.fbtypeeditor.contentprovider.InterfaceContextMenuProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBInterfaceEditPartFactory;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

public class FBInterfaceEditor extends DiagramEditorWithFlyoutPalette implements IFBTEditorPart {

	private CommandStack commandStack;
	private FBType fbType;

	private PaletteRoot paletteRoot;
	private TypeLibrary typeLib;

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		setInput(input);
		if (input instanceof FBTypeEditorInput) {
			final FBTypeEditorInput untypedInput = (FBTypeEditorInput) input;
			fbType = untypedInput.getContent();
			typeLib = untypedInput.getPaletteEntry().getTypeLibrary();
		}
		super.init(site, input);
		setPartName(FordiacMessages.Interface);
		setTitleImage(FordiacImage.ICON_INTERFACE_EDITOR.getImage());
	}

	@Override
	protected void setModel(final IEditorInput input) {
		super.setModel(input);
		setEditDomain(new FBTypeEditDomain(this, commandStack));
	}

	@Override
	protected void createActions() {
		final ActionRegistry registry = getActionRegistry();
		InterfaceContextMenuProvider.createInterfaceEditingActions(this, registry, getModel());
		super.createActions();
	}

	@Override
	protected EditPartFactory getEditPartFactory() {
		return new FBInterfaceEditPartFactory(this, typeLib);
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		if (null == paletteRoot) {
			paletteRoot = FBInterfacePaletteFactory.createPalette(typeLib);
		}
		return paletteRoot;
	}

	protected TypeLibrary getTypeLib() {
		return typeLib;
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// currently nothing needs to be done here
	}

	@Override
	public boolean outlineSelectionChanged(final Object selectedElement) {
		final Object editpart = getGraphicalViewer().getEditPartRegistry().get(selectedElement);
		getGraphicalViewer().flush();
		if (editpart instanceof EditPart && ((EditPart) editpart).isSelectable()) {
			getGraphicalViewer().select((EditPart) editpart);
			return true;
		}
		return (selectedElement instanceof InterfaceList);
	}

	@Override
	public void setCommonCommandStack(final CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	@Override
	protected FlyoutPreferences getPalettePreferences() {
		return FBInterfacePaletteFactory.PALETTE_PREFERENCES;
	}

	/**
	 * Override so that we can add a template transferdragsourcelistener for drag
	 * and drop
	 */
	@Override
	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new PaletteViewerProvider(getEditDomain()) {
			@Override
			protected void configurePaletteViewer(final PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);
				viewer.addDragSourceListener(new TemplateTransferDragSourceListener(viewer));
			}
		};
	}

	@Override
	public FBType getModel() {
		return fbType;
	}

	@Override
	protected ContextMenuProvider getContextMenuProvider(final ScrollingGraphicalViewer viewer, final ZoomManager zoomManager) {
		return new InterfaceContextMenuProvider(viewer, zoomManager, getActionRegistry(), typeLib.getDataTypeLibrary());
	}

	@Override
	protected TransferDropTargetListener createTransferDropTargetListener() {
		// we don't need an additional transferdroptarget listener
		return null;
	}

	@Override
	public AutomationSystem getSystem() {
		return null; // this is currently needed as the base class is targeted for system editors
	}

	@Override
	public void doSaveAs() {
		// nothing to do here
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
		fbType = type;
		getGraphicalViewer().setContents(fbType);

	}

}
