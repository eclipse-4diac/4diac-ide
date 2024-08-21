/*******************************************************************************
 * Copyright (c)  2012, 2024 Profactor GmbH, fortiss GmbH,
 * 							 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger - initial implementation
 *   Alois Zoitl - extracted createFigure and position calculation from
 *                 TestEditPart from the FBTester to be used here
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui.view.editparts;

import org.eclipse.draw2d.AncestorListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.graphics.Font;

public abstract class AbstractDebugInterfaceValueEditPart extends AbstractGraphicalEditPart {

	private InterfaceEditPart referencedInterface;

	protected AbstractDebugInterfaceValueEditPart() {
	}

	@Override
	public void activate() {
		super.activate();
		final Object part = getViewer().getEditPartRegistry().get(getInterfaceElement());
		if (part instanceof final InterfaceEditPart iep) {
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
		refreshPosition();
	}

	protected abstract IInterfaceElement getInterfaceElement();

	private boolean isInput() {
		return getInterfaceElement().isIsInput();
	}

	@Override
	protected IFigure createFigure() {
		final Label l = new Label();
		l.setSize(100, -1);
		l.setOpaque(true);
		l.setBackgroundColor(org.eclipse.draw2d.ColorConstants.yellow);
		l.setPreferredSize(150, 20);
		final LineBorder lb = new LineBorder() {
			@Override
			public Insets getInsets(final IFigure figure) {
				return new Insets(1, 2, 1, 2);
			}
		};
		l.setBorder(lb);
		return l;
	}

	public Label getLabelFigure() {
		return (Label) super.getFigure();
	}

	@Override
	protected void createEditPolicies() {
		// currently we don't have any edit policy
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		refreshPosition();
	}

	protected void refreshPosition() {
		if (null != referencedInterface) {
			final Rectangle bounds = referencedInterface.getFigure().getBounds();

			final int width = getFigureWidth();
			final int x = calcXPos(bounds, width);
			final Rectangle newBounds = new Rectangle(x, bounds.y, width, -1);
			((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), newBounds);
		}
	}

	private int getFigureWidth() {
		final Font font = getFigure().getFont();
		int width = 50;
		if (font != null) {
			width = getFigure().getPreferredSize().width;
			width = Math.max(width, 50);
		}
		return width;
	}

	private int calcXPos(final Rectangle bounds, final int width) {
		int x = 0;
		if (isInput()) {
			x = bounds.x - 10 - width - 15 * getNumEventInputs();
		} else {
			x = bounds.x + bounds.width + 10 + 15 * getNumEventOutputs();
		}
		return x;
	}

	private int getNumEventInputs() {
		return ((InterfaceList) getInterfaceElement().eContainer()).getEventInputs().size();
	}

	private int getNumEventOutputs() {
		return ((InterfaceList) getInterfaceElement().eContainer()).getEventOutputs().size();
	}

}