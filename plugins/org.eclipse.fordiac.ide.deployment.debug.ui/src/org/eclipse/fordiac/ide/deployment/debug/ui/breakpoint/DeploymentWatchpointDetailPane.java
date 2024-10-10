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
package org.eclipse.fordiac.ide.deployment.debug.ui.breakpoint;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.debug.ui.IDetailPane3;
import org.eclipse.fordiac.ide.deployment.debug.breakpoint.DeploymentWatchpoint;
import org.eclipse.fordiac.ide.deployment.debug.ui.Messages;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.IWorkbenchPartSite;

public class DeploymentWatchpointDetailPane implements IDetailPane3 {

	public static final String ID = "org.eclipse.fordiac.ide.deployment.debug.ui.watchpoint.detailPane"; //$NON-NLS-1$

	private final ListenerList<IPropertyListener> listeners = new ListenerList<>();
	private Composite comp;
	private DeploymentWatchpointForceEditor editor;

	@Override
	public void init(final IWorkbenchPartSite partSite) {
		// do nothing
	}

	@Override
	public Control createControl(final Composite parent) {
		comp = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(comp);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(comp);

		editor = new DeploymentWatchpointForceEditor();
		editor.addPropertyListener((source, propId) -> {
			if (propId == DeploymentWatchpointForceEditor.PROP_FORCE_ENABLED) {
				editor.doSave(); // autosave only for condition enabled changes
			}
			firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
		});
		editor.createControl(comp);

		return comp;
	}

	@Override
	public void display(final IStructuredSelection selection) {
		if (selection != null && selection.size() == 1
				&& selection.getFirstElement() instanceof final DeploymentWatchpoint watchpoint) {
			editor.setInput(watchpoint);
		} else {
			editor.setInput(null);
		}
	}

	@Override
	public boolean setFocus() {
		return editor.setFocus();
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public String getName() {
		return Messages.DeploymentWatchpointDetailPane_Name;
	}

	@Override
	public String getDescription() {
		return Messages.DeploymentWatchpointDetailPane_Description;
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		editor.doSave();
	}

	@Override
	public void doSaveAs() {
		// do nothing
	}

	@Override
	public boolean isDirty() {
		return editor != null && editor.isDirty();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public boolean isSaveOnCloseNeeded() {
		return isDirty();
	}

	@Override
	public void dispose() {
		listeners.clear();
		editor.dispose();
		comp.dispose();
		editor = null;
		comp = null;
	}

	@Override
	public void addPropertyListener(final IPropertyListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removePropertyListener(final IPropertyListener listener) {
		listeners.remove(listener);
	}

	protected void firePropertyChange(final int property) {
		for (final IPropertyListener listener : listeners) {
			listener.propertyChanged(this, property);
		}
	}
}
