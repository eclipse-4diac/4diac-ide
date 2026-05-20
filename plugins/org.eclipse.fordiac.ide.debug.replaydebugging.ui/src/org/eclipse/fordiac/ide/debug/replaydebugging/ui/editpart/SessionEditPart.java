/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.Session;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.widgets.Display;

/**
 * @brief EditPart for the Session model element.
 *
 *        This EditPart is responsible for creating the figure that represents
 *        the session and managing its children, which are the devices in the
 *        session. It listens to changes in the session model and refreshes the
 *        children accordingly.
 */
public class SessionEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener {

	@Override
	protected IFigure createFigure() {

		final Figure figure = new Figure() {
			@Override
			public Dimension getPreferredSize(final int wHint, final int hHint) {
				// Let the layout manager compute the true preferred size
				return getLayoutManager().getPreferredSize(this, wHint, hHint);
			}
		};
		figure.setLayoutManager(new ToolbarLayout(false)); // vertical
		figure.setOpaque(true);
		return figure;
	}

	@Override
	protected List<?> getModelChildren() {
		final Session session = (Session) getModel();
		return new ArrayList<>(session.getDevices());
	}

	@Override
	protected void createEditPolicies() {
		// no policy needed for now
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			((Session) getModel()).addPropertyChangeListener(this);
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			((Session) getModel()).removePropertyChangeListener(this);
			super.deactivate();
		}
	}

	private void safeRefresh() {
		final Display display = getViewer().getControl().getDisplay();
		if (display.getThread() == Thread.currentThread()) {
			refreshChildren();
			return;
		}

		display.asyncExec(() -> {
			if (isActive()) {
				refreshChildren();
			}
		});

	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(Session.PROPERTY_SESSION_CHANGED)) {
			safeRefresh();
		}
	}

}