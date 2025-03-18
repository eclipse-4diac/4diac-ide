/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.bulkeditor.editors;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;

public class BulkEditorInput implements IEditorInput, IPersistableElement {

	private final IProject project;
	private final BulkEditorSettings settings;

	public BulkEditorInput(final IProject project) {
		this(project, new BulkEditorSettings());
	}

	public BulkEditorInput(final IProject project, final BulkEditorSettings settings) {
		this.project = project;
		this.settings = settings;
	}

	public IProject getProject() {
		return project;
	}

	public BulkEditorSettings getSettings() {
		return settings;
	}

	@Override
	public void saveState(final IMemento memento) {
		BulkEditorInputFactory.saveState(memento, this);
	}

	@Override
	public String getFactoryId() {
		return BulkEditorInputFactory.getFactoryId();
	}

	@Override
	public boolean exists() {
		return project.exists();
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return project.getName();
	}

	@Override
	public IPersistableElement getPersistable() {
		return this;
	}

	@Override
	public String getToolTipText() {
		return project.getName();
	}

	@Override
	public int hashCode() {
		return project.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof final BulkEditorInput bei) {
			return project.equals(bei.getProject());
		}
		return false;
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		return null;
	}
}
