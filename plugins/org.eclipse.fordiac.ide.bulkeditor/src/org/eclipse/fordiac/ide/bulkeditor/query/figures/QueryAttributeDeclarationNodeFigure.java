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

import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;

/** Node of an attribute declaration showing the attribute name. */
public class QueryAttributeDeclarationNodeFigure extends QueryNodeFigure {

	private static final int MIN_VALUE_WIDTH = 160;

	private final Label nameLabel = QueryValueLabel.createGrowing(MIN_VALUE_WIDTH);

	public QueryAttributeDeclarationNodeFigure(final EObject attributeDeclaration) {
		super(attributeDeclaration);
		final Figure body = createBody();
		body.add(createValueRow(QueryModelHelper.FEATURE_NAME, nameLabel));
		add(body);
	}

	@Override
	public void refresh() {
		super.refresh();
		nameLabel.setText(QueryModelHelper.getFeatureText(getElement(), QueryModelHelper.FEATURE_NAME));
	}

	@Override
	protected List<EditableValue> getEditableValues() {
		return List.of(new EditableValue(nameLabel, getElement(), QueryModelHelper.FEATURE_NAME));
	}
}
