/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.listeners;

import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Control;

public class DiagramFontChangeListener implements IPropertyChangeListener {

	private static class FigureFontUpdateListener implements IFontUpdateListener {

		private final IFigure figure;
		private final String fontName;

		public FigureFontUpdateListener(final IFigure figure, final String fontname) {
			this.figure = figure;
			this.fontName = fontname;
		}

		@Override
		public void updateFonts() {
			figure.setFont(JFaceResources.getFont(fontName));
			figure.invalidateTree();
			figure.revalidate();
		}

	}

	private static final String FONT = UIPreferenceConstants.DIAGRAM_FONT;
	private final IFontUpdateListener updateListener;

	public DiagramFontChangeListener(final IFontUpdateListener updateListner) {
		this.updateListener = updateListner;
	}

	public static DiagramFontChangeListener createForFigure(final IFigure figure) {
		final IFontUpdateListener figureListener = new FigureFontUpdateListener(figure, FONT);
		figureListener.updateFonts(); // ensure that root figure has the right font set
		return new DiagramFontChangeListener(figureListener);
	}

	/**
	 * adds this listener to the JFace font registry and removes it again once the
	 * given control is disposed
	 *
	 * @param control the control to check for disposing
	 */
	public void setupListeners(final Control control) {
		JFaceResources.getFontRegistry().addListener(this);
		control.addDisposeListener(_ -> JFaceResources.getFontRegistry().removeListener(this));
	}

	@Override
	public void propertyChange(final PropertyChangeEvent event) {
		if (event.getProperty().equals(FONT)) {
			updateListener.updateFonts();
		}
	}
}
