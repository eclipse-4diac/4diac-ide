/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *   Alois Zoitl   - added event repetation toolbar
 *   Lukas Leimeister - added test recorder interface
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui.view;

import java.util.Iterator;

import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.contexts.DebugContextEvent;
import org.eclipse.debug.ui.contexts.IDebugContextListener;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FreeformLayeredPane;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RangeModel;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Translatable;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugElement;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugTarget;
import org.eclipse.fordiac.ide.debug.EvaluatorProcess;
import org.eclipse.fordiac.ide.debug.ui.view.actions.RepeatEventAction;
import org.eclipse.fordiac.ide.gef.FordiacContextMenuProvider;
import org.eclipse.fordiac.ide.gef.figures.AbstractFreeformFigure;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluator;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.gef.editparts.GridLayer;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.DirectEditAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.UpdateAction;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

public class FBDebugView extends ViewPart implements IDebugContextListener, ISelectionListener {

	public static final class ZeroOffestFreeformCanvas extends AbstractFreeformFigure {
		private Point contentOffset;

		@Override
		protected Rectangle calculateFreeformExtent() {
			final Rectangle newExtents = getContents().getFreeformExtent().getCopy();
			contentOffset = newExtents.getTopLeft();
			newExtents.x = 0;
			newExtents.y = 0;
			return newExtents;
		}

		@Override
		protected void setChildBounds(final Rectangle childBounds) {
			childBounds.x = contentOffset.x;
			childBounds.y = contentOffset.y;
			super.setChildBounds(childBounds);
		}

		@Override
		protected void paintChildren(final Graphics graphics) {
			graphics.translate(-contentOffset.x, -contentOffset.y);
			super.paintChildren(graphics);
		}

		@Override
		public void translateFromParent(final Translatable t) {
			t.performTranslate(contentOffset.x, contentOffset.y);
		}

		@Override
		public void translateToParent(final Translatable t) {
			t.performTranslate(-contentOffset.x, -contentOffset.y);
		}

		@Override
		public Rectangle getClientArea(final Rectangle rect) {
			final Rectangle clientArea = super.getClientArea(rect);
			clientArea.translate(contentOffset);
			return clientArea;
		}

	}

	private GraphicalViewer viewer;
	private ActionRegistry actionRegistry;
	private KeyHandler sharedKeyHandler;
	private RepeatEventAction repeatEventAction;
	private final FBDebugViewClockWidget clockWidget = new FBDebugViewClockWidget();

	@Override
	public void createPartControl(final Composite parent) {
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(this);
		GridLayoutFactory.fillDefaults().applyTo(parent);
		createGraphicalViewer(parent);
		final Composite clockControl = clockWidget.createControl(parent);
		clockControl.setVisible(false);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(clockControl);
		createToolBarEntries();
		hookDebugListeners();
	}

	private void hookDebugListeners() {
		DebugUITools.addPartDebugContextListener(getSite(), this);
	}

	private void createToolBarEntries() {
		final IActionBars actionBars = getViewSite().getActionBars();
		final IToolBarManager toolBar = actionBars.getToolBarManager();
		toolBar.add(createRepeatEventAction());
	}

	private IAction createRepeatEventAction() {
		repeatEventAction = new RepeatEventAction();
		return repeatEventAction;
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		// If not the active editor, ignore selection changed.
		if (this.equals(getSite().getPage().getActivePart())) {
			updateActions();
		}
	}

	private void createGraphicalViewer(final Composite parent) {
		viewer = new ScrollingGraphicalViewer();
		viewer.createControl(parent);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(viewer.getControl());
		// needed to get selection working
		final EditDomain editDomain = new EditDomain();
		editDomain.addViewer(viewer);

		configureGraphicalViewer();
		initializeGraphicalViewer();
		hookGraphicalViewer();
	}

