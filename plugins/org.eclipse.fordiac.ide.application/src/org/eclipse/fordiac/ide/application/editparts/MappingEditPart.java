/*******************************************************************************
 * Copyright (c) 2026 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationMappingTarget;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public class MappingEditPart extends AbstractGraphicalEditPart {

	private final Adapter colorChangeListener = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			if (notification.getFeature() == LibraryElementPackage.eINSTANCE.getColorizableElement_Color()
					&& notification.getNewValue() instanceof final Color col) {
				setColor(getFigure(), col);
			}
		}
	};

	@Override
	public void activate() {
		getColorizeableElement().eAdapters().add(colorChangeListener);
		super.activate();
	}

	@Override
	protected IFigure createFigure() {

		final IFigure fig = new Figure() {
			@Override
			protected void paintFigure(final org.eclipse.draw2d.Graphics graphics) {
				// super paint figure first to draw the border
				super.paintFigure(graphics);
				graphics.fillRoundRectangle(getBounds(), 3, 3);
			}

		};
		fig.setPreferredSize(new Dimension(-1, 7));
		fig.setBorder(new MarginBorder(0, 0, -1, 0));

		setColor(fig, getColorizeableElement().getColor());
		return fig;
	}

	@Override
	protected void createEditPolicies() {
		// no edit interaction should be done with mapping elements
	}

	@Override
	public void deactivate() {
		getColorizeableElement().eAdapters().remove(colorChangeListener);
		super.deactivate();
	}

	private ColorizableElement getColorizeableElement() {
		if (getModel().getTo().eContainer() instanceof final CommunicationMappingTarget commTarget) {
			return (Segment) commTarget.eContainer().eContainer();
		}
		return getModel().getTo().getResource().getDevice();
	}

	@Override
	public Mapping getModel() {
		return (Mapping) super.getModel();
	}

	@Override
	public void installEditPolicy(final Object key, final EditPolicy editPolicy) {
		if (key == EditPolicy.PRIMARY_DRAG_ROLE) {
			// we do not want to be selectable and dragable
			return;
		}

		super.installEditPolicy(key, editPolicy);
	}

	private static void setColor(final IFigure figure, final Color fordiacColor) {
		org.eclipse.swt.graphics.Color newColor;
		if (fordiacColor != null) {
			newColor = new org.eclipse.swt.graphics.Color(fordiacColor.getRed(), fordiacColor.getGreen(),
					fordiacColor.getBlue());
		} else {
			newColor = null;
		}
		figure.setBackgroundColor(newColor);
	}

}
