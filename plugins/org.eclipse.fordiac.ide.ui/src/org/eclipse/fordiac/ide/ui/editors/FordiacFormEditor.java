/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.editors;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.ui.forms.editor.FormEditor;

/* extension of the form editor for saver getEditor and global notifications on page changes
 *
 */
public abstract class FordiacFormEditor extends FormEditor {

	@Override
	protected void pageChange(final int newPageIndex) {
		super.pageChange(newPageIndex);
		// notify the part service that we have a new page activated. This is needed
		// that for example link with editor
		// gets notified.
		final EPartService partService = getSite().getService(EPartService.class);
		final MPart activePart = partService.getActivePart();
		partService.activate(null);
		partService.activate(activePart);
	}
}
