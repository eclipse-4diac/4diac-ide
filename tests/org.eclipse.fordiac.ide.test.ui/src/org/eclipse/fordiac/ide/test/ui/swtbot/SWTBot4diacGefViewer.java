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

import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefViewer;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.waits.ICondition;

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

	/**
	 * Checks if there is a ConnectionEditPart in the editPartRegistry. Throws an
	 * exception if no such part can be found after 1 second.
	 *
	 * @param editPartRegistry Map with all registered editParts
	 * @throws Exception When no ConnectionEditPart can be found in the map of the
	 *                   {@link EditPartViewer#getEditPartRegistry
	 *                   EditPartRegistry}.
	 */
	public void waitForConnection() {
		final Map<?, ?> editPartRegistry = getGraphicalViewer().getEditPartRegistry();
		bot().waitUntil(new ICondition() {

			@Override
			public boolean test() throws Exception {
				return editPartRegistry.values().stream().filter(ConnectionEditPart.class::isInstance).count() == 1;
			}

			@Override
			public void init(final SWTBot bot) {
				// method must be implemented but empty since not needed
			}

			@Override
			public String getFailureMessage() {
				return "no ConnectionEditPart found";
			}

		}, 1000);
	}

}
