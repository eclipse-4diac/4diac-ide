/*******************************************************************************
 * Copyright (c) 2021, 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added delete option for error marker pins
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles.AnnotationCompoundBorder;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles.AnnotationFeedbackBorder;
import org.eclipse.fordiac.ide.gef.annotation.ProblemAnnotationStyler;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteErrorMarkerCommand;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorMarkerDataTypeImpl;
import org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorMarkerInterfaceImpl;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class ErrorMarkerInterfaceEditPart extends InterfaceEditPart {

	@Override
	protected GraphicalNodeEditPolicy getNodeEditPolicy() {
		// we don't want to allow any connection additions here therefore return null
		return null;
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		// allow delete of a errorMarker interface elements
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy() {

			@Override
			protected Command createDeleteCommand(final GroupRequest request) {
				if (getHost().getModel() instanceof IInterfaceElement) {
					return new DeleteErrorMarkerCommand(getModel(), getModel().getFBNetworkElement());
				}
				return null;
			}

		});
	}

	@Override
	public ErrorMarkerInterface getModel() {
		return (ErrorMarkerInterface) super.getModel();
	}

	@Override
	protected IFigure createFigure() {
		final IFigure figure = super.createFigure();
		figure.setBorder(new AnnotationCompoundBorder(figure.getBorder(),
				new AnnotationFeedbackBorder(ProblemAnnotationStyler.getErrorAnnotationColor())));
		return figure;
	}

	@Override
	public <T> T getAdapter(final Class<T> key) {
		if (key == ErrorMarkerInterfaceEditPart.class) {
			return key.cast(this);
		}
		if (key == ErrorMarkerDataTypeImpl.class) {
			final Adapter a = getContentAdapter();
			if (a.getTarget() instanceof final ErrorMarkerInterfaceImpl em
					&& em.getType() instanceof final ErrorMarkerDataTypeImpl emdt) {
				return key.cast(emdt);
			}
		}

		return super.getAdapter(key);
	}

}
