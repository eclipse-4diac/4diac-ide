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
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class QueryPlaceNodeFigure extends QueryNodeFigure {

	public QueryPlaceNodeFigure(final EObject element) {
		super(element);
		add(createOccurrenceBody(element));
	}

	private static Figure createOccurrenceBody(final EObject instance) {
		final Figure body = new Figure();
		final ToolbarLayout bodyLayout = new ToolbarLayout(false);
		bodyLayout.setSpacing(2);
		body.setLayoutManager(bodyLayout);
		body.setBorder(new MarginBorder(2, 6, 4, 6));

		final EStructuralFeature feature = instance.eClass()
				.getEStructuralFeature(QueryModelHelper.FEATURE_IGNORE_LINKED_LIBRARIES);
		body.add(createOccurrenceToggle(instance, feature));

		return body;
	}

	private static Figure createOccurrenceToggle(final EObject instance, final EStructuralFeature feature) {
		final var button = new ToggleButton(QueryModelHelper.FEATURE_IGNORE_LINKED_LIBRARIES);
		button.setSelected(Boolean.TRUE.equals(instance.eGet(feature)));
		button.addActionListener(event -> QueryModelHelper.setIgnoreLinkedLibrary(instance, button.isSelected()));
		return button;
	}
}
