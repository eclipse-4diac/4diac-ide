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
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fordiac.ide.bulkeditor.QueryUIPreferenceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Text;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphItem;

public class QueryPlaceholderNodeFigure extends QueryNodeFigure {

	private static final Color COLOR_VALUE_BG = QueryUIPreferenceConstants.getDefaultQueryBackground();
	private static final Color COLOR_VALUE_BORDER = QueryUIPreferenceConstants.getValueBorder();

	private final FigureCanvas canvas;

	public QueryPlaceholderNodeFigure(final EObject element, final FigureCanvas canvas) {
		super(element);
		this.canvas = canvas;
		add(createPlaceholderBody(element));
	}

	private Figure createPlaceholderBody(final EObject placeholder) {
		final Figure body = new Figure();
		final ToolbarLayout bodyLayout = new ToolbarLayout(false);
		bodyLayout.setStretchMinorAxis(true);
		bodyLayout.setSpacing(1);
		body.setLayoutManager(bodyLayout);
		body.setBorder(new MarginBorder(2, 6, 4, 6));
		body.setOpaque(true);

		body.add(createEditableRow(QueryModelHelper.FEATURE_KEY, placeholder));
		body.add(createEditableRow(QueryModelHelper.FEATURE_VAL, placeholder));

		return body;
	}

	private Figure createEditableRow(final String featureName, final EObject placeholder) {
		final Figure row = new Figure();
		final GridLayout gl = new GridLayout(2, false);
		gl.marginHeight = 2;
		gl.marginWidth = 4;
		row.setLayoutManager(gl);

		final Label nameLabel = new Label(featureName + ":"); //$NON-NLS-1$
		row.add(nameLabel);
		gl.setConstraint(nameLabel, new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		final EStructuralFeature feature = placeholder.eClass().getEStructuralFeature(featureName);
		final String initialValue = feature != null && placeholder.eIsSet(feature)
				? String.valueOf(placeholder.eGet(feature))
				: ""; //$NON-NLS-1$

		final Label valueLabel = new Label(initialValue);
		valueLabel.setOpaque(true);
		valueLabel.setBackgroundColor(COLOR_VALUE_BG);
		valueLabel.setBorder(new LineBorder(COLOR_VALUE_BORDER, 1));
		valueLabel.addMouseListener(new MouseListener.Stub() {
			@Override
			public void mouseDoubleClicked(final MouseEvent me) {
				if (canvas instanceof final Graph graph) {
					graph.setSelection(new GraphItem[0]);
				}
				openDirectEdit(valueLabel, placeholder, featureName);
			}
		});
		row.add(valueLabel);
		final var valueGd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		valueGd.widthHint = 120;
		gl.setConstraint(valueLabel, valueGd);

		return row;
	}

	private void openDirectEdit(final Label valueLabel, final EObject placeholder, final String featureName) {
		if (canvas == null || canvas.isDisposed()) {
			return;
		}

		final var figureBounds = valueLabel.getBounds().getCopy();
		valueLabel.translateToAbsolute(figureBounds);

		final Text textWidget = new Text(canvas, SWT.BORDER);
		textWidget.setText(valueLabel.getText());
		textWidget.setBounds(figureBounds.x, figureBounds.y, figureBounds.width, figureBounds.height);
		textWidget.selectAll();
		textWidget.setFocus();

		final Runnable commit = () -> {
			if (!textWidget.isDisposed()) {
				final String newValue = textWidget.getText() != null ? textWidget.getText() : ""; //$NON-NLS-1$
				valueLabel.setText(newValue);
				textWidget.dispose();
				QueryModelHelper.setPlaceholderFeature(placeholder, featureName, newValue);
			}
		};
		final Runnable cancel = () -> {
			if (!textWidget.isDisposed()) {
				textWidget.dispose();
			}
		};

		textWidget.addListener(SWT.FocusOut, e -> commit.run());
		textWidget.addListener(SWT.KeyDown, e -> {
			if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
				commit.run();
			} else if (e.keyCode == SWT.ESC) {
				cancel.run();
			}
		});
	}
}