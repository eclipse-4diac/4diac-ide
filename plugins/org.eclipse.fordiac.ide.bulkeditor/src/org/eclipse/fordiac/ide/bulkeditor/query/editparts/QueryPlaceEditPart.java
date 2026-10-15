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
package org.eclipse.fordiac.ide.bulkeditor.query.editparts;

import java.util.List;
import java.util.Objects;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.utilities.SubAppHierarchyDialog;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;
import org.eclipse.fordiac.ide.bulkeditor.query.figures.QueryNodeFigure;
import org.eclipse.fordiac.ide.bulkeditor.query.figures.QueryPlaceNodeFigure;
import org.eclipse.fordiac.ide.bulkeditor.search.SearchHelper;

/** Edit part of the query place, whose node selects the scope of the search. */
public class QueryPlaceEditPart extends QueryNodeEditPart {

	public QueryPlaceEditPart(final IProject project) {
		super(project);
	}

	@Override
	protected QueryNodeFigure createNodeFigure() {
		final QueryPlaceNodeFigure figure = new QueryPlaceNodeFigure(getModel());
		figure.setSubappHierarchyButtonAction(this::selectSubappHierarchy);
		figure.setSubappHierarchyElementExists(
				name -> SearchHelper.resolveSubappHierarchyElement(getTypeLibrary(), name) != null);
		return figure;
	}

	private void selectSubappHierarchy() {
		final List<EObject> selectedElements = QueryModelHelper.getSubappHierarchy(getModel()).stream()
				.<EObject>map(name -> SearchHelper.resolveSubappHierarchyElement(getTypeLibrary(), name))
				.filter(Objects::nonNull).toList();
		final Object[] result = new SubAppHierarchyDialog(getProject(), selectedElements).open();
		if (result != null) {
			final List<String> names = SubAppHierarchyDialog.mapResultToURIs(result).stream()
					.map(SearchHelper::resolveElement).filter(Objects::nonNull)
					.map(SearchHelper::getSubappHierarchyName).toList();
			changeFeature(getModel(), QueryModelHelper.FEATURE_SUBAPP_HIERARCHY_ELEMENTS, names);
		}
	}
}
