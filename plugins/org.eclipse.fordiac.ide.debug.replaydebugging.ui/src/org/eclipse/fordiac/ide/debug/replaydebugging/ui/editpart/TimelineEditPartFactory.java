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

import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.Device;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.EventMarker;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.Resource;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.Session;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.TimelineConnection;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.TimelineModel;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

/**
 * @brief Factory for creating EditParts for the timeline view.
 *
 *        It creates the appropriate EditPart based on the type of the model
 *        element (Session, Device, Resource, Timeline, TimelineConnection).
 */
public class TimelineEditPartFactory implements EditPartFactory {

	@Override
	public EditPart createEditPart(final EditPart context, final Object model) {

		if (model instanceof Session) {
			final var part = new SessionEditPart();
			part.setModel(model);
			return part;
		}

		if (model instanceof Device) {
			final var part = new DeviceEditPart();
			part.setModel(model);
			return part;
		}

		if (model instanceof Resource) {
			final var part = new ResourceEditPart();
			part.setModel(model);
			return part;
		}

		if (model instanceof TimelineModel) {
			final var part = new TimelineEditPart();
			part.setModel(model);
			return part;
		}

		if (model instanceof EventMarker) {
			final var part = new EventMarkerEditPart();
			part.setModel(model);
			return part;
		}

		if (model instanceof TimelineConnection) {
			final var part = new TimelineConnectionEditPart();
			part.setModel(model);
			return part;
		}

		return null;
	}
}