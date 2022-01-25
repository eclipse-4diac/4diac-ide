/*******************************************************************************
 * Copyright (c) 2020, 2021 Primetals Technologies Germany GmbH,
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.policies.ContainerContentXYLayoutPolicy;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;
import org.eclipse.swt.graphics.Point;

public abstract class AbstractContainerContentEditPart extends FBNetworkEditPart {
	private int childrenNumber = 0;
	protected Point p = new Point(0, 0);

	public Point getOffsetPoint() {
		return p;
	}

	private final Adapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			final Object feature = notification.getFeature();
			if (LibraryElementPackage.eINSTANCE.getPositionableElement_Position().equals(feature)) {
				p = FBNetworkHelper.getTopLeftCornerOfFBNetwork(getModel().getNetworkElements());
				p.x -= 40;
				getChildren().forEach(ep -> ((EditPart) ep).refresh());
			} else {
				super.notifyChanged(notification);
				if (getModel().getNetworkElements().size() != childrenNumber) {
					childrenNumber = getModel().getNetworkElements().size();
					p = FBNetworkHelper.getTopLeftCornerOfFBNetwork(getModel().getNetworkElements());
					p.x -= 40;
					getChildren().forEach(ep -> ((EditPart) ep).refresh());
				}
			}
		}
	};

	@Override
	protected List<?> getModelChildren() {
		final List<Object> children = new ArrayList<>(getNetworkElements());
		children.addAll(getFBValues());
		return children;
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			((Notifier) getModel()).eAdapters().add(adapter);
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			((Notifier) getModel()).eAdapters().remove(adapter);
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
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ContainerContentXYLayoutPolicy());
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

}
