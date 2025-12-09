/*******************************************************************************
 * Copyright (c) 2022 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.segment.commands;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.change.AbstractChangeListElementOrderCommand;
import org.eclipse.fordiac.ide.systemconfiguration.segment.communication.TsnConfiguration;
import org.eclipse.fordiac.ide.systemconfiguration.segment.communication.TsnWindow;

public class ChangeTsnWindowOrderCommand extends AbstractChangeListElementOrderCommand<TsnWindow> {

	public ChangeTsnWindowOrderCommand(final TsnWindow selection, final TsnConfiguration config, final boolean moveUp) {
		super(selection, moveUp, getTsnWindows(config));
	}

	public ChangeTsnWindowOrderCommand(final TsnWindow selection, final TsnConfiguration config, final int newIndex) {
		super(selection, newIndex, getTsnWindows(config));
	}

	public ChangeTsnWindowOrderCommand(final TsnWindow selection, final TsnConfiguration config,
			final TsnWindow refElement, final boolean insertAfter) {
		super(selection, refElement, insertAfter, getTsnWindows(config));
	}

	private static EList<TsnWindow> getTsnWindows(final TsnConfiguration config) {
		return config.getWindows();
	}
}
