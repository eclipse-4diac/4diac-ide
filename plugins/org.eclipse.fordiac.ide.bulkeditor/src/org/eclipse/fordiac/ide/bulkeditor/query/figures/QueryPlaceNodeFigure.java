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

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;

public class QueryPlaceNodeFigure extends QueryNodeFigure {

	private final QueryToggleButton ignoreLinkedLibrariesToggle;

	public QueryPlaceNodeFigure(final EObject element) {
		super(element);
		ignoreLinkedLibrariesToggle = createOccurrenceToggle();
		add(createOccurrenceBody(ignoreLinkedLibrariesToggle));
	}

	@Override
	public void refresh() {
		super.refresh();
		ignoreLinkedLibrariesToggle.setSelected(Boolean.TRUE.equals(
				QueryModelHelper.getFeatureValue(getElement(), QueryModelHelper.FEATURE_IGNORE_LINKED_LIBRARIES)));
	}

	private static Figure createOccurrenceBody(final Figure occurrenceToggle) {
		final Figure body = new Figure();
		final ToolbarLayout bodyLayout = new ToolbarLayout(false);
		bodyLayout.setSpacing(2);
		body.setLayoutManager(bodyLayout);
		body.setBorder(new MarginBorder(2, 6, 4, 6));
		body.add(occurrenceToggle);
		return body;
	}

	private QueryToggleButton createOccurrenceToggle() {
		final var button = new QueryToggleButton(QueryModelHelper.FEATURE_IGNORE_LINKED_LIBRARIES);
		button.addActionListener(_ -> changeFeature(getElement(), QueryModelHelper.FEATURE_IGNORE_LINKED_LIBRARIES,
				Boolean.valueOf(button.isSelected())));
		return button;
	}
}
