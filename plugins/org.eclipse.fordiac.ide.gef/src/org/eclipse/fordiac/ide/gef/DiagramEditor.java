/*******************************************************************************
 * Copyright (c) 2008, 2023 Profactor GbmH, TU Wien ACIN, fortiss GmbH,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Waldemar Eisenmenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef;

import java.util.EventObject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.zoom.MouseLocationZoomScrollPolicy;
import org.eclipse.fordiac.ide.gef.annotation.FordiacAnnotationModelEventDispatcher;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModelListener;
import org.eclipse.fordiac.ide.gef.dnd.ParameterDropTargetListener;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.handlers.AdvancedGraphicalViewerKeyHandler;
import org.eclipse.fordiac.ide.gef.print.PrintPreviewAction;
import org.eclipse.fordiac.ide.gef.ruler.FordiacRulerComposite;
import org.eclipse.fordiac.ide.gef.tools.AdvancedPanningSelectionTool;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.ui.editors.AdvancedScrollingGraphicalViewer;
import org.eclipse.fordiac.ide.ui.editors.I4diacModelEditor;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.AlignmentAction;
import org.eclipse.gef.ui.actions.DirectEditAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.gef.ui.rulers.RulerComposite;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IReusableEditor;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.MultiPageEditorSite;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * A base editor for various graphical editors.
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public abstract class DiagramEditor extends GraphicalEditor
		implements ITabbedPropertySheetPageContributor, I4diacModelEditor, IReusableEditor {

	public static final int INITIAL_SCROLL_OFFSET = 5;

	/** The PROPERTY_CONTRIBUTOR_ID. */
	public static final String PROPERTY_CONTRIBUTOR_ID = "org.eclipse.fordiac.ide.application.editors.DiagramEditor"; //$NON-NLS-1$

	/** The shared key handler. */
	private KeyHandler sharedKeyHandler;

	/** The outline page. */
	private DiagramOutlinePage outlinePage;

	private GraphicalAnnotationModel annotationModel;
	private GraphicalAnnotationModelListener annotationModelEventDispatcher;

	/** Instantiates a new diagram editor. */
	protected DiagramEditor() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#commandStackChanged(java.util
	 * .EventObject)
	 */
	@Override
	public void commandStackChanged(final EventObject event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}

	/** refresh all child editparts when editor gets focus. */
	@Override
	public void setFocus() {
		super.setFocus();
		getGraphicalViewer().getRootEditPart().getChildren().forEach(EditPart::refresh);
	}

	@Override
	public void createPartControl(final Composite parent) {
		super.createPartControl(parent);

		final AdvancedScrollingGraphicalViewer viewer = getGraphicalViewer();
		if (viewer.getControl() instanceof FigureCanvas) {
			performInitialsationScroll(viewer);
		}
	}

	public void performInitialsationScroll(final AdvancedScrollingGraphicalViewer viewer) {
		final FigureCanvas canvas = (FigureCanvas) viewer.getControl();
		if (canvas != null && !canvas.isDisposed()) {
			viewer.flush();
			// if an editpart is selected then the viewer has bee created with something to
			// be shown centered
			// therefore we will not show the initial position
			// do not use getSelection() here because it will return always at least one
			// element
			if (viewer.getSelectedEditParts().isEmpty()) {
				final GraphicalEditPart rootEditPart = (GraphicalEditPart) viewer.getRootEditPart();
				final Point scrollPos = getInitialScrollPos(rootEditPart);
				canvas.scrollTo(scrollPos.x, scrollPos.y);
			} else {
				// if we have a selected edit part we want to show it in the middle
				viewer.revealEditPart(viewer.getSelectedEditParts().get(0));
			}
		}
	}

	@SuppressWarnings("static-method") // allow subclases to override this method
	protected Point getInitialScrollPos(final GraphicalEditPart rootEditPart) {
		final Rectangle drawingAreaBounds = rootEditPart.getContentPane().getBounds();
		return new Point(drawingAreaBounds.x - DiagramEditor.INITIAL_SCROLL_OFFSET,
				drawingAreaBounds.y - DiagramEditor.INITIAL_SCROLL_OFFSET);
	}

	@Override
	protected void createGraphicalViewer(final Composite parent) {
		final RulerComposite rulerComp = new FordiacRulerComposite(parent, SWT.NONE);

		final GraphicalViewer viewer = new AdvancedScrollingGraphicalViewer();
		viewer.createControl(rulerComp);
		setGraphicalViewer(viewer);
		configureGraphicalViewer();
		hookGraphicalViewer();
		initializeGraphicalViewer();

		rulerComp.setGraphicalViewer(getGraphicalViewer());
	}

	/**
	 * Create the root edit part used in this diagram editor. Editors which need
	 * special behavior should override this function
	 *
	 * @return the new root edit part
	 */
	protected ScalableFreeformRootEditPart createRootEditPart() {
		return new ZoomScalableFreeformRootEditPart(getSite(), getActionRegistry());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#configureGraphicalViewer()
	 */
	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		final AdvancedScrollingGraphicalViewer viewer = getGraphicalViewer();

		final ScalableFreeformRootEditPart root = createRootEditPart();

		final ContextMenuProvider cmp = getContextMenuProvider(viewer, root.getZoomManager());
		if (null != cmp) {
			viewer.setContextMenu(cmp);
			getSite().registerContextMenu("org.eclipse.fordiac.ide.gef.contextmenu", //$NON-NLS-1$
					cmp, viewer);
		}
		root.getZoomManager().setScrollPolicy(new MouseLocationZoomScrollPolicy(viewer.getControl()));

		viewer.setRootEditPart(root);
		viewer.setEditPartFactory(getEditPartFactory());

		final AdvancedGraphicalViewerKeyHandler keyHandler = new AdvancedGraphicalViewerKeyHandler(viewer);
		keyHandler.setParent(getCommonKeyHandler());
		viewer.setKeyHandler(keyHandler);

		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1), MouseWheelZoomHandler.SINGLETON);
	}

	public ZoomManager getZoomManger() {
		return ((ScalableFreeformRootEditPart) (getGraphicalViewer().getRootEditPart())).getZoomManager();
	}

	/**
	 * Gets the edits the part factory.
	 *
	 * @return the edits the part factory
	 */
	protected abstract EditPartFactory getEditPartFactory();

	/**
	 * Gets the context menu provider.
	 *
	 * @param viewer the viewer
	 * @param root   the root
	 *
	 * @return the context menu provider
	 */
	protected abstract ContextMenuProvider getContextMenuProvider(ScrollingGraphicalViewer viewer,
			ZoomManager zoomManager);

	/**
	 * Creates the transfer drop target listener.
	 *
	 * @return the transfer drop target listener
	 */
	protected abstract TransferDropTargetListener createTransferDropTargetListener();

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#initializeGraphicalViewer()
	 */
	@Override
	protected void initializeGraphicalViewer() {
		final GraphicalViewer viewer = getGraphicalViewer();
		viewer.setContents(getModel());
		// listen for dropped parts
		final TransferDropTargetListener listener = createTransferDropTargetListener();
		if (listener != null) {
			viewer.addDropTargetListener(createTransferDropTargetListener());
		}
		// enable drag from palette
		getGraphicalViewer().addDropTargetListener(new TemplateTransferDropTargetListener(getGraphicalViewer()));
		viewer.addDropTargetListener(new ParameterDropTargetListener(getGraphicalViewer()));

		addAnnotationModelDispatcher();
	}

	protected void addAnnotationModelDispatcher() {
		if (annotationModel != null && getGraphicalViewer() != null) {
			annotationModelEventDispatcher = new FordiacAnnotationModelEventDispatcher(getGraphicalViewer());
			annotationModel.addAnnotationModelListener(annotationModelEventDispatcher, true);
		}
	}

	protected void removeAnnotationModelDispatcher() {
		if (annotationModel != null && annotationModelEventDispatcher != null) {
			annotationModel.removeAnnotationModelListener(annotationModelEventDispatcher);
		}
	}

	/**
	 * Sets the model.
	 *
	 * @param input the new model
	 */
	@Override
	public void setInput(final IEditorInput input) {
		setEditDomain(createEditDomain());
		getEditDomain().setDefaultTool(createDefaultTool());
		getEditDomain().setActiveTool(getEditDomain().getDefaultTool());
		// use one "System - Wide" command stack to avoid incositensies due to
		// undo redo
		if (null != getSystem()) {
			getEditDomain().setCommandStack(getSystem().getCommandStack());
		}
		if (getSite() instanceof final MultiPageEditorSite multiPageEditorSite) {
			removeAnnotationModelDispatcher();
			annotationModel = multiPageEditorSite.getMultiPageEditor().getAdapter(GraphicalAnnotationModel.class);
			addAnnotationModelDispatcher();
		}
		super.setInputWithNotify(input);
	}

	protected DefaultEditDomain createEditDomain() {
		return new DefaultEditDomain(this);
	}

	@SuppressWarnings("static-method")
	protected AdvancedPanningSelectionTool createDefaultTool() {
		return new AdvancedPanningSelectionTool();
	}

	/**
	 * Gets the system.
	 *
	 * @return the system
	 */
	public abstract AutomationSystem getSystem();

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public abstract String getFileName();

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	public abstract void doSave(final IProgressMonitor monitor);

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		// not implemented
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/**
	 * Gets the common key handler.
	 *
	 * @return the common key handler
	 */
	protected KeyHandler getCommonKeyHandler() {
		if (sharedKeyHandler == null) {
			sharedKeyHandler = new KeyHandler();
			sharedKeyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0),
					getActionRegistry().getAction(ActionFactory.DELETE.getId()));
			sharedKeyHandler.put(KeyStroke.getPressed(SWT.F2, 0),
					getActionRegistry().getAction(GEFActionConstants.DIRECT_EDIT));
		}
		return sharedKeyHandler;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#getAdapter(
	 * java.lang.Class)
	 */
	@Override
	public <T> T getAdapter(final Class<T> type) {
		if (type == ZoomManager.class) {
			return type.cast(getGraphicalViewer().getProperty(ZoomManager.class.toString()));
		}
		if (type == IContentOutlinePage.class) {
			outlinePage = new DiagramOutlinePage(getGraphicalViewer());
			return type.cast(outlinePage);
		}
		if (type == IPropertySheetPage.class) {
			return type.cast(new TabbedPropertySheetPage(this));
		}
		if (type == GraphicalAnnotationModel.class) {
			return type.cast(annotationModel);
		}
		return super.getAdapter(type);
	}

	/**
	 * Gets the editor.
	 *
	 * @return the editor
	 */
	protected FigureCanvas getEditor() {
		return (FigureCanvas) getGraphicalViewer().getControl();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#createActions()
	 */
	@Override
	protected void createActions() {
		final ActionRegistry registry = getActionRegistry();
		IAction action;

		action = new DirectEditAction((IWorkbenchPart) this);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this, PositionConstants.LEFT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this, PositionConstants.RIGHT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this, PositionConstants.TOP);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this, PositionConstants.BOTTOM);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this, PositionConstants.CENTER);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this, PositionConstants.MIDDLE);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		super.createActions();

		// remove the default print action and register our own one
		registry.removeAction(registry.getAction(ActionFactory.PRINT.getId()));
		action = new PrintPreviewAction(getGraphicalViewer());
		registry.registerAction(action);
		getEditorSite().getActionBars().setGlobalActionHandler(ActionFactory.PRINT.getId(), action);

	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		super.selectionChanged(part, selection);
		updateActions(getSelectionActions());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.IWorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		outlinePage = null;
		removeAnnotationModelDispatcher();
		super.dispose();
	}

	/**
	 * Returns the GraphicalViewer of this Editor.
	 *
	 * @return the GraphicalViewer
	 */
	public GraphicalViewer getViewer() {
		return getGraphicalViewer();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor
	 * #getContributorId()
	 */
	@Override
	public String getContributorId() {
		return PROPERTY_CONTRIBUTOR_ID;
	}

	@Override
	protected AdvancedScrollingGraphicalViewer getGraphicalViewer() {
		return (AdvancedScrollingGraphicalViewer) super.getGraphicalViewer();
	}

}
