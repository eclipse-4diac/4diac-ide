/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;

public interface I4diacTableUtil extends ISelectionProvider {

	AbstractTableViewer getViewer();

	void addEntry(Object entry, int index, CompoundCommand cmd);

	Object removeEntry(int index, CompoundCommand cmd);

	void executeCompoundCommand(CompoundCommand cmd);

	@Override
	default void addSelectionChangedListener(final ISelectionChangedListener listener) {
		// currently nothing to be done here
	}

	@Override
	default ISelection getSelection() {
		return new StructuredSelection(new Object[] {});
	}

	@Override
	default void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		// currently nothing to be done here
	}

	@Override
	default void setSelection(final ISelection selection) {
		// currently nothing to be done here
	}

}
