/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.query;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.emf.ecore.EObject;

public class QueryConstraintNodeFigure extends QueryNodeFigure {

	private final FigureCanvas canvas;

	public QueryConstraintNodeFigure(final EObject element, final FigureCanvas canvas) {
		super(element);
		this.canvas = canvas;
		add(createFieldConstraintBody(element));
	}

	private Figure createFieldConstraintBody(final EObject constraint) {
		final Figure body = new Figure();
		final ToolbarLayout bodyLayout = new ToolbarLayout(false);
		bodyLayout.setStretchMinorAxis(true);
		bodyLayout.setSpacing(1);
		body.setLayoutManager(bodyLayout);
		body.setBorder(new MarginBorder(2, 6, 4, 6));
		body.setOpaque(true);

		for (final var entry : QueryModelHelper.getContainedFieldConstraints(constraint)) {
			body.add(createFieldConstraintRow(entry.reference().getName(), entry.fieldConstraint()));
		}
		return body;
	}

	private Figure createFieldConstraintRow(final String fieldName, final EObject fc) {
		final Figure row = new Figure();
		final GridLayout gl = new GridLayout(1, false);
		gl.marginHeight = 1;
		gl.marginWidth = 0;
		row.setLayoutManager(gl);

		final var filter = new FilterFigure(fieldName, QueryModelHelper.readFieldConstraint(fc), canvas);
		filter.addFilterChangeListener(data -> QueryModelHelper.writeFieldConstraint(fc, data));
		row.add(filter);
		return row;
	}
}