	private void configureGraphicalViewer() {
		viewer.getControl().setBackground(ColorConstants.listBackground);
		final ScalableFreeformRootEditPart root = createRootEditPart();
		viewer.setRootEditPart(root);
		viewer.setEditPartFactory(new FBDebugViewEditPartFactory());
		viewer.setContextMenu(new FordiacContextMenuProvider(viewer, root.getZoomManager(), getActionRegistry()));
		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1), MouseWheelZoomHandler.SINGLETON);
		final KeyHandler viewerKeyHandler = new GraphicalViewerKeyHandler(viewer).setParent(getCommonKeyHandler());
		viewer.setKeyHandler(viewerKeyHandler);

	}

	private void initializeGraphicalViewer() {
		// Placing a listener and setting the appropriate content
	}

	private void hookGraphicalViewer() {
		getSite().setSelectionProvider(viewer);
	}

	private static ScalableFreeformRootEditPart createRootEditPart() {
		return new ScalableFreeformRootEditPart() {

			@Override
			protected IFigure createFigure() {
				final FreeformViewport viewPort = (FreeformViewport) super.createFigure();

				getLayer(PRIMARY_LAYER).setLayoutManager(new FreeformLayout());

				final GridLayer grid = (GridLayer) getLayer(GRID_LAYER);
				if (grid != null) {
					// it does not make sense to have a grid in the interface layer so hide it
					grid.setVisible(false);
				}

				final FreeformLayeredPane drawingArea = (FreeformLayeredPane) viewPort.getContents();

				final AbstractFreeformFigure editorBackground = new ZeroOffestFreeformCanvas();
				viewPort.setContents(editorBackground);
				editorBackground.setContents(drawingArea);
				return viewPort;
			}

			@Override
			protected void refreshGridLayer() {
				// No grid
			}
		};
	}

	private ActionRegistry getActionRegistry() {
		if (actionRegistry == null) {
			actionRegistry = createActionRegistry();
		}
		return actionRegistry;
	}

	@Override
	public void dispose() {
		DebugUITools.removePartDebugContextListener(getSite(), this);
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(this);
	}

	@Override
	public <T> T getAdapter(final Class<T> type) {
		if (type == ActionRegistry.class) {
			return type.cast(getActionRegistry());
		}
		return super.getAdapter(type);
	}

	private KeyHandler getCommonKeyHandler() {
		if (sharedKeyHandler == null) {
			sharedKeyHandler = new KeyHandler();
			sharedKeyHandler.put(KeyStroke.getPressed(SWT.F2, 0),
					getActionRegistry().getAction(GEFActionConstants.DIRECT_EDIT));
		}
		return sharedKeyHandler;
	}

	@Override
	public void setFocus() {
		// Nothing for now
	}

	@Override
	public void debugContextChanged(final DebugContextEvent event) {
		if ((event.getFlags() & DebugContextEvent.ACTIVATED) > 0) {
			contextActivated(event.getContext());
		}
	}

	private void contextActivated(final ISelection selection) {
		if (selection instanceof final IStructuredSelection structSel) {
			final Object source = structSel.getFirstElement();
			if (source == null) {
				setContents(null);
				clockWidget.getControl().setVisible(false);
			} else {
				final EvaluatorProcess evaluator = getFBEvaluatorDebugContext(source);
				clockWidget.getControl().setVisible(true);
				if (!isViewerContent(evaluator)) {
					setContents(evaluator);
				}
			}
		}
	}

	private void setContents(final EvaluatorProcess evaluator) {
		viewer.setContents(evaluator);
		repeatEventAction.updateEvaluator(evaluator);
		clockWidget.setProcess(evaluator);
		clockWidget.refresh(true);
		setScrollPosition();
	}

	private boolean isViewerContent(final EvaluatorProcess evaluator) {
		final EditPart content = viewer.getContents();
		return (content != null && content.getModel() == evaluator) || evaluator == null;
	}

	private static EvaluatorProcess getFBEvaluatorDebugContext(final Object source) {
		Object evaluatorProcess = source;
		if (evaluatorProcess instanceof final EvaluatorDebugElement evalDebugElem) {
			evaluatorProcess = evalDebugElem.getDebugTarget();
		}
		if (evaluatorProcess instanceof final EvaluatorDebugTarget evalDebugTarget) {
			evaluatorProcess = evalDebugTarget.getProcess();
		}

		if (evaluatorProcess instanceof final EvaluatorProcess ep && ep.getEvaluator() instanceof FBEvaluator<?>) {
			return ep;
		}
		return null;
	}

	private void setScrollPosition() {
		if (viewer.getControl() instanceof final FigureCanvas canvas) {
			Display.getDefault().asyncExec(() -> {
				if (!canvas.isDisposed()) {
					viewer.flush();
					if (viewer.getSelectedEditParts().isEmpty()) {
						final Point scrollPos = getInitialScrollPos(viewer);
						canvas.scrollTo(scrollPos.x, scrollPos.y);
					}
				}
			});
		}
	}

	private static Point getInitialScrollPos(final GraphicalViewer viewer) {
		final FreeformGraphicalRootEditPart rootEditPart = (FreeformGraphicalRootEditPart) viewer.getRootEditPart();
		final FreeformViewport rootviewPort = (FreeformViewport) rootEditPart.getFigure();
		return new Point(calculateCenterScrollPos(rootviewPort.getHorizontalRangeModel()),
				calculateCenterScrollPos(rootviewPort.getVerticalRangeModel()));
	}

	private static int calculateCenterScrollPos(final RangeModel rangeModel) {
		final int center = (rangeModel.getMaximum() + rangeModel.getMinimum()) / 2;
		return center - rangeModel.getExtent() / 2;
	}

	private ActionRegistry createActionRegistry() {
		final ActionRegistry newAR = new ActionRegistry();
		final var action = new DirectEditAction(this);
		newAR.registerAction(action);
		return newAR;
	}

	private void updateActions() {
		final ActionRegistry registry = getActionRegistry();
		final Iterator<IAction> actionIter = registry.getActions();
		while (actionIter.hasNext()) {
			final IAction action = actionIter.next();
			if (action instanceof final UpdateAction updateAction) {
				updateAction.update();
			}
		}
	}

}
