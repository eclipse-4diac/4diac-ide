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
 *   Daniel Lindhuber - added path functionality
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
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

	private static final String SEPARATOR = "/"; //$NON-NLS-1$
	private AdapterFactoryContentProvider contentProvider;
	private AdapterFactoryLabelProvider labelProvider;

	private final ToolBar toolbar;
	private final List<BreadcrumbItem> items = new ArrayList<>();

	private final ListenerList<ISelectionChangedListener> selectionChangedListeners = new ListenerList<>();

	public BreadcrumbWidget(final Composite parent) {
		toolbar = new ToolBar(parent, SWT.FLAT | SWT.WRAP | SWT.RIGHT);
		toolbar.addDisposeListener(e -> items.forEach(BreadcrumbItem::dispose));
	}

	public void setInput(final Object input) {
		setInput(input, new SelectionChangedEvent(this, new StructuredSelection(input)));
	}

	public void setInput(final Object input, final SelectionChangedEvent event) {
		if (!items.isEmpty() && getActiveItem().getModel().equals(input)) {
			return;
		}

		if (isValidBreadcrumbInput(input)) {
			items.forEach(BreadcrumbItem::dispose);
			items.clear();
			toolbar.requestLayout();
			createBreadcrumbItems(input);
			toolbar.pack();
		}
		fireSelectionChanged(event);
	}

	private void createBreadcrumbItems(final Object input) {
		final ArrayList<Object> parentObjects = new ArrayList<>();
		createItems(input, parentObjects);
		Collections.reverse(parentObjects); // is needed for correct order in composite
		parentObjects.forEach(obj -> items.add(new BreadcrumbItem(this, obj, labelProvider, contentProvider)));
	}

	public void setContentProvider(final AdapterFactoryContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}

	public void setLabelProvider(final AdapterFactoryLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	public AdapterFactoryContentProvider getContentProvider() {
		return contentProvider;
	}

	public AdapterFactoryLabelProvider getLabelProvider() {
		return labelProvider;
	}

	private static boolean isValidBreadcrumbInput(final Object input) {
		// @formatter:off
		return   input instanceof IFile
				|| input instanceof AutomationSystem
				|| input instanceof SystemConfiguration
				|| input instanceof Application
				|| input instanceof Device
				|| input instanceof Resource
				|| input instanceof SubApp
				|| input instanceof SubAppType
				|| isCompositeType(input);
		// @formatter:on
	}

	ToolBar getToolBar() {
		return toolbar;
	}

	public BreadcrumbItem getActiveItem() {
		return items.get(items.size() - 1);
	}

	public String serializePath() {
		return items.stream().map(item -> SEPARATOR + item.getText()).collect(Collectors.joining());
	}

	public boolean openPath(final String path, final AutomationSystem system) {
		return validateAndOpenPath(path, system);
	}

	public boolean openPath(final String path, final SubAppType type) {
		return validateAndOpenPath(path, type);
	}

	private boolean validateAndOpenPath(final String path, final INamedElement parent) {
		if (path.isBlank()) {
			return false;
		}

		final String[] tokens = path.substring(1).split(SEPARATOR); // remove first "/" and split

		if (tokens.length == 0) {
			return false;
		}

		if (parent.getName().equals(tokens[0])) {
			if (tokens.length == 1) {
				setInput(parent); // the system or type itself
				return true;
			}
			Object current = parent;
			for (int i = 1; i < tokens.length; i++) {
				final Object child = getMatchingPathChild(current, tokens[i]);
				if (child == null) {
					return false;
				}
				if (i == tokens.length - 1) {
					setInput(child); // last token, therefore we are at our destination
					return true;
				}
				current = child;
			}
		}
		return false;
	}

	private Object getMatchingPathChild(final Object current, final String token) {
		final Object[] children = contentProvider.getChildren(current);
		for (final Object child : children) {
			if (labelProvider.getText(child).equals(token)) {
				return child;
			}
		}
		return null;
	}

	// recursive function for collecting parent objects
	private void createItems(final Object input, final ArrayList<Object> parentObjects) {
		if (input == null || (input instanceof org.eclipse.emf.ecore.resource.Resource)) {
			return;
		}

		// Do not add any FBNetwork into the breadcrumb as item
		if (!(input instanceof FBNetwork)) {
			parentObjects.add(input);
		}
		if (input instanceof AutomationSystem) {
			return;
		}
		createItems(contentProvider.getParent(input), parentObjects);
	}

	@Override
	public ISelection getSelection() {
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
		if (!selection.isEmpty() && selection instanceof final StructuredSelection structSel) {
			setInput(structSel.getFirstElement());
		}
	}

	private static boolean isCompositeType(final Object input) {
		return (input instanceof CFBInstance);
	}
}
