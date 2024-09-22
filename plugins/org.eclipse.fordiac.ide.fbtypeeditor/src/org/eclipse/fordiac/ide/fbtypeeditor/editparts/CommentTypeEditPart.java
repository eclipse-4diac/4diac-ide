/*******************************************************************************
 * Copyright (c) 2011, 2024 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *               - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Moved position calculation to the comment type edit part
 *   Virendra Ashiwal - Regulate Space at both side of FB (between FB and its
 *                      interfaces) based on number of WITH connections
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import java.util.List;

import org.eclipse.draw2d.AncestorListener;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.gef.annotation.AnnotableGraphicalEditPart;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModelEvent;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

class CommentTypeEditPart extends AbstractGraphicalEditPart implements AnnotableGraphicalEditPart {

	private static final int WITH_SIZE = 10;
	private static final int DISTANCE_TO_FB_BORDER = 15;

	private InterfaceEditPart referencedInterface;

	private final Adapter contentAdapter = new AdapterImpl() {
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

		/** Instantiates a new variable output container figure. */
		public CommentTypeContainerFigure() {
			final GridLayout layout = new GridLayout(3, false);
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
		setupReferencedEP();
	}

	public void setupReferencedEP() {
		if (getViewer().getEditPartForModel(getInterfaceElement()) instanceof final InterfaceEditPart iep) {
			referencedInterface = iep;
			referencedInterface.getFigure().addAncestorListener(new AncestorListener() {

				@Override
				public void ancestorRemoved(final IFigure ancestor) {
					// Currently nothing to do here
				}

				@Override
				public void ancestorMoved(final IFigure ancestor) {
					refreshVisuals();
				}

				@Override
				public void ancestorAdded(final IFigure ancestor) {
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
	public void refreshVisuals() {
		super.refreshVisuals();
		refreshPosition();
	}

	@Override
	public void updateAnnotations(final GraphicalAnnotationModelEvent event) {
		getChildren().stream().filter(AnnotableGraphicalEditPart.class::isInstance)
				.map(AnnotableGraphicalEditPart.class::cast).forEach(child -> child.updateAnnotations(event));
	}

	public InterfaceEditPart getReferencedInterface() {
		return referencedInterface;
	}

	private void refreshPosition() {
		if (null != getParent() && null != getInterfaceElement().eContainer()) {
			Rectangle bounds = null;
			final Point p = calculatePos();
			bounds = new Rectangle(p.x, p.y, getFigureWidth(), -1);
			((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
		}
	}

	private Point calculatePos() {
		if (null != referencedInterface) {
			final Rectangle bounds = referencedInterface.getFigure().getBounds();
			final InterfaceList interfaceList = ((InterfaceList) getInterfaceElement().eContainer());
			int dx = 0;
			if (getInterfaceElement().isIsInput()) {
				final int countInputEvWITH = getNrEvWITH(interfaceList.getEventInputs());
				dx = -DISTANCE_TO_FB_BORDER - getFigureWidth() - countInputEvWITH * WITH_SIZE;
			} else {
				final int countOutputEvWITH = getNrEvWITH(interfaceList.getEventOutputs());
				dx = DISTANCE_TO_FB_BORDER + bounds.width + countOutputEvWITH * WITH_SIZE;
			}
			return new Point(bounds.x + dx, bounds.y);
		}
		return new Point(0, 0);
	}

	private static int getNrEvWITH(final EList<Event> eList) {
		return (int) eList.stream().filter(ev -> !ev.getWith().isEmpty()).count();
	}

	private int getFigureWidth() {
		return getFigure().getPreferredSize().width;
	}

	@Override
	public CommentTypeField getModel() {
		return (CommentTypeField) super.getModel();
	}

	public IInterfaceElement getInterfaceElement() {
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

	@Override
	protected List<Object> getModelChildren() {
		return getModel().getChildren();
	}
}
