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
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.QueryUIPreferenceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

/** Query node with a header showing the class name of its element. */
public class QueryNodeFigure extends Figure {

	/** Performs the changes of query elements requested in the figures. */
	@FunctionalInterface
	public interface FeatureChangeHandler {
		void changeFeature(EObject element, String featureName, Object value);
	}

	/** Feature value shown in a label that can be edited directly. */
	public record EditableValue(Label label, EObject element, String featureName) {
	}

	private static final Color COLOR_HEADER_BG = QueryUIPreferenceConstants.getHeaderBackgroundColor();
	private static final Color COLOR_HEADER_FG = QueryUIPreferenceConstants.getHeaderForegroundColor();
	private static final Font BOLD_FONT = QueryUIPreferenceConstants.getHeaderFont();

	private final EObject element;
	private final Figure header;
	private FeatureChangeHandler featureChangeHandler;

	public QueryNodeFigure(final EObject element) {
		this.element = element;
		final ToolbarLayout layout = new ToolbarLayout(false);
		layout.setStretchMinorAxis(true);
		setLayoutManager(layout);
		setBorder(new LineBorder(getBackgroundColor()));
		setOpaque(true);
		header = createHeader(element.eClass().getName());
		add(header);
	}

	public EObject getElement() {
		return element;
	}

	public void setFeatureChangeHandler(final FeatureChangeHandler featureChangeHandler) {
		this.featureChangeHandler = featureChangeHandler;
	}

	/** Updates the figure to the current state of its element. */
	public void refresh() {
		final Color color = getBackgroundColor();
		header.setBackgroundColor(color);
		((LineBorder) getBorder()).setColor(color);
		repaint();
	}

	/** @return the editable value shown at the given absolute location or null */
	public EditableValue getEditableValueAt(final Point location) {
		if (location == null) {
			return null;
		}
		return getEditableValues().stream().filter(value -> {
			final Point point = location.getCopy();
			value.label().translateToRelative(point);
			return value.label().containsPoint(point);
		}).findFirst().orElse(null);
	}

	@Override
	public Color getBackgroundColor() {
		return COLOR_HEADER_BG;
	}

	@SuppressWarnings("static-method") // allow subclasses to provide their editable values
	protected List<EditableValue> getEditableValues() {
		return List.of();
	}

	protected void changeFeature(final EObject featureOwner, final String featureName, final Object value) {
		featureChangeHandler.changeFeature(featureOwner, featureName, value);
	}

	/** @return a container for the content shown below the header */
	protected static Figure createBody() {
		final Figure body = new Figure();
		final ToolbarLayout layout = new ToolbarLayout(false);
		layout.setStretchMinorAxis(true);
		layout.setSpacing(1);
		body.setLayoutManager(layout);
		body.setBorder(new MarginBorder(2, 6, 4, 6));
		body.setOpaque(true);
		return body;
	}

	/** @return a row showing the name of a feature and the label with its value */
	protected static Figure createValueRow(final String featureName, final Label valueLabel) {
		final Figure row = new Figure();
		final GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 2;
		layout.marginWidth = 4;
		row.setLayoutManager(layout);
		row.add(new Label(featureName + ":"), new GridData(SWT.BEGINNING, SWT.CENTER, false, false)); //$NON-NLS-1$
		row.add(valueLabel, new GridData(SWT.FILL, SWT.CENTER, true, false));
		return row;
	}

	private Figure createHeader(final String name) {
		final Figure headerFigure = new Figure();
		final GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 4;
		layout.marginWidth = 6;
		headerFigure.setLayoutManager(layout);
		headerFigure.setOpaque(true);
		headerFigure.setBackgroundColor(getBackgroundColor());

		final Label nameLabel = new Label(name);
		nameLabel.setForegroundColor(COLOR_HEADER_FG);
		nameLabel.setFont(BOLD_FONT);
		headerFigure.add(nameLabel, new GridData(SWT.FILL, SWT.CENTER, true, false));
		return headerFigure;
	}
}
