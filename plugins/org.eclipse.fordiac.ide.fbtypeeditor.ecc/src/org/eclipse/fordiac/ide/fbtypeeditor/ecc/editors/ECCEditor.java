/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 				            Johannes Kepler University Linz
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
 *   Alois Zoitl - changed inheritence to DiagramEditorWithFlyoutPalette,
 *                 removed duplicated code
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editors;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FreeformFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.AddECCActionAction;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.DeleteECCAction;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.ECCSelectAllAction;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.NewStateAction;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithm;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECCEditPartFactory;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.gef.FordiacContextMenuProvider;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.figures.AbstractFreeformFigure;
import org.eclipse.fordiac.ide.gef.figures.ModuloFreeformFigure;
import org.eclipse.fordiac.ide.gef.tools.AdvancedMarqueeDragTracker;
import org.eclipse.fordiac.ide.gef.tools.AdvancedPanningSelectionTool;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.provider.ECCItemProvider;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.tools.MarqueeSelectionTool;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

/**
 * The Class ECCEditor.
 */
public class ECCEditor extends DiagramEditorWithFlyoutPalette implements IFBTEditorPart {

	private static final class ECCEditorRootEditPart extends ZoomScalableFreeformRootEditPart {
		private ECCEditorRootEditPart(final IWorkbenchPartSite site, final ActionRegistry actionRegistry) {
			super(site, actionRegistry);
		}

		@Override
		public DragTracker getDragTracker(final Request req) {
			final AdvancedMarqueeDragTracker dragTracker = new AdvancedMarqueeDragTracker();
			dragTracker.setMarqueeBehavior(MarqueeSelectionTool.BEHAVIOR_NODES_CONTAINED_AND_RELATED_CONNECTIONS);
			return dragTracker;
		}

		@Override
		protected AbstractFreeformFigure createDrawingAreaContainer() {
			return new ModuloFreeformFigure(this) {

				@Override
				protected Rectangle getUnscaledContentsExtent() {
					final Rectangle contentsExtent = super.getUnscaledContentsExtent();
					contentsExtent.union(((FreeformFigure) getZoomScalableFreeformRootEditPart()
							.getLayer(LayerConstants.CONNECTION_LAYER)).getFreeformExtent());
					return contentsExtent;
				}

			};
		}
	}

	@Override
	public BasicFBType getType() {
		return (BasicFBType) IFBTEditorPart.super.getType();
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		super.init(site, input);
		setPartName(Messages.ECCEditor_LABEL_ECCEditorTabName);
		setTitleImage(FordiacImage.ICON_ECC.getImage());
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		final ScrollingGraphicalViewer viewer = getGraphicalViewer();

		// set the control for the new state action so that it can get the correct
		// position for state creation
		final IAction action = getActionRegistry().getAction(NewStateAction.CREATE_STATE);
		((NewStateAction) action).setViewerControl((FigureCanvas) viewer.getControl());
		((NewStateAction) action).setZoomManager(getZoomManger());
	}

	@Override
	protected KeyHandler getCommonKeyHandler() {
		final KeyHandler keyHandler = super.getCommonKeyHandler();

		// bind insert key press to add action action
		keyHandler.put(KeyStroke.getPressed(SWT.INSERT, 0),
				getActionRegistry().getAction(AddECCActionAction.ADD_ECC_ACTION));
		return keyHandler;
	}

