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

import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.figure.NameStackedFigure;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.Device;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.widgets.Display;

/**
 * @brief EditPart for the Device model element.
 *
 *        This EditPart is responsible for creating the figure that represents a
 *        Device in the UI, providing the content pane for its children, and
 *        listening to changes in the Device model to update the UI accordingly.
 */
public class DeviceEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener {

	@Override
	protected IFigure createFigure() {
		return new NameStackedFigure(getDeviceName());
	}

	private String getDeviceName() {
		return ((Device) getModel()).getName();
	}

	@Override
	public IFigure getContentPane() {
		return ((NameStackedFigure) getFigure()).getContentPane();
	}

	@Override
	protected List<?> getModelChildren() {
		final Device device = (Device) getModel();
		return new ArrayList<>(device.getResources());
	}

	@Override
	protected void createEditPolicies() {
		// no policy needed for now
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			((Device) getModel()).addPropertyChangeListener(this);
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			((Device) getModel()).removePropertyChangeListener(this);
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
		if (evt.getPropertyName().equals(Device.PROPERTY_DEVICE_CHANGED)) {
			safeRefresh();
		}
	}

}
