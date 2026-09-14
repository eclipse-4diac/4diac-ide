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

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class SetValueOperation<T> extends AbstractOperation {

	private final Supplier<T> getter;
	private final Consumer<T> setter;
	private final T newValue;
	private final Runnable refresh;

	private T oldValue;

	public SetValueOperation(final String label, final Supplier<T> getter, final Consumer<T> setter, final T newValue,
			final Runnable refresh) {
		super(label);
		this.getter = getter;
		this.setter = setter;
		this.newValue = newValue;
		this.refresh = refresh;
	}

	@Override
	public IStatus execute(final IProgressMonitor monitor, final IAdaptable info) {
		oldValue = getter.get();
		setter.accept(newValue);
		refresh();
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(final IProgressMonitor monitor, final IAdaptable info) {
		setter.accept(newValue);
		refresh();
		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(final IProgressMonitor monitor, final IAdaptable info) {
		setter.accept(oldValue);
		refresh();
		return Status.OK_STATUS;
	}

	private void refresh() {
		if (refresh != null) {
			refresh.run();
		}
	}

}
