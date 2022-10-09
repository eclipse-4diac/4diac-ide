/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui.view;

import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.contexts.DebugContextEvent;
import org.eclipse.debug.ui.contexts.IDebugContextListener;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RangeModel;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugElement;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugTarget;
import org.eclipse.fordiac.ide.debug.EvaluatorProcess;
import org.eclipse.fordiac.ide.gef.FordiacContextMenuProvider;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluator;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
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
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.ViewPart;

public class FBDebugView extends ViewPart implements IDebugContextListener {

	private GraphicalViewer viewer;
	private ActionRegistry actionRegistry;
	private static final int NUM_COLUMNS = 1;
	private KeyHandler sharedKeyHandler;

	@Override
	public void createPartControl(final Composite parent) {
		GridLayoutFactory.fillDefaults().numColumns(NUM_COLUMNS).margins(0, 0).generateLayout(parent);
		createGraphicalViewer(parent);
	}

	private void createGraphicalViewer(final Composite parent) {
		viewer = new ScrollingGraphicalViewer();
		viewer.createControl(parent);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(viewer.getControl());
		configureGraphicalViewer();
		initializeGraphicalViewer();
		hookGraphicalViewer();
		DebugUITools.addPartDebugContextListener(getSite(), this);
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
				final IFigure rootFigure = super.createFigure();
				final GridLayer grid = (GridLayer) getLayer(GRID_LAYER);
				if (grid != null) {
					// it does not make sense to have a grid in the interface layer so hide it
					grid.setVisible(false);
				}
				return rootFigure;
			}

			@Override
			protected void refreshGridLayer() {
				// No grid
			}
		};
	}

	private ActionRegistry getActionRegistry() {
		if (actionRegistry == null) {
			actionRegistry = new ActionRegistry();
		}
		return actionRegistry;
	}

	@Override
	public void dispose() {
		DebugUITools.removePartDebugContextListener(getSite(), this);
	}

	private KeyHandler getCommonKeyHandler() {
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
		if (selection instanceof IStructuredSelection) {
			final Object source = ((IStructuredSelection) selection).getFirstElement();
			final FBEvaluator<? extends FBType> evaluator = getFBEvaluatorDebugContext(source);

			if (!isViewerContent(evaluator)) {
				setContents(evaluator);
			}
		}
	}

	private void setContents(final FBEvaluator<? extends FBType> evaluator) {
		viewer.setContents(evaluator);
		setScrollPosition();
	}

	private boolean isViewerContent(final FBEvaluator<? extends FBType> evaluator) {
		final EditPart content = viewer.getContents();
		return (content != null && content.getModel() == evaluator) || evaluator == null;
	}

	private static FBEvaluator<? extends FBType> getFBEvaluatorDebugContext(final Object source) {
		Object evaluatorProcess = source;
		if (evaluatorProcess instanceof EvaluatorDebugElement) {
			evaluatorProcess = ((EvaluatorDebugElement) evaluatorProcess).getDebugTarget();
		}
		if (evaluatorProcess instanceof EvaluatorDebugTarget) {
			evaluatorProcess = ((EvaluatorDebugTarget) evaluatorProcess).getProcess();
		}

		if (evaluatorProcess instanceof EvaluatorProcess) {
			final Evaluator evaluator = ((EvaluatorProcess) evaluatorProcess).getEvaluator();
			if (evaluator instanceof FBEvaluator<?>) {
				return (FBEvaluator<?>) evaluator;
			}
		}
		return null;
	}

	private void setScrollPosition() {
		if (viewer.getControl() instanceof FigureCanvas) {
			Display.getDefault().asyncExec(() -> {
				final FigureCanvas canvas = (FigureCanvas) viewer.getControl();
				if (canvas != null && !canvas.isDisposed()) {
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

}
