/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial implementation and/or documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.ui.widgets;

import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;

public class GoIntoSubappSelectionEvent extends SelectionChangedEvent {

	private final SubApp subapp;

	public GoIntoSubappSelectionEvent(final ISelectionProvider source, final SubApp subapp) {
		super(source, new StructuredSelection(subapp));
		this.subapp = subapp;
	}

	public SubApp getSubApp() {
		return subapp;
	}

}
