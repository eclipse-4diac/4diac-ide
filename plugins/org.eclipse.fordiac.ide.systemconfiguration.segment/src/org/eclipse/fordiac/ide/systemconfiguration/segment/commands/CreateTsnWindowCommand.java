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

import org.eclipse.fordiac.ide.systemconfiguration.segment.TsnParameters;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.CommunicationFactory;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnConfiguration;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnWindow;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;

/** This command allows adding windows to a TSN configuration. */
public class CreateTsnWindowCommand extends CreationCommand {
	private final TsnConfiguration config;

	private TsnWindow newWindow;

	private final String comment;
	private final int duration;

	public CreateTsnWindowCommand(final TsnConfiguration config, final int duration) {
		this(config, TsnParameters.TSN_WINDOW_NAME, "", duration); // //$NON-NLS-1$
	}

	public CreateTsnWindowCommand(final TsnConfiguration config, final String name, final String comment,
			final int duration) {
		this.config = config;
		this.comment = comment;
		this.duration = duration;
	}

	@Override
	public boolean canExecute() {
		return (null != config) && (config.getWindows().size() < TsnParameters.TSN_MAX_WINDOWS);
	}

	@Override
	public void execute() {
		newWindow = CommunicationFactory.eINSTANCE.createTsnWindow();
		newWindow.setComment(comment);
		newWindow.setDuration(duration);
		config.getWindows().add(newWindow);
	}

	@Override
	public void undo() {
		config.getWindows().remove(newWindow);
	}

	@Override
	public void redo() {
		config.getWindows().add(newWindow);
	}

	@Override
	public Object getCreatedElement() {
		return newWindow;
	}

}
