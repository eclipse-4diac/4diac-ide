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

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;

public class BulkEditorInputFactory implements IElementFactory {

	private static final String ID_FACTORY = "org.eclipse.fordiac.ide.bulkeditor.BulkEditorInputFactory"; //$NON-NLS-1$

	private static final String TAG_BULKEDITOR = "BULKEDITOR"; //$NON-NLS-1$

	@Override
	public IAdaptable createElement(final IMemento memento) {
		final var project = ResourcesPlugin.getWorkspace().getRoot().getProject(memento.getString(TAG_BULKEDITOR));

		if (project != null) {
			return new BulkEditorInput(project.getFile(project.getFullPath()),
					BulkEditorSettings.createFromMemento(memento));
		}

		return null;
	}

	public static void saveState(final IMemento memento, final BulkEditorInput input) {
		memento.putString(TAG_BULKEDITOR, input.getProject().getName());

		input.getSettings().saveState(memento);
	}

	public static String getFactoryId() {
		return ID_FACTORY;
	}
}
