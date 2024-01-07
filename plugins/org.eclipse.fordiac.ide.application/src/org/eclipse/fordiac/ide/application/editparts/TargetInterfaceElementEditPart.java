/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
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

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.ui.editors.AdvancedScrollingGraphicalViewer;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceGetter;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.graphics.Color;

public class TargetInterfaceElementEditPart extends AbstractGraphicalEditPart {

	@Override
	public TargetInterfaceElement getModel() {
		return (TargetInterfaceElement) super.getModel();
	}

	private IInterfaceElement getRefElement() {
		return getModel().getRefElement();
	}

	@Override
	protected IFigure createFigure() {
		final var label = new Label() {
			@Override
			protected void paintFigure(final Graphics graphics) {
				final int alpha = graphics.getAlpha();
				graphics.setAlpha(150);
				graphics.fillRoundRectangle(getBounds(), 8, 4);
				graphics.setAlpha(alpha);
				super.paintFigure(graphics);
			}
		};
		label.setOpaque(false);
		label.setBackgroundColor(getModelColor());
		label.setText(getLabelText());
		return label;
	}

	private String getLabelText() {
		return getRefElement().getComment() + "\n" + getModel().getRefPinFullName();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new ModifiedNonResizeableEditPolicy());
	}

	@Override
	public void performRequest(final Request request) {
		if ((request.getType() == RequestConstants.REQ_OPEN)) {
			final var viewer = getViewer();
			final EditPart editPart = (EditPart) viewer.getEditPartRegistry().get(getRefElement());
			if (viewer instanceof final AdvancedScrollingGraphicalViewer asgv) {
				asgv.selectAndRevealEditPart(editPart);
			} else {
				viewer.select(editPart);
				viewer.reveal(editPart);
			}
		} else {
			super.performRequest(request);
		}
	}

	private Color getModelColor() {
		if (getRefElement() instanceof Event) {
			return PreferenceGetter.getColor(PreferenceConstants.P_EVENT_CONNECTOR_COLOR);
		}

		if (getRefElement() instanceof AdapterDeclaration) {
			return PreferenceGetter.getColor(PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR);
		}
		return PreferenceGetter.getDataColor(getRefElement().getType().getName());
	}

}
