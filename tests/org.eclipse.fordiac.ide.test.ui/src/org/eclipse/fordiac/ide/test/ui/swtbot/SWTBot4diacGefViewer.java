/*******************************************************************************
 * Copyright (c) 2023 Andrea Zoitl
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Andrea Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.ui.swtbot;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefViewer;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;

public class SWTBot4diacGefViewer extends SWTBotGefViewer {

	public SWTBot4diacGefViewer(final GraphicalViewer graphicalViewer) throws WidgetNotFoundException {
		super(graphicalViewer);
	}

	@Override
	protected void init() throws WidgetNotFoundException {
		UIThreadRunnable.syncExec(() -> {
			final Control control = graphicalViewer.getControl();
			if (control instanceof final FigureCanvas figureCanvas) {
				canvas = new SWTBot4diacFigureCanvas(figureCanvas);
			} else if ((control instanceof final Canvas gefCanvas) && (control instanceof final IAdaptable adaptable)) {
				final LightweightSystem lws = adaptable.getAdapter(LightweightSystem.class);
				if (lws != null) {
					canvas = new SWTBot4diacFigureCanvas(gefCanvas, lws);
				}
			}
			editDomain = graphicalViewer.getEditDomain();
		});

		if (graphicalViewer == null) {
			throw new WidgetNotFoundException("Editor does not adapt to a GraphicalViewer"); //$NON-NLS-1$
		}
	}

	@Override
	public SWTBot4diacFigureCanvas getCanvas() {
		return (SWTBot4diacFigureCanvas) super.getCanvas();
	}

	public GraphicalViewer getGraphicalViewer() {
		return graphicalViewer;
	}

}
