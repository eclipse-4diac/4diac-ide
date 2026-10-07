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
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.QueryUIPreferenceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

public class QueryNodeFigure extends Figure {

	/** Performs the changes of query elements requested in the figures. */
	@FunctionalInterface
	public interface FeatureChangeHandler {
		void changeFeature(EObject element, String featureName, Object value);
	}

	private static final Color COLOR_HEADER_BG = QueryUIPreferenceConstants.getHeaderBackgroundColor();
	private static final Color COLOR_HEADER_FG = QueryUIPreferenceConstants.getHeaderForegroundColor();
	private static final Font BOLD_FONT = QueryUIPreferenceConstants.getHeaderFont();

	private final EObject element;
	private final Figure header;
	private FeatureChangeHandler featureChangeHandler;

	public QueryNodeFigure(final EObject element) {
		this.element = element;
		configureLayout();
		header = createHeader(element);
		add(header);
	}

	public EObject getElement() {
		return element;
	}

	public void setFeatureChangeHandler(final FeatureChangeHandler featureChangeHandler) {
		this.featureChangeHandler = featureChangeHandler;
	}

	protected void changeFeature(final EObject featureOwner, final String featureName, final Object value) {
		featureChangeHandler.changeFeature(featureOwner, featureName, value);
	}

	/** Updates the figure to the current state of its element. */
	public void refresh() {
		final Color color = getBackgroundColor();
		header.setBackgroundColor(color);
		((LineBorder) getBorder()).setColor(color);
		repaint();
	}

	private void configureLayout() {
		final ToolbarLayout layout = new ToolbarLayout(false);
		layout.setStretchMinorAxis(true);
		layout.setSpacing(0);
		setLayoutManager(layout);
		setBorder(new LineBorder(getBackgroundColor()));
		setOpaque(true);
	}

	protected Figure createHeader(final EObject eObj) {
		final Figure header = new Figure();
		final GridLayout gl = new GridLayout(1, false);
		gl.marginHeight = 4;
		gl.marginWidth = 6;
		header.setLayoutManager(gl);
		header.setOpaque(true);
		header.setBackgroundColor(getBackgroundColor());

		final Label nameLabel = new Label(eObj.eClass().getName());
		nameLabel.setForegroundColor(COLOR_HEADER_FG);
		nameLabel.setFont(BOLD_FONT);
		header.add(nameLabel);
		gl.setConstraint(nameLabel, new GridData(SWT.FILL, SWT.CENTER, true, false));

		return header;
	}

	@Override
	public Color getBackgroundColor() {
		return COLOR_HEADER_BG;
	}
}
