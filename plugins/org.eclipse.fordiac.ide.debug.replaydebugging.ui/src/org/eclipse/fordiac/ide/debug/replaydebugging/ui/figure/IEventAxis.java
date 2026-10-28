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

package org.eclipse.fordiac.ide.debug.replaydebugging.ui.figure;

import org.eclipse.draw2d.IFigure;

public interface IEventAxis {

	/** Figure whose coordinate space the X values below are expressed in. */
	IFigure getFigure();

	/** Live number of events. Never cache this. */
	int getEventCount();

	/** Center X of event 0, in the coordinate space of {@link #getFigure()}. */
	int getFirstEventCenterX();

	/** Distance between two consecutive event centers (unzoomed). */
	double getEventPitch();
}
