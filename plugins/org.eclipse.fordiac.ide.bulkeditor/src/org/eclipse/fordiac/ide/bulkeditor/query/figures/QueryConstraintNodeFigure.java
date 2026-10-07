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
package org.eclipse.fordiac.ide.bulkeditor.query.figures;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.QueryUIPreferenceConstants;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper.FieldConstraintEntry;
import org.eclipse.swt.graphics.Color;

public class QueryConstraintNodeFigure extends QueryNodeFigure {

	private static final Color NEGATED_COLOR_HEADER_BG = QueryUIPreferenceConstants.getNegatedHeaderBackgroundColor();
	private final FigureCanvas canvas;
	private final Figure body;
	private final Map<EObject, FieldConstraintFigure> filters = new LinkedHashMap<>();

	public QueryConstraintNodeFigure(final EObject element, final FigureCanvas canvas) {
		super(element);
		this.canvas = canvas;
		body = createFieldConstraintBody();
		add(body);
	}

	@Override
	public void refresh() {
		super.refresh();
		final List<FieldConstraintEntry> entries = QueryModelHelper.getContainedFieldConstraints(getElement());
		if (entries.stream().map(FieldConstraintEntry::fieldConstraint).toList()
				.equals(List.copyOf(filters.keySet()))) {
			filters.forEach((fc, filter) -> filter.setData(QueryModelHelper.readFieldConstraint(fc)));
		} else {
			body.removeAll();
			filters.clear();
			entries.forEach(
					entry -> body.add(createFieldConstraintRow(entry.reference().getName(), entry.fieldConstraint())));
		}
	}

	private static Figure createFieldConstraintBody() {
		final Figure body = new Figure();
		final ToolbarLayout bodyLayout = new ToolbarLayout(false);
		bodyLayout.setStretchMinorAxis(true);
		bodyLayout.setSpacing(1);
		body.setLayoutManager(bodyLayout);
		body.setBorder(new MarginBorder(2, 6, 4, 6));
		body.setOpaque(true);
		return body;
	}

	private Figure createFieldConstraintRow(final String fieldName, final EObject fc) {
		final Figure row = new Figure();
		final GridLayout gl = new GridLayout(1, false);
		gl.marginHeight = 1;
		gl.marginWidth = 0;
		row.setLayoutManager(gl);

		final var filter = new FieldConstraintFigure(fieldName, QueryModelHelper.readFieldConstraint(fc), canvas);
		filter.addFilterChangeListener(data -> QueryModelHelper.writeFieldConstraint(fc, data));
		row.add(filter);
		filters.put(fc, filter);
		return row;
	}

	@Override
	public Color getBackgroundColor() {
		return QueryModelHelper.isNegatedConstraint(getElement()) ? NEGATED_COLOR_HEADER_BG
				: super.getBackgroundColor();
	}
}
