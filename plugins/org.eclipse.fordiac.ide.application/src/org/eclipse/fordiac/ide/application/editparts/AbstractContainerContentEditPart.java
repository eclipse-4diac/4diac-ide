/*******************************************************************************
 * Copyright (c) 2020, 2022 Primetals Technologies Germany GmbH,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Alois Zoitl - initial implementation and/or documentation
 *   Alexander Lumplecker, Bianca Wiesmayr, Alois Zoitl - Bug: 568569
 *   Alois Zoitl - extracted most code into common base class for group
 *                 infrastructure
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkRootEditPart.FBNetworkMarqueeDragTracker;
import org.eclipse.fordiac.ide.application.policies.ContainerContentLayoutPolicy;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;
import org.eclipse.gef.tools.MarqueeSelectionTool;
import org.eclipse.jface.viewers.StructuredSelection;

public abstract class AbstractContainerContentEditPart extends FBNetworkEditPart {
	private static class ContainerMarqueeDragTracker extends FBNetworkRootEditPart.FBNetworkMarqueeDragTracker {
		private final AbstractContainerContentEditPart host;

		public ContainerMarqueeDragTracker(final AbstractContainerContentEditPart host) {
			this.host = host;
		}

		@Override
		protected StructuredSelection getDefaultSelectionForRightMouseDown() {
			return new StructuredSelection(host);
		}
	}

	private final Adapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			final Object feature = notification.getFeature();
			if (LibraryElementPackage.eINSTANCE.getPositionableElement_Position().equals(feature)) {
				getChildren().forEach(GraphicalEditPart::refresh);
			}
			super.notifyChanged(notification);
		}
	};

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getModel().eAdapters().add(adapter);
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getModel().eAdapters().remove(adapter);
		}
	}

	@Override
	public void installEditPolicy(final Object key, final EditPolicy editPolicy) {
		if (!(editPolicy instanceof ModifiedNonResizeableEditPolicy)) {
			super.installEditPolicy(key, editPolicy);
		} else {
			// install an selection editpolicy which is not showing any selection
			super.installEditPolicy(key, new SelectionEditPolicy() {

				@Override
				protected void showSelection() {
					// don't show any selection feedback
				}

				@Override
				protected void hideSelection() {
					// we don't need anything here
				}
			});
		}
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		// handles constraint changes of model elements and creation of new
		// model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ContainerContentLayoutPolicy());
	}

	@Override
	protected IFigure createFigure() {
		final IFigure figure = new Figure() {
			@Override
			public void setBounds(final Rectangle rect) {
				final Rectangle clientArea = getParent().getClientArea();
				final int maxAvailableHeight = clientArea.height - (rect.y - clientArea.y);
				// expand content to the available group size
				rect.height = Math.max(rect.height, maxAvailableHeight);
				super.setBounds(rect);
			}
		};

		figure.setOpaque(true);
		figure.setLayoutManager(new XYLayout());
		return figure;
	}

	@Override
	public DragTracker getDragTracker(final Request request) {
		final FBNetworkMarqueeDragTracker dragTracker = new ContainerMarqueeDragTracker(this);
		dragTracker.setMarqueeBehavior(MarqueeSelectionTool.BEHAVIOR_NODES_CONTAINED_AND_RELATED_CONNECTIONS);
		return dragTracker;
	}

	public abstract FBNetworkElement getContainerElement();

}
