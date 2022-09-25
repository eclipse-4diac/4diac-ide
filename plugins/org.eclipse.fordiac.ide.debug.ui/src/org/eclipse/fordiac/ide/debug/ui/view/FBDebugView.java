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

import java.util.List;

import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.contexts.DebugContextEvent;
import org.eclipse.debug.ui.contexts.IDebugContextListener;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugElement;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugTarget;
import org.eclipse.fordiac.ide.gef.FordiacContextMenuProvider;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.ViewPart;

public class FBDebugView extends ViewPart implements IDebugContextListener {

	private GraphicalViewer viewer;
	private ActionRegistry actionRegistry;
	private static final int NUM_COLUMNS = 1;
	private KeyHandler sharedKeyHandler;

	@Override
	public void createPartControl(final Composite parent) {
		GridLayoutFactory.fillDefaults().numColumns(NUM_COLUMNS).margins(LayoutConstants.getMargins())
		.generateLayout(parent);

		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(NUM_COLUMNS).margins(LayoutConstants.getMargins())
		.generateLayout(composite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);

		createGraphicalViewer(composite);
		setTitleImage(FordiacImage.ICON_BASIC_FB.getImage());
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
			protected ScalableFreeformLayeredPane createScaledLayers() {
				final ScalableFreeformLayeredPane layers = new ScalableFreeformLayeredPane();
				// Removed the grid
				layers.add(getPrintableLayers(), PRINTABLE_LAYERS);
				// TODO: layers.add(new FeedbackLayer(), SCALED_FEEDBACK_LAYER);
				return layers;
			}

			@Override
			protected void refreshGridLayer() {
				// No grid
			}

			// This is to overwrite the comments + add buttons to the viewer
			@Override
			protected List getModelChildren() {
				return super.getModelChildren();
			}

			@Override
			protected void refreshChildren() {
				super.refreshChildren();
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

	/** Updates actions and sets the viewer input when a context is activated.
	 *
	 * @param selection New selection to activate. */
	protected void contextActivated(final ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			final Object source = ((IStructuredSelection) selection).getFirstElement();
			final FBType type = getFBTypeFromDebugContext(source);

			if (!isViewerContent(type)) {
				viewer.setContents(type);
			}
		}
	}

	private boolean isViewerContent(final FBType type) {
		final EditPart content = viewer.getContents();
		return (content != null && content.getModel() == type) || type == null;
	}

	private static FBType getFBTypeFromDebugContext(final Object source) {
		if (source instanceof EvaluatorDebugTarget) {
			final Object sourceElement = ((EvaluatorDebugTarget) source).getProcess().getEvaluator().getSourceElement();
			if (sourceElement instanceof FBType) {
				return (FBType) sourceElement;
			}
		} else if (source instanceof EvaluatorDebugElement) {
			return getFBTypeFromDebugContext(((EvaluatorDebugElement) source).getDebugTarget());
		}
		return null;
	}

}
