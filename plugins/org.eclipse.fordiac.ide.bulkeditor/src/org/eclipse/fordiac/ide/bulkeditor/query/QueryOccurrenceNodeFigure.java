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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class QueryOccurrenceNodeFigure extends QueryNodeFigure {

	public QueryOccurrenceNodeFigure(final EObject element) {
		super(element);
		add(createOccurrenceBody(element));
	}

	private static Figure createOccurrenceBody(final EObject instance) {
		final Figure body = new Figure();
		final ToolbarLayout bodyLayout = new ToolbarLayout(false);
		bodyLayout.setSpacing(2);
		body.setLayoutManager(bodyLayout);
		body.setBorder(new MarginBorder(2, 6, 4, 6));

		final EStructuralFeature occFeature = instance.eClass()
				.getEStructuralFeature(QueryModelHelper.FEATURE_OCCURRENCE);
		if (occFeature == null) {
			return body;
		}

		final EEnum occEnum = (EEnum) occFeature.getEType();
		final Object raw = instance.eGet(occFeature);
		if (raw instanceof final List<?> selectedValues) {
			for (final EEnumLiteral literal : occEnum.getELiterals()) {
				body.add(createOccurrenceToggle(instance, occFeature, literal, selectedValues));
			}
		}
		return body;
	}

	private static Figure createOccurrenceToggle(final EObject instance, final EStructuralFeature occFeature,
			final EEnumLiteral literal, final List<?> selectedValues) {
		final var button = new ToggleButton(literal.getName());
		button.setSelected(selectedValues.contains(literal.getInstance()));
		button.addActionListener(event -> {
			final Object raw = instance.eGet(occFeature);
			if (raw instanceof final List<?> list) {
				final List<Object> updatedList = new ArrayList<>(list);
				if (button.isSelected()) {
					if (!updatedList.contains(literal.getInstance())) {
						updatedList.add(literal.getInstance());
					}
				} else {
					updatedList.remove(literal.getInstance());
				}
				QueryModelHelper.setOccurrences(instance, updatedList);
			}
		});
		return button;
	}
}
