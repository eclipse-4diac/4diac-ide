/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner
 *   	- initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.editors.operations;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class RemoveElementOperation<T> extends AbstractOperation {

	private final List<T> elements;
	private final T element;
	private final Runnable refresh;

	private int index;

	public RemoveElementOperation(final String label, final List<T> elements, final T element, final Runnable refresh) {
		super(label);
		this.elements = elements;
		this.element = element;
		this.refresh = refresh;
	}

	@Override
	public IStatus execute(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		index = elements.indexOf(element);

		if (index >= 0) {
			elements.remove(index);
			refresh();
		}

		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		elements.remove(element);
		refresh();
		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		elements.add(index, element);
		refresh();
		return Status.OK_STATUS;
	}

	private void refresh() {
		if (refresh != null) {
			refresh.run();
		}
	}
}
