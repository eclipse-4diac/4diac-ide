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

import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnConfiguration;
import org.eclipse.gef.commands.Command;

/** This command allows changing the cycle time of a TSN configuration. */
public class ChangeTsnCycleTimeCommand extends Command {
	private final TsnConfiguration config;
	private final int cycleTime;
	private int oldCycleTime;

	public ChangeTsnCycleTimeCommand(final TsnConfiguration config, final int cycleTime) {
		this.cycleTime = cycleTime;
		this.config = config;
	}

	@Override
	public boolean canExecute() {
		return (null != config) && (cycleTime >= 0);
	}

	@Override
	public void execute() {
		this.oldCycleTime = config.getCycleTime();
		config.setCycleTime(cycleTime);
	}

	@Override
	public void undo() {
		config.setCycleTime(oldCycleTime);
	}

	@Override
	public void redo() {
		config.setCycleTime(cycleTime);
	}
}
