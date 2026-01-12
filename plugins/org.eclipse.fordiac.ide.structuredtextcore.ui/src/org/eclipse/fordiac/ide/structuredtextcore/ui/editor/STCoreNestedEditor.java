/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.ISaveablePart;

public interface STCoreNestedEditor {

	/**
	 * Save the outer editor
	 *
	 * @param monitor The progress monitor
	 * @see ISaveablePart#doSave(IProgressMonitor)
	 */
	void doSaveOuterEditor(IProgressMonitor monitor);
}
