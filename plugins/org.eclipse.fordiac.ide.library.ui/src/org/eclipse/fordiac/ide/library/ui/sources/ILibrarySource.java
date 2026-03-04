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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.sources;

import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;

public interface ILibrarySource {
	String id();

	String comboLabelText();

	void createConfigUI(Composite parent);

	LibrarySourceUIComponents loadLibrarySource(IProgressMonitor monitor) throws Exception;

	boolean isSelectableLeaf(Object element);

	void install(IProject targetProject, Collection<?> selectedLeafElements, IProgressMonitor monitor) throws Exception;

	default void dispose() {
		// optional
	}

	default String exclusiveVersinSelectionKey(final Object element) {
		return null;
	}

}