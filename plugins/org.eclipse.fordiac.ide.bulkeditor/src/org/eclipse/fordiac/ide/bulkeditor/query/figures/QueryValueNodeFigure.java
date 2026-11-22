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
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;

public abstract class QueryValueNodeFigure extends QueryNodeFigure {

	private final Figure body = createBody();
	private final List<EditableValue> editableValues = new ArrayList<>();

	protected QueryValueNodeFigure(final EObject element) {
		super(element);
		add(body);
	}

	protected void addValue(final EAttribute attribute, final Label valueLabel) {
		addValue(new EditableValue(valueLabel, getElement(), attribute));
	}

	protected void addValue(final EditableValue value) {
		body.add(createValueRow(value.feature().getName(), value.label()));
		editableValues.add(value);
	}

	@Override
	public void refresh() {
		super.refresh();
		editableValues.forEach(value -> value.label().setText(value.getText()));
	}

	@Override
	protected List<EditableValue> getEditableValues() {
		return Collections.unmodifiableList(editableValues);
	}

	private static Figure createValueRow(final String featureName, final Label valueLabel) {
		final Figure row = new Figure();
		final GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 2;
		layout.marginWidth = 4;
		row.setLayoutManager(layout);
		row.add(new Label(featureName + ":"), new GridData(SWT.BEGINNING, SWT.CENTER, false, false)); //$NON-NLS-1$
		row.add(valueLabel, new GridData(SWT.FILL, SWT.CENTER, true, false));
		return row;
	}
}
