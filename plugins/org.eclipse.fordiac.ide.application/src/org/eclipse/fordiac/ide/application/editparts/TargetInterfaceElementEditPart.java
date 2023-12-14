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
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.ui.editors.AdvancedScrollingGraphicalViewer;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public class TargetInterfaceElementEditPart extends AbstractGraphicalEditPart {

	@Override
	public UntypedSubAppInterfaceElementEditPart.TargetInterfaceElement getModel() {
		return (UntypedSubAppInterfaceElementEditPart.TargetInterfaceElement) super.getModel();
	}

	private IInterfaceElement getRefElement() {
		return getModel().getRefElement();
	}

	@Override
	protected IFigure createFigure() {
		final var label = new Label() {
			@Override
			protected void paintFigure(final Graphics graphics) {
				graphics.fillRoundRectangle(getBounds(), 8, 4);
				super.paintFigure(graphics);
			}
		};
		label.setOpaque(false);
		label.setBackgroundColor(EditorWithInterfaceEditPart.INTERFACE_BAR_BG_COLOR);
		label.setText(getLabelText());

		return label;
	}

	private String getLabelText() {
		final var fbelement = getRefElement().getFBNetworkElement();
		final FBNetworkElement parent = fbelement.getOuterFBNetworkElement();
		return getRefElement().getComment() + "\n" + parent.getName() + "." + fbelement.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
				+ getRefElement().getName();
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

}
