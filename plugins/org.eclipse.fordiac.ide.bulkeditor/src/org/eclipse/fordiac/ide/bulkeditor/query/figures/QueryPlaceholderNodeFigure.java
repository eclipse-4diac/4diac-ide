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
import org.eclipse.fordiac.ide.bulkeditor.query.model.Placeholder;
import org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage;

/** Node of a placeholder showing its key and its value. */
public class QueryPlaceholderNodeFigure extends QueryNodeFigure {

	private static final int VALUE_WIDTH = 120;

	private final Label keyLabel = QueryValueLabel.createFixedWidth(VALUE_WIDTH);
	private final Label valLabel = QueryValueLabel.createFixedWidth(VALUE_WIDTH);

	public QueryPlaceholderNodeFigure(final Placeholder placeholder) {
		super(placeholder);
		final Figure body = createBody();
		body.add(createValueRow(QueryPackage.Literals.PLACEHOLDER__KEY.getName(), keyLabel));
		body.add(createValueRow(QueryPackage.Literals.PLACEHOLDER__VAL.getName(), valLabel));
		add(body);
	}

	@Override
	public Placeholder getElement() {
		return (Placeholder) super.getElement();
	}

	@Override
	public void refresh() {
		super.refresh();
		keyLabel.setText(getElement().getKey() != null ? getElement().getKey() : ""); //$NON-NLS-1$
		valLabel.setText(getElement().getVal() != null ? getElement().getVal() : ""); //$NON-NLS-1$
	}

	@Override
	protected List<EditableValue> getEditableValues() {
		return List.of(new EditableValue(keyLabel, getElement(), QueryPackage.Literals.PLACEHOLDER__KEY),
				new EditableValue(valLabel, getElement(), QueryPackage.Literals.PLACEHOLDER__VAL));
	}
}
