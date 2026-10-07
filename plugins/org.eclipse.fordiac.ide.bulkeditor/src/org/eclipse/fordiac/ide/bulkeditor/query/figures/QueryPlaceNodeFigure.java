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

/** Node of the query place with the toggle to ignore linked libraries. */
public class QueryPlaceNodeFigure extends QueryNodeFigure {

	private final QueryToggleButton ignoreLinkedLibrariesToggle = new QueryToggleButton(
			QueryModelHelper.FEATURE_IGNORE_LINKED_LIBRARIES);

	public QueryPlaceNodeFigure(final EObject place) {
		super(place);
		ignoreLinkedLibrariesToggle.addActionListener(_ -> changeIgnoreLinkedLibraries());

		final Figure body = new Figure();
		body.setLayoutManager(new ToolbarLayout(false));
		body.setBorder(new MarginBorder(2, 6, 4, 6));
		body.add(ignoreLinkedLibrariesToggle);
		add(body);
	}

	@Override
	public void refresh() {
		super.refresh();
		ignoreLinkedLibrariesToggle.setSelected(
				QueryModelHelper.getBooleanFeature(getElement(), QueryModelHelper.FEATURE_IGNORE_LINKED_LIBRARIES));
	}

	private void changeIgnoreLinkedLibraries() {
		changeFeature(getElement(), QueryModelHelper.FEATURE_IGNORE_LINKED_LIBRARIES,
				Boolean.valueOf(ignoreLinkedLibrariesToggle.isSelected()));
	}
}
