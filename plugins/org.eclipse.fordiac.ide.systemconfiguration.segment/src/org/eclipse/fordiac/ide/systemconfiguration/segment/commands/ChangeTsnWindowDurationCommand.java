/*******************************************************************************
 * Copyright (c) 2022 Johannes Keppler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.segment.commands;

import org.eclipse.fordiac.ide.systemconfiguration.segment.communication.TsnWindow;
import org.eclipse.gef.commands.Command;

/** This command allows changing the cycle time of a TSN configuration. */
public class ChangeTsnWindowDurationCommand extends Command {
	private final TsnWindow window;
	private final int duration;
	private int oldDuration;

	public ChangeTsnWindowDurationCommand(final TsnWindow window, final int cycleTime) {
		this.duration = cycleTime;
		this.window = window;
	}

	@Override
	public boolean canExecute() {
		return (null != window) && (duration >= 0);
	}

	@Override
	public void execute() {
		this.oldDuration = window.getDuration();
		window.setDuration(duration);
	}

	@Override
	public void undo() {
		window.setDuration(oldDuration);
	}

	@Override
	public void redo() {
		window.setDuration(duration);
	}
}
