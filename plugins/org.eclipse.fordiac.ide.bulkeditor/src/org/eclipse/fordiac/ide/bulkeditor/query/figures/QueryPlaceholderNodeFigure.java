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
import org.eclipse.draw2d.Label;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;

/** Node of a placeholder showing its key and its value. */
public class QueryPlaceholderNodeFigure extends QueryNodeFigure {

	private static final int VALUE_WIDTH = 120;

	private final Map<String, Label> valueLabels = new LinkedHashMap<>();

	public QueryPlaceholderNodeFigure(final EObject placeholder) {
		super(placeholder);
		final Figure body = createBody();
		addValueRow(body, QueryModelHelper.FEATURE_KEY);
		addValueRow(body, QueryModelHelper.FEATURE_VAL);
		add(body);
	}

	@Override
	public void refresh() {
		super.refresh();
		valueLabels.forEach((featureName, valueLabel) -> valueLabel
				.setText(QueryModelHelper.getFeatureText(getElement(), featureName)));
	}

	@Override
	protected List<EditableValue> getEditableValues() {
		return valueLabels.entrySet().stream()
				.map(entry -> new EditableValue(entry.getValue(), getElement(), entry.getKey())).toList();
	}

	private void addValueRow(final Figure body, final String featureName) {
		final Label valueLabel = QueryValueLabel.createFixedWidth(VALUE_WIDTH);
		valueLabels.put(featureName, valueLabel);
		body.add(createValueRow(featureName, valueLabel));
	}
}
