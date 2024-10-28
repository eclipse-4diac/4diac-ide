/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *   - initial API and implementation and/or initial documentation
 *   Fabio Gandolfi - added Comments for Applications
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.application.policies.AbstractCreateInstanceDirectEditPolicy;
import org.eclipse.fordiac.ide.application.policies.FBNetworkCreateInstanceDirectEditPolicy;
import org.eclipse.fordiac.ide.application.policies.FBNetworkXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.draw2d.SingleLineBorder;
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.requests.SelectionRequest;

/** Edit Part for the visualization of FBNetworks. */
public class FBNetworkEditPart extends AbstractFBNetworkEditPart {

	/** The adapter. */
	private Adapter adapter;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getModel().eAdapters().add(getContentAdapter());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#deactivate()
	 */
	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getModel().eAdapters().remove(getContentAdapter());

		}
	}

	/**
	 * Gets the content adapter.
	 *
	 * @return the content adapter
	 */
	protected Adapter getContentAdapter() {
		if (adapter == null) {
			adapter = new AdapterImpl() {
				@Override
				public void notifyChanged(final Notification notification) {
					final int type = notification.getEventType();
					switch (type) {
					case Notification.ADD:
					case Notification.ADD_MANY:
					case Notification.REMOVE:
					case Notification.REMOVE_MANY:
						refreshChildren();
						break;
					case Notification.SET:
						break;
					default:
					}
				}
			};
		}
		return adapter;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		for (final Object ep : getChildren()) {
			if (ep instanceof final IContainerEditPart container) {
				final GraphicalEditPart contentEP = container.getContentEP();
				container.refresh();
				// unfolded subapps and groups do have a content network
				if (contentEP != null) {
					contentEP.refresh();
				}
			}
		}
	}

	/**
	 * Creates the EditPolicies used for this EditPart.
	 *
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		// handles constraint changes of model elements and creation of new
		// model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new FBNetworkXYLayoutEditPolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new FBNetworkCreateInstanceDirectEditPolicy());
	}

	private static final int BASE_WIDTH = 400;
	private static final int BASE_HEIGHT = 200;

	private class InterfaceBarLayout extends BorderLayout {
		@Override
		protected Dimension calculatePreferredSize(final IFigure figure, final int wHint, final int hHint) {
			final Rectangle newBounds = new Rectangle();
			newBounds.setSize(super.calculatePreferredSize(figure, wHint, hHint));
			newBounds.setLocation(getLocation());
			newBounds.y -= commentContainer.getPreferredSize().height;
			if (newBounds.x > 0) {
				newBounds.x = 0;
			}
			if (newBounds.y > 0) {
				newBounds.y = 0;
			}
			// get the size of the feedback/handle layer and use it to calculate our size,
			// this is needed when stuff is
			// moved around or
			FreeformLayer layer = (FreeformLayer) getLayer(LayerConstants.FEEDBACK_LAYER);
			layer.validate();
			newBounds.union(layer.getFreeformExtent());
			layer = (FreeformLayer) getLayer(LayerConstants.HANDLE_LAYER);
			layer.validate();
			newBounds.union(layer.getFreeformExtent());

			final Rectangle resultingBounds = calculateModuloExtent(newBounds);
			// it is important to keep the width and height in the constraints to -1
			// otherwise it will never be
			// recalculated
			figure.getParent().setConstraint(figure, new Rectangle(resultingBounds.x, resultingBounds.y, -1, -1));
			return resultingBounds.getSize();
		}

		private Rectangle calculateModuloExtent(final Rectangle newBounds) {
			// adjust size to be a multiple of the base width/height
			final int x = calcAxisOrigin(newBounds.x, BASE_WIDTH);
			final int y = calcAxisOrigin(newBounds.y, BASE_HEIGHT);
			final int width = calcAxisExtent(newBounds.x, x, newBounds.width, BASE_WIDTH);
			final int height = calcAxisExtent(newBounds.y, y, newBounds.height, BASE_HEIGHT);
			return new Rectangle(x, y, width, height);
		}

		private int calcAxisExtent(final int baseOrigin, final int newOrigin, final int sourceExtent,
				final int baseUnit) {
			final int startExtent = (sourceExtent + baseOrigin) - newOrigin;

			int newExtend = ((startExtent / baseUnit) + 1) * baseUnit;
			if (newExtend < (3 * baseUnit)) {
				newExtend = 3 * baseUnit;
			}
			return newExtend;
		}

		private int calcAxisOrigin(final int axisPos, final int baseUnit) {
			if (axisPos < 0) {
				// when negative we need to go one beyond to have the correct origin
				return ((axisPos / baseUnit) - 1) * baseUnit;
			}
			return (axisPos / baseUnit) * baseUnit;
		}

		private Point getLocation() {
			if (getContentPane() instanceof final FreeformLayer contentFreeFormLayer) {
				return contentFreeFormLayer.getFreeformExtent().getLocation();
			}
			return getContentPane().getBounds().getLocation();
		}

	}

	private FreeformLayer contentContainer;

	private InstanceComment instanceComment;
	private Figure commentContainer;

	@Override
	protected IFigure createFigure() {
		final IFigure mainFigure = new Figure();
		final BorderLayout mainLayout = new InterfaceBarLayout();
		mainLayout.setVerticalSpacing(-1); // remove spacing between comment and interface container

		mainFigure.setLayoutManager(mainLayout);
		mainFigure.setOpaque(false);

		contentContainer = new FreeformLayer();
		contentContainer.setLayoutManager(new FreeformLayout());
		mainFigure.add(contentContainer, BorderLayout.CENTER);

		createCommentContainer(mainFigure);

		final IFigure root = super.createFigure();
		root.setBorder(null); // we don't want a border here
		root.add(mainFigure);
		root.setConstraint(mainFigure, new Rectangle(0, 0, -1, -1));

		return root;
	}

	private void createCommentContainer(final IFigure mainFigure) {
		commentContainer = new Figure();
		final Border border = new SingleLineBorder() {

			private final Insets insets = new Insets(5); // spacing

			@Override
			public Insets getInsets(final IFigure figure) {
				return insets;
			}

		};
		commentContainer.setBorder(border);
		final ToolbarLayout layout = new ToolbarLayout();
		layout.setMinorAlignment(OrderedLayout.ALIGN_CENTER);
		layout.setStretchMinorAxis(false);
		commentContainer.setOpaque(true);

		commentContainer.setLayoutManager(layout);
		mainFigure.add(commentContainer, BorderLayout.TOP);
	}

	@Override
	public IFigure getContentPane() {
		if (contentContainer != null) {
			return contentContainer;
		}
		return super.getContentPane();
	}

	@Override
	protected List<?> getModelChildren() {
		if (getModel() != null) {
			final ArrayList<Object> children = new ArrayList<>(super.getModelChildren());
			if (isInstance()) {
				children.add(getInstanceComment());
			}
			return children;
		}
		return Collections.emptyList();
	}

	private boolean isInstance() {
		return getModel().eContainer() instanceof Application;
	}

	private InstanceComment getInstanceComment() {
		if (null == instanceComment) {
			instanceComment = new InstanceComment((Application) getModel().eContainer());
		}
		return instanceComment;
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		if (childEditPart instanceof final InstanceCommentEditPart instanceCommentEP) {
			final Figure commentFigure = instanceCommentEP.getFigure();
			commentFigure.setBorder(null);
			commentContainer.add(commentFigure);
		} else {
			super.addChildVisual(childEditPart, index);
		}
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		if (childEditPart instanceof final InstanceCommentEditPart instanceCommentEP) {
			commentContainer.remove(instanceCommentEP.getFigure());
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick

		if (((request.getType() == RequestConstants.REQ_DIRECT_EDIT)
				|| (request.getType() == RequestConstants.REQ_OPEN))
				&& (request instanceof final SelectionRequest selectionRequest)) {
			if (getEditPolicy(
					EditPolicy.DIRECT_EDIT_ROLE) instanceof final AbstractCreateInstanceDirectEditPolicy createInstanceDEP) {
				createInstanceDEP.performDirectEdit(selectionRequest);
			}
		} else {
			super.performRequest(request);
		}
	}

	@Override
	public DragTracker getDragTracker(final Request req) {
		return getParent().getDragTracker(req);
	}
}