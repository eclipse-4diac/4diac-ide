/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.network;

import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.actions.UnmapAction;
import org.eclipse.fordiac.ide.application.actions.UnmapAllAction;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.application.editors.FBTypePaletteViewerProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.FBTypeEditDomain;
import org.eclipse.fordiac.ide.fbtypeeditor.contentprovider.InterfaceContextMenuProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeNetworkEditPartFactory;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPart;

public class CompositeNetworkEditor extends FBNetworkEditor implements IFBTEditorPart {

	protected CompositeFBType fbType;
	protected CommandStack commandStack;
	protected Palette palette;
	protected EContentAdapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			// only refresh propertypage (page) if the event is not an
			// REMOVING_ADAPTER event - otherwise, the remove adapter in the
			// dispose method (called when closing the editor) will fail
			if (notification.getEventType() != Notification.REMOVING_ADAPTER
					&& (((notification.getNewValue() == null) && (notification
							.getNewValue() != notification.getOldValue())) || ((notification
							.getNewValue() != null) && !(notification
							.getNewValue().equals(notification.getOldValue()))))) {
				super.notifyChanged(notification);
			}

		}
	};
	
	@Override
	public void setCommonCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	@Override
	protected CompositeNetworkEditPartFactory getEditPartFactory() {
		return new CompositeNetworkEditPartFactory(this, getZoomManger() );
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part,
			final ISelection selection) {
		super.selectionChanged(part, selection);
		updateActions(getSelectionActions());
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		//currently nothing needs to be done here
	}

	@Override
	public void dispose() {
		getModel().eAdapters().remove(adapter);
		super.dispose();
	}

	@Override
	protected void createActions() {
		ActionRegistry registry = getActionRegistry();
		IAction action;
		
		super.createActions();

		action = registry.getAction(UnmapAction.ID);
		registry.removeAction(action);
		
		action = registry.getAction(UnmapAllAction.ID);
		registry.removeAction(action);
		
		InterfaceContextMenuProvider.createInterfaceEditingActions(this, registry, fbType);
		
		super.createActions();
	}
	
	@Override
	public boolean outlineSelectionChanged(Object selectedElement) {

		EditPart editPart = getEditPartForSelection(selectedElement);
		if (null != editPart) {
			getGraphicalViewer().select(editPart);
			return true;
		}
		if (selectedElement instanceof FBNetwork) {
			return true;
		}

		return false;
	}

	EditPart getEditPartForSelection(Object selectedElement) {
		@SuppressWarnings("rawtypes")
		Map map = getGraphicalViewer().getEditPartRegistry();

		for (Object key : map.keySet()) {
			if (key instanceof FB && ((FB) key) == selectedElement) {
				selectedElement = key;
				break;
			}
			if (key instanceof Connection && ((Connection) key) == selectedElement) {
				selectedElement = key;
				break;
			}
		}

		Object obj = getGraphicalViewer().getEditPartRegistry().get(selectedElement);
		if (obj instanceof EditPart) {
			return (EditPart) obj;
		}
		return null;
	}

	@Override
	protected Palette getPalette() {
		return palette;
	}

	@Override
	public AutomationSystem getSystem() {
		return palette.getAutomationSystem();
	}

	@Override
	protected void setModel(IEditorInput input) {
		if (input instanceof FBTypeEditorInput) {
			FBTypeEditorInput untypedInput = (FBTypeEditorInput) input;
			if (untypedInput.getContent() instanceof CompositeFBType) {
				fbType = (CompositeFBType) untypedInput.getContent();
				setModel(fbType.getFBNetwork());
				getModel().eAdapters().add(adapter);
				configurePalette(untypedInput);
			}
		}

		setPartName(Messages.CompositeNetworkEditor_LABEL_CompositeEditor);
		setTitleImage(FordiacImage.ICON_FBNetwork.getImage());

		setEditDomain(new FBTypeEditDomain(this, commandStack));
	}

	protected void configurePalette(FBTypeEditorInput fbTypeEditorInput) {
		Palette fbPallette = fbTypeEditorInput.getPaletteEntry().getGroup().getPallete();
		if (null != fbPallette) {
			palette = fbPallette;
		}
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		return null;  //we are filling a the palete directly in the viewer so we don't need it here
	}

	
	@Override
	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new FBTypePaletteViewerProvider(fbType.getPaletteEntry().getFile().getProject(), getEditDomain(), getPalletNavigatorID());
	}
	
	@Override
	protected String getPalletNavigatorID() {
		return "org.eclipse.fordiac.ide.compositefbpaletteviewer"; //$NON-NLS-1$
	}
	
	@Override
	protected ContextMenuProvider getContextMenuProvider(
			final ScrollingGraphicalViewer viewer,
			final ZoomManager zoomManager) {
		return new CFBNetworkcontextMenuProvider(this, getActionRegistry(), zoomManager, getPalette());
	}
}
