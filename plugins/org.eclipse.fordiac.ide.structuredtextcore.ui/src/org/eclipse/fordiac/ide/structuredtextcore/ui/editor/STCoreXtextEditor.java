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

import org.eclipse.fordiac.ide.structuredtextcore.ui.cleanup.STCoreSaveActionsEditor;
import org.eclipse.xtext.ui.editor.XtextEditor;

public class STCoreXtextEditor extends XtextEditor implements STCoreSaveActionsEditor {

	private boolean saveActionsDisabled;

	@Override
	public void doRevertToSaved() {
		setSaveActionsDisabled(true);
		try {
			super.doRevertToSaved();
		} finally {
			setSaveActionsDisabled(false);
		}
	}

	@Override
	public boolean isSaveActionsDisabled() {
		return saveActionsDisabled;
	}

	public void setSaveActionsDisabled(final boolean saveActionsDisabled) {
		this.saveActionsDisabled = saveActionsDisabled;
	}
}
