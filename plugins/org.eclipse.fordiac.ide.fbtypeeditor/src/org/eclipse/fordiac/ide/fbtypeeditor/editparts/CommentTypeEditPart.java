/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, fortiss GmbH
 * 				 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Moved position calculation to the comment type edit part
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import java.util.List;

import org.eclipse.draw2d.AncestorListener;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

class CommentTypeEditPart extends AbstractGraphicalEditPart implements EditPart {

	private static final int WITH_SIZE = 10;
	private static final int DISTANCE_TO_FB_BORDER = 15;

	InterfaceEditPart referencedInterface;

	private EContentAdapter contentAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if (Notification.SET == notification.getEventType()) {
				refreshVisuals();
			}
		}
	};

	/**
	 * The Class CommentTypeContainerFigure for handling the layout of one comment
	 * and type label of an fb interface.
	 */
	private static class CommentTypeContainerFigure extends Figure {

		/**
		 * Instantiates a new variable output container figure.
		 */
		public CommentTypeContainerFigure() {
			GridLayout layout = new GridLayout(3, false);
			layout.horizontalSpacing = 0;
			layout.verticalSpacing = 0;
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			setLayoutManager(layout);
		}
	}

	@Override
	public void activate() {
		super.activate();
		getModel().getReferencedElement().eAdapters().add(contentAdapter);
		Object part = getViewer().getEditPartRegistry().get(getInterfaceElement());
		if (part instanceof InterfaceEditPart) {
			referencedInterface = (InterfaceEditPart) part;
			referencedInterface.getFigure().addAncestorListener(new AncestorListener() {

				@Override
				public void ancestorRemoved(IFigure ancestor) {
					// Currently nothing to do here
				}

				@Override
				public void ancestorMoved(IFigure ancestor) {
					refreshVisuals();
				}

				@Override
				public void ancestorAdded(IFigure ancestor) {
					refreshVisuals();
				}
			});
		}
	}

	@Override
	public void deactivate() {
		super.deactivate();
		getModel().getReferencedElement().eAdapters().remove(contentAdapter);
	}

	@Override
	public void refresh() {
		super.refresh();
		refreshPosition();
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		refreshPosition();
	}

	private void refreshPosition() {
		if (null != getParent() && null != getInterfaceElement().eContainer()) {
			Rectangle bounds = null;
			Point p = calculatePos();
			bounds = new Rectangle(p.x, p.y, getFigureWidth(), -1);
			((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
		}
	}

	private Point calculatePos() {
		if (null != referencedInterface) {
			Rectangle bounds = referencedInterface.getFigure().getBounds();
			InterfaceList interfaceList = ((InterfaceList) getInterfaceElement().eContainer());
			int dx = 0;
			if (getInterfaceElement().isIsInput()) {
				int nrOfInputEvents = interfaceList.getEventInputs().size();
				dx = -DISTANCE_TO_FB_BORDER - getFigureWidth() - nrOfInputEvents * WITH_SIZE;
			} else {
				int nrOfOutputEvents = interfaceList.getEventOutputs().size();
				dx = DISTANCE_TO_FB_BORDER + bounds.width + nrOfOutputEvents * WITH_SIZE;
			}
			return new Point(bounds.x + dx, bounds.y);
		}
		return new Point(0, 0);
	}

	private int getFigureWidth() {
		return getFigure().getPreferredSize().width;
	}

	@Override
	public CommentTypeField getModel() {
		return (CommentTypeField) super.getModel();
	}

	private IInterfaceElement getInterfaceElement() {
		return getModel().getReferencedElement();
	}

	@Override
	protected IFigure createFigure() {
		return new CommentTypeContainerFigure();
	}

	@Override
	protected void createEditPolicies() {
		// we currently don't have any editpolices for this edit part
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected List getModelChildren() {
		return getModel().getChildren();
	}
}
