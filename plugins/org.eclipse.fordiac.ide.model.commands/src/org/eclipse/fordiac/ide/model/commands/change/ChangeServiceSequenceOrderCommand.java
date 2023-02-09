/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Melanie Winter
 *      - Initial implementation and/or documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;

public class ChangeServiceSequenceOrderCommand extends AbstractChangeListElementOrderCommand<ServiceSequence> {

	public ChangeServiceSequenceOrderCommand(final ServiceSequence selection, final boolean moveUp) {
		super(selection, moveUp, selection.getService().getServiceSequence());
	}

	public ChangeServiceSequenceOrderCommand(final ServiceSequence selection, final int newIndex) {
		super(selection, newIndex, selection.getService().getServiceSequence());
	}

	public ChangeServiceSequenceOrderCommand(final ServiceSequence selection, final ServiceSequence refElement,
			final boolean insertAfter) {
		super(selection, refElement, insertAfter, selection.getService().getServiceSequence());
	}
}
