/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber - initial implementation and/or documentation
 *   Alois Zoitl - extended selection handling so that others can listen to
 *                 selection changes
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;

public class BreadcrumbWidget implements ISelectionProvider {

	private AdapterFactoryContentProvider contentProvider;
	private AdapterFactoryLabelProvider labelProvider;

	private final ToolBar toolbar;
	private final List<BreadcrumbItem> items = new ArrayList<>();

	private final ListenerList<ISelectionChangedListener> selectionChangedListeners = new ListenerList<>();

	public BreadcrumbWidget(final Composite parent) {
		toolbar = new ToolBar(parent, SWT.FLAT | SWT.WRAP | SWT.RIGHT);
	}

	public void setInput(final Object input) {
		if (isValidInput(input)) {
			items.forEach(BreadcrumbItem::dispose);
			toolbar.requestLayout();
			final ArrayList<Object> list = new ArrayList<>(); // list of all parent objects
			createItems(input, list);
			Collections.reverse(list); // is needed for correct order in composite
			list.forEach(obj -> items.add(new BreadcrumbItem(this, obj, labelProvider, contentProvider)));
			toolbar.pack();
		}

		final SelectionChangedEvent changeEvent = new SelectionChangedEvent(this, new StructuredSelection(input));
		fireSelectionChanged(changeEvent);
	}

	public void setContentProvider(final AdapterFactoryContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}

	public void setLabelProvider(final AdapterFactoryLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	private static boolean isValidInput(final Object input) {
		return input instanceof IFile || input instanceof AutomationSystem || input instanceof SystemConfiguration
				|| input instanceof Application || input instanceof SubApp || input instanceof Device
				|| input instanceof Resource || input instanceof SubAppType;
	}

	ToolBar getToolBar() {
		return toolbar;
	}

	private void createItems(final Object v, final ArrayList<Object> list) {
		if (v == null) {
			return;
		}
		// if the input is a automation system we have to ignore it,
		// the next object up the tree is the system file which we use instead of the
		// automation system itself
		if (!(v instanceof AutomationSystem)) {
			list.add(v);
		}
		if (!(v instanceof IFile)) { // (file -> AutomationSystem) no need for
			// objects further up the tree
			createItems(contentProvider.getParent(v), list);
		}
	}

	@Override
	public ISelection getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}

	protected void fireSelectionChanged(final SelectionChangedEvent event) {
		for (final ISelectionChangedListener l : selectionChangedListeners) {
			SafeRunnable.run(() -> l.selectionChanged(event));
		}
	}

	@Override
	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		selectionChangedListeners.remove(listener);
	}

	@Override
	public void setSelection(final ISelection selection) {
		if (!selection.isEmpty() && selection instanceof StructuredSelection) {
			setInput(((StructuredSelection) selection).getFirstElement());
		}
	}

}
