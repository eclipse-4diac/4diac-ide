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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.QueryUIPreferenceConstants;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper.FieldConstraintEntry;
import org.eclipse.swt.graphics.Color;

/** Node of a constraint showing its field constraints, red if negated. */
public class QueryConstraintNodeFigure extends QueryNodeFigure {

	private static final Color NEGATED_COLOR_HEADER_BG = QueryUIPreferenceConstants.getNegatedHeaderBackgroundColor();

	private final Figure body = createBody();
	private final List<FieldConstraintFigure> fieldConstraintFigures = new ArrayList<>();

	public QueryConstraintNodeFigure(final EObject constraint) {
		super(constraint);
		add(body);
	}

	@Override
	public void refresh() {
		super.refresh();
		final List<EObject> fieldConstraints = QueryModelHelper.getContainedFieldConstraints(getElement()).stream()
				.map(FieldConstraintEntry::fieldConstraint).toList();
		if (!fieldConstraints.equals(getShownFieldConstraints())) {
			createFieldConstraintFigures(fieldConstraints);
		}
		fieldConstraintFigures.forEach(FieldConstraintFigure::refresh);
	}

	@Override
	public Color getBackgroundColor() {
		return QueryModelHelper.isNegatedConstraint(getElement()) ? NEGATED_COLOR_HEADER_BG
				: super.getBackgroundColor();
	}

	@Override
	protected List<EditableValue> getEditableValues() {
		return fieldConstraintFigures.stream().map(FieldConstraintFigure::getEditableValue).toList();
	}

	private List<EObject> getShownFieldConstraints() {
		return fieldConstraintFigures.stream().map(FieldConstraintFigure::getFieldConstraint).toList();
	}

	private void createFieldConstraintFigures(final List<EObject> fieldConstraints) {
		body.removeAll();
		fieldConstraintFigures.clear();
		for (final EObject fieldConstraint : fieldConstraints) {
			final FieldConstraintFigure figure = new FieldConstraintFigure(fieldConstraint, this::changeFeature);
			body.add(figure);
			fieldConstraintFigures.add(figure);
		}
	}
}
