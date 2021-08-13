/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Melanie Winter
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.policies.DeletePrimitiveEditPolicy;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.policies.ChangeStringEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.gef.EditPolicy;

public class ParameterEditPart extends AbstractDirectEditableEditPart {

	ParameterEditPart() {

	}

	@Override
	public INamedElement getINamedElement() {
		return null;
	}

	@Override
	public Label getNameLabel() {
		return (Label) getFigure();
	}

	@Override
	protected IFigure createFigure() {
		final IFigure fig = new Figure();
		final GridLayout layout = new GridLayout(1, false);
		fig.setLayoutManager(layout);
		final Label label = new Label("text");
		fig.add(label);
		fig.setConstraint(label, new GridData());
		return label;

	}

	@Override
	public Parameter getModel() {
		return (Parameter) super.getModel();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeletePrimitiveEditPolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new ChangeStringEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy());
	}
}
