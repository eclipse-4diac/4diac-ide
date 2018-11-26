/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger, Filip Andren, Ingo Hegny
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.fbtypeeditor.FBTypeEditDomain;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.FBTypeEditor;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.InputPrimitiveEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.OutputPrimitiveEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.SequenceRootEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.ServiceSequenceEditPartFactory;
import org.eclipse.fordiac.ide.gef.ZoomUndoRedoContextMenuProvider;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;

public class ServiceSequenceEditor extends GraphicalEditorWithFlyoutPalette implements IFBTEditorPart {

	private FBType fbType;
	private KeyHandler sharedKeyHandler;
	private CommandStack commandStack;


	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		setInputWithNotify(input);
		if (input instanceof FBTypeEditorInput) {
			FBTypeEditorInput untypedInput = (FBTypeEditorInput) input;
			fbType = untypedInput.getContent();
		}
		setSite(site);
		setEditDomain(new FBTypeEditDomain(this, commandStack));
		setPartName("Service");
		setTitleImage(FordiacImage.ICON_ServiceSequence.getImage());
		super.init(site, input);
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		ScrollingGraphicalViewer viewer = (ScrollingGraphicalViewer) getGraphicalViewer();
		ScalableFreeformRootEditPart root = new ZoomScalableFreeformRootEditPart(getSite(), getActionRegistry());
		viewer.setRootEditPart(root);
		viewer.setEditPartFactory(new ServiceSequenceEditPartFactory(this));
		// configure the context menu provider
		ContextMenuProvider cmProvider = new ZoomUndoRedoContextMenuProvider(viewer, root.getZoomManager(),
				getActionRegistry()) {
			@Override
			public void buildContextMenu(IMenuManager menu) {
				super.buildContextMenu(menu);
				IAction action = registry.getAction(ActionFactory.DELETE.getId());
				menu.appendToGroup(GEFActionConstants.GROUP_COPY, action);
			}
		};
		viewer.setContextMenu(cmProvider);
		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1), MouseWheelZoomHandler.SINGLETON);
		KeyHandler viewerKeyHandler = new GraphicalViewerKeyHandler(viewer).setParent(getCommonKeyHandler());
		viewer.setKeyHandler(viewerKeyHandler);
	}

	@Override
	public void createPartControl(final Composite parent) {
		Composite graphicaEditor = new Composite(parent, SWT.NONE);
		graphicaEditor.setLayout(new FillLayout());
		super.createPartControl(graphicaEditor);
	}

	@Override
	protected void initializeGraphicalViewer() {
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setContents(fbType);
	}

	protected KeyHandler getCommonKeyHandler() {
		if (sharedKeyHandler == null) {
			sharedKeyHandler = new KeyHandler();
			sharedKeyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0),
					getActionRegistry().getAction(ActionFactory.DELETE.getId()));
			sharedKeyHandler.put(KeyStroke.getPressed(SWT.F2, 0),
					getActionRegistry().getAction(GEFActionConstants.DIRECT_EDIT));
			sharedKeyHandler.put(/* CTRL + '=' */
					KeyStroke.getPressed('+', 0x3d, SWT.CTRL),
					getActionRegistry().getAction(GEFActionConstants.ZOOM_IN));

		}
		return sharedKeyHandler;
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		super.selectionChanged(part, selection);
		// If not in FBTypeEditor ignore selection changed
		if (part.getSite().getPage().getActiveEditor() instanceof FBTypeEditor) {
			updateActions(getSelectionActions());
			if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
				IStructuredSelection sel = (IStructuredSelection) selection;
				Object ob = null;
				if (sel.getFirstElement() instanceof SequenceRootEditPart) {
					ob = ((FBType) ((SequenceRootEditPart) sel.getFirstElement()).getModel()).getService();
				}
				// if (sel.getFirstElement() instanceof ConnectionEditPart) {
				// ob =
				// ((ConnectionEditPart)sel.getFirstElement()).getSource().getModel();
				// if(ob instanceof InputPrimitiveEditPart){
				// ob = ((InputPrimitiveEditPart)ob).getModel();
				// }
				// else if(ob instanceof OutputPrimitiveEditPart){
				// ob = ((OutputPrimitiveEditPart)ob).getModel();
				// }
				// }
				else if (sel.getFirstElement() instanceof OutputPrimitiveEditPart) {
					ob = ((OutputPrimitiveEditPart) sel.getFirstElement()).getModel();
				} else if (sel.getFirstElement() instanceof InputPrimitiveEditPart) {
					ob = ((InputPrimitiveEditPart) sel.getFirstElement()).getModel();
				}
			}
		}
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		return ServiceInterfacePaletteFactory.createPalette();
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// currently nothing needs to be done here
	}

	@Override
	public boolean outlineSelectionChanged(Object selectedElement) {
		if (null != selectedElement) {
			Object editpart = getGraphicalViewer().getEditPartRegistry().get(selectedElement);
			getGraphicalViewer().flush();
			if (editpart != null && editpart instanceof EditPart && ((EditPart) editpart).isSelectable()) {
				getGraphicalViewer().select((EditPart) editpart);
				return true;
			}
			if (selectedElement instanceof Service) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void setCommonCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;
	}
}
