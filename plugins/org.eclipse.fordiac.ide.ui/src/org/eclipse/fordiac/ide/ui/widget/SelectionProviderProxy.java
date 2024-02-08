/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;

public class SelectionProviderProxy implements IPostSelectionProvider {

	private final ListenerList<ISelectionChangedListener> selectionListeners = new ListenerList<>();
	private final ListenerList<ISelectionChangedListener> postSelectionListeners = new ListenerList<>();

	private ISelectionProvider delegate;

	private final ISelectionChangedListener selectionListener = event -> {
		if (event.getSelectionProvider() == delegate) {
			fireSelectionChanged(event.getSelection());
		}
	};

	private final ISelectionChangedListener postSelectionListener = event -> {
		if (event.getSelectionProvider() == delegate) {
			firePostSelectionChanged(event.getSelection());
		}
	};

	public void setDelegate(final ISelectionProvider newDelegate) {
		if (delegate == newDelegate) {
			return;
		}
		if (delegate != null) {
			delegate.removeSelectionChangedListener(selectionListener);
			if (delegate instanceof final IPostSelectionProvider postSelectionProvider) {
				postSelectionProvider.removePostSelectionChangedListener(postSelectionListener);
			}
		}
		delegate = newDelegate;
		if (newDelegate != null) {
			newDelegate.addSelectionChangedListener(selectionListener);
			if (newDelegate instanceof final IPostSelectionProvider postSelectionProvider) {
				postSelectionProvider.addPostSelectionChangedListener(postSelectionListener);
			}
			fireSelectionChanged(newDelegate.getSelection());
			firePostSelectionChanged(newDelegate.getSelection());
		} else {
			fireSelectionChanged(StructuredSelection.EMPTY);
			firePostSelectionChanged(StructuredSelection.EMPTY);
		}
	}

	protected void fireSelectionChanged(final ISelection selection) {
		fireSelectionChanged(selectionListeners, selection);
	}

	protected void firePostSelectionChanged(final ISelection selection) {
		fireSelectionChanged(postSelectionListeners, selection);
	}

	private void fireSelectionChanged(final ListenerList<ISelectionChangedListener> list, final ISelection selection) {
		final SelectionChangedEvent event = new SelectionChangedEvent(this, selection);
		list.forEach(listener -> listener.selectionChanged(event));
	}

	@Override
	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		selectionListeners.add(listener);
	}

	@Override
	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		selectionListeners.remove(listener);
	}

	@Override
	public void addPostSelectionChangedListener(final ISelectionChangedListener listener) {
		postSelectionListeners.add(listener);
	}

	@Override
	public void removePostSelectionChangedListener(final ISelectionChangedListener listener) {
		postSelectionListeners.remove(listener);
	}

	@Override
	public ISelection getSelection() {
		return delegate != null ? delegate.getSelection() : StructuredSelection.EMPTY;
	}

	@Override
	public void setSelection(final ISelection selection) {
		if (delegate != null) {
			delegate.setSelection(selection);
		}
	}
}
