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
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.nattable;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;

public class DelegatingSelectionProvider implements ISelectionProvider {
	private final ListenerList<ISelectionChangedListener> listeners = new ListenerList<>();
	private ISelectionProvider provider;

	private final ISelectionChangedListener forwardingListener = event -> fireSelectionChanged(
			new SelectionChangedEvent(this, event.getSelection()));

	public void setActiveProvider(final ISelectionProvider newProvider) {
		if (this.provider != null) {
			this.provider.removeSelectionChangedListener(forwardingListener);
		}
		this.provider = newProvider;
		if (this.provider != null) {
			this.provider.addSelectionChangedListener(forwardingListener);
			fireSelectionChanged(new SelectionChangedEvent(this, getSelection()));
		}
	}

	@Override
	public ISelection getSelection() {
		return provider != null ? provider.getSelection() : StructuredSelection.EMPTY;
	}

	@Override
	public void setSelection(final ISelection selection) {
		if (provider != null) {
			provider.setSelection(selection);
		}
	}

	@Override
	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		listeners.remove(listener);
	}

	private void fireSelectionChanged(final SelectionChangedEvent event) {
		for (final ISelectionChangedListener listener : listeners) {
			listener.selectionChanged(event);
		}
	}
}