	@Override
	protected ContextMenuProvider getContextMenuProvider(final ScrollingGraphicalViewer viewer,
			final ZoomManager zoomManager) {
		return new FordiacContextMenuProvider(viewer, zoomManager, getActionRegistry()) {
			@Override
			public void buildContextMenu(final IMenuManager menu) {
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
		return new ECCEditorRootEditPart(getSite(), getActionRegistry());
	}

	/** The palette root. */
	private PaletteRoot paletteRoot;

	@Override
	protected PaletteRoot getPaletteRoot() {
		if (null == paletteRoot) {
			paletteRoot = ECCPaletteFactory.createPalette();
		}
		return paletteRoot;
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// we do not need to do anything on save
	}

	@Override
	protected void createActions() {
		final ActionRegistry registry = getActionRegistry();
		IAction action;

		action = new NewStateAction(this);
		registry.registerAction(action);

		action = new AddECCActionAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		super.createActions();

		// we need a special delete action that will remove ecc_actions from the list if
		// it contains also the according state
		action = registry.getAction(ActionFactory.DELETE.getId());
		registry.removeAction(action);
		getSelectionActions().remove(action.getId());

		action = new DeleteECCAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		// remove the select all action added in the graphical editor and replace it
		// with our
		action = registry.getAction(ActionFactory.SELECT_ALL.getId());
		registry.removeAction(action);
		getSelectionActions().remove(action.getId());

		action = new ECCSelectAllAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
	}

	@Override
	public boolean outlineSelectionChanged(final Object selectedElement) {
		if (getGraphicalViewer().getEditPartForModel(selectedElement) instanceof final EditPart ep) {
			getGraphicalViewer().select(ep);
			return true;
		}
		if (selectedElement instanceof ECCItemProvider) {
			return true;
		}
		if (selectedElement instanceof final ECAction ecAction) {
			handleActionOutlineSelection(ecAction);
			return true;
		}

		return false;
	}

	private void handleActionOutlineSelection(final ECAction action) {
		Object obj = getGraphicalViewer().getEditPartForModel(action.getECState());
		if (null != obj) {
			for (final Object element : ((ECStateEditPart) obj).getCurrentChildren()) {
				if ((element instanceof final ECActionAlgorithm ecAlg) && (action.equals(ecAlg.getAction()))) {
					obj = getGraphicalViewer().getEditPartForModel(element);
					if (null != obj) {
						getGraphicalViewer().select((EditPart) obj);
						break;
					}
				}
			}
		}
	}

	private CommandStack commandStack;

	@Override
	protected DefaultEditDomain createEditDomain() {
		return new ECCEditorEditDomain(this, commandStack);
	}

	@Override
	protected AdvancedPanningSelectionTool createDefaultTool() {
		return new ECCEditorEditDomain.ECCPanningSelectionTool();
	}

	@Override
	public void setCommonCommandStack(final CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	@Override
	protected FlyoutPreferences getPalettePreferences() {
		return ECCPaletteFactory.PALETTE_PREFERENCES;
	}

	@Override
	public ECC getModel() {
		return getType().getECC();
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
		return null; // this is currently needed as the base class is targeted for system editors
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == IContentOutlinePage.class) {
			return null; // use outline page from FBTypeEditor
		}
		if (adapter == ECC.class) {
			return adapter.cast(getModel());
		}
		return super.getAdapter(adapter);
	}

	@Override
	public void doSaveAs() {
		// nothing to do here
	}

	private static final String UNKNOWN_LINE = "Unknown"; //$NON-NLS-1$

	@Override
	public void gotoMarker(final IMarker marker) {
		final Map<Object, EditPart> map = getGraphicalViewer().getEditPartRegistry();
		final String lineNumber = marker.getAttribute(IMarker.LINE_NUMBER, UNKNOWN_LINE);
		if (!UNKNOWN_LINE.equals(lineNumber)) {
			final int hashCode = Integer.parseInt(lineNumber);
			for (final Object key : map.keySet()) {
				if (key.hashCode() == hashCode) {
					final EditPart ep = getGraphicalViewer().getEditPartForModel(key);
					if (ep != null) {
						getGraphicalViewer().select(ep);
						break;
					}
				}
			}
		}
	}

	@Override
	public boolean isMarkerTarget(final IMarker marker) {
		return marker.getAttribute(IMarker.LOCATION, UNKNOWN_LINE).startsWith("ECC"); //$NON-NLS-1$
	}

	@Override
	public void reloadType() {
		getGraphicalViewer().setContents(getModel());
	}

	@Override
	public Object getSelectableObject() {
		if (getGraphicalViewer() == null) {
			return null;
		}
		return getGraphicalViewer().getEditPartForModel(getModel());
	}

}
