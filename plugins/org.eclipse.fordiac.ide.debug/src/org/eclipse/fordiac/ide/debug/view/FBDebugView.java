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
package org.eclipse.fordiac.ide.debug.view;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBInterfaceEditPartFactory;
import org.eclipse.fordiac.ide.gef.FordiacContextMenuProvider;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.figures.AbstractFreeformFigure;
import org.eclipse.fordiac.ide.gef.figures.MinSpaceFreeformFigure;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.GridLayer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class FBDebugView extends ViewPart {

	private GraphicalViewer viewer;
	private ActionRegistry actionRegistry;
	private static final int NUM_COLUMNS = 1;
	private FBType fbType;


	@Override
	public void createPartControl(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(NUM_COLUMNS).margins(LayoutConstants.getMargins())
		.generateLayout(composite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);

		createGraphicalViewer(composite);
		setTitleImage(FordiacImage.ICON_BASIC_FB.getImage());
	}



	private void createGraphicalViewer(final Composite parent) {
		final Composite composite = WidgetFactory.composite(SWT.None).create(parent);
		GridLayoutFactory.fillDefaults().numColumns(NUM_COLUMNS).margins(LayoutConstants.getMargins())
		.generateLayout(composite);

		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
		fbType = AbstractInterpreterTest.loadFBType("E_CTU"); //$NON-NLS-1$
		viewer = new ScrollingGraphicalViewer();
		viewer.createControl(composite);
		configureGraphicalViewer();
		initializeGraphicalViewer();
		hookGraphicalViewer();
	}

	private void configureGraphicalViewer() {
		viewer.getControl().setBackground(ColorConstants.listBackground);
		final ZoomScalableFreeformRootEditPart root = createRootEditPart();
		viewer.setRootEditPart(root);
		viewer.setEditPartFactory(getEditpartFactory());
		viewer.setContextMenu(new FordiacContextMenuProvider(viewer, root.getZoomManager(), getActionRegistry()));
	}

	private void initializeGraphicalViewer() {
		viewer.setContents(fbType);
	}

	private void hookGraphicalViewer() {
		getSite().setSelectionProvider(viewer);
	}

	@Override
	public void setFocus() {
		// nothing to do here
	}

	private ZoomScalableFreeformRootEditPart createRootEditPart() {
		return new ZoomScalableFreeformRootEditPart(getSite(), getActionRegistry()) {
			@Override
			protected AbstractFreeformFigure createDrawingAreaContainer() {
				return new MinSpaceFreeformFigure();
			}

			@Override
			protected IFigure createFigure() {
				final IFigure rootFigure = super.createFigure();
				final GridLayer grid = (GridLayer) getLayer(GRID_LAYER);
				if (grid != null) {
					// it does not make sense to have a grid in the interface layer so hide it
					grid.setVisible(true);
				}
				return rootFigure;
			}

			@Override
			protected void refreshGridLayer() {
				// empty to be sure that grid will not be drawn
			}
		};
	}

	private FBInterfaceEditPartFactory getEditpartFactory() {
		final TypeLibrary typeLib = fbType.getTypeLibrary();
		return new FBInterfaceEditPartFactory(null, typeLib);
	}

	private ActionRegistry getActionRegistry() {
		if (actionRegistry == null) {
			actionRegistry = new ActionRegistry();
		}
		return actionRegistry;
	}

}
