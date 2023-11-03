/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger, Filip Andren, Ingo Hegny
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr, Melanie Winter
 *     - change canvas, fix problem with size calculation when dragging elements
 *   Felix Roithmayr
 *     - added support for new commands and context menu items
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.RangeModel;
import org.eclipse.fordiac.ide.fbtypeeditor.FBTypeEditDomain;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.FBTypeEditor;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.InputPrimitiveEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.OutputPrimitiveEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.SequenceRootEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.ServiceSequenceEditPartFactory;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.helpers.ServiceSequenceSaveAndLoadHelper;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.gef.FordiacContextMenuProvider;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.figures.AbstractFreeformFigure;
import org.eclipse.fordiac.ide.gef.figures.ModuloFreeformFigure;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;

public class ServiceSequenceEditor extends DiagramEditorWithFlyoutPalette implements IFBTEditorPart {

	private FBType fbType;
	private CommandStack commandStack;

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		setInputWithNotify(input);
		if (input instanceof final FBTypeEditorInput untypedInput) {
			fbType = untypedInput.getContent();
		}
		super.init(site, input);
		setPartName(Messages.ServiceSequenceEditor_Service);
		setTitleImage(FordiacImage.ICON_SERVICE_SEQUENCE.getImage());
		setServiceSequences();
	}

	@Override
	protected DefaultEditDomain createEditDomain() {
		return new FBTypeEditDomain(this, commandStack);
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		super.selectionChanged(part, selection);
		// If not in FBTypeEditor ignore selection changed
		if (part.getSite().getPage().getActiveEditor() instanceof FBTypeEditor) {
			updateActions(getSelectionActions());
			if (!selection.isEmpty() && selection instanceof final IStructuredSelection sel) {
				if (sel.getFirstElement() instanceof SequenceRootEditPart) {
					((FBType) ((SequenceRootEditPart) sel.getFirstElement()).getModel()).getService();
				} else if (sel.getFirstElement() instanceof OutputPrimitiveEditPart) {
					((OutputPrimitiveEditPart) sel.getFirstElement()).getModel();
				} else if (sel.getFirstElement() instanceof InputPrimitiveEditPart) {
					((InputPrimitiveEditPart) sel.getFirstElement()).getModel();
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
		// nothing to do here (handled by the FBType editor)
	}

	@Override
	public boolean outlineSelectionChanged(final Object selectedElement) {
		if (null != selectedElement) {
			final Object editpart = getGraphicalViewer().getEditPartRegistry().get(selectedElement);
			getGraphicalViewer().flush();
			if (editpart instanceof EditPart && ((EditPart) editpart).isSelectable()) {
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
	public void setCommonCommandStack(final CommandStack commandStack) {
		this.commandStack = commandStack;
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
		this.fbType = type;
		getGraphicalViewer().setContents(fbType);
	}

	@Override
	public Object getModel() {
		return fbType;
	}

	@Override
	protected EditPartFactory getEditPartFactory() {
		return new ServiceSequenceEditPartFactory(this);
	}

	@Override
	protected ContextMenuProvider getContextMenuProvider(final ScrollingGraphicalViewer viewer,
			final ZoomManager zoomManager) {
		return new FordiacContextMenuProvider(viewer, zoomManager, getActionRegistry()) {
			@Override
			public void buildContextMenu(final IMenuManager menu) {
				super.buildContextMenu(menu);
				final IAction action = getRegistry().getAction(ActionFactory.DELETE.getId());
				menu.appendToGroup(GEFActionConstants.GROUP_COPY, action);
			}
		};
	}

	@Override
	protected ScalableFreeformRootEditPart createRootEditPart() {
		return new ZoomScalableFreeformRootEditPart(getSite(), getActionRegistry()) {
			@Override
			protected AbstractFreeformFigure createDrawingAreaContainer() {
				return new ModuloFreeformFigure(this, false);
			}
		};
	}

	@Override
	protected Point getInitialScrollPos(final GraphicalEditPart rootEditPart) {
		final FreeformViewport rootviewPort = (FreeformViewport) rootEditPart.getFigure();
		return new Point(calculateTopLeftScrollPosition(rootviewPort.getHorizontalRangeModel()),
				calculateTopLeftScrollPosition(rootviewPort.getVerticalRangeModel()));
	}

	private static int calculateTopLeftScrollPosition(final RangeModel rangeModel) {
		return rangeModel.getExtent();
	}

	@Override
	protected TransferDropTargetListener createTransferDropTargetListener() {
		// we don't need an additional transferdroptarget listener
		return null;
	}

	@Override
	public AutomationSystem getSystem() {
		// this is not needed for type editor
		return null;
	}

	@Override
	public void doSaveAs() {
		// nothing to do here
	}

	@Override
	public Object getSelectableEditPart() {
		if (getGraphicalViewer() == null) {
			return null;
		}
		return getGraphicalViewer().getRootEditPart();
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == CommandStack.class) {
			return adapter.cast(commandStack);
		}
		if (adapter == Service.class) {
			return adapter.cast(fbType.getService());
		}
		return super.getAdapter(adapter);
	}

	void setServiceSequences() {
		fbType.getService().getServiceSequence()
				.addAll(ServiceSequenceSaveAndLoadHelper.loadServiceSequencesFromFile(fbType));

	}
}
