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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.part.FileEditorInput;

public class BulkEditorInput extends FileEditorInput {

	private final BulkEditorSettings settings;

	public BulkEditorInput(final IFile file) {
		this(file, new BulkEditorSettings());
	}

	public BulkEditorInput(final IFile file, final BulkEditorSettings settings) {
		super(file);
		this.settings = settings;
	}

	@Override
	public void saveState(final IMemento memento) {
		BulkEditorInputFactory.saveState(memento, this);
	}

	@Override
	public String getFactoryId() {
		return BulkEditorInputFactory.getFactoryId();
	}

	public IProject getProject() {
		return getFile().getProject();
	}

	public BulkEditorSettings getSettings() {
		return settings;
	}
}
