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

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.change.AbstractChangeListElementOrderCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnConfiguration;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnWindow;

public class ChangeTsnWindowOrderCommand extends AbstractChangeListElementOrderCommand<TsnWindow> {

	public ChangeTsnWindowOrderCommand(final TsnWindow selection, final Segment segment, final boolean moveUp) {
		super(selection, moveUp, getTsnWindows(segment));
	}

	public ChangeTsnWindowOrderCommand(final TsnWindow selection, final Segment segment, final int newIndex) {
		super(selection, newIndex, getTsnWindows(segment));
	}

	public ChangeTsnWindowOrderCommand(final TsnWindow selection, final Segment segment, final TsnWindow refElement,
			final boolean insertAfter) {
		super(selection, refElement, insertAfter, getTsnWindows(segment));
	}

	private static EList<TsnWindow> getTsnWindows(final Segment segment) {
		if (segment.getCommunication() instanceof TsnConfiguration) {
			final TsnConfiguration tsnC = (TsnConfiguration) segment.getCommunication();
			return tsnC.getWindows();
		}
		return ECollections.emptyEList();
	}
}
