/*******************************************************************************
 * Copyright (c) 2015, 2018 fortiss GmbH, Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.linkhelpers;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.navigator.ILinkHelper;

public abstract class AbstractEditorLinkHelper implements ILinkHelper {

	protected static IEditorPart activateEditor(final IWorkbenchPage aPage, final IEditorInput editorInput) {
		final IEditorPart editor = aPage.findEditor(editorInput);
		if (null != editor) {
			aPage.bringToTop(editor);
		}
		return editor;
	}

}