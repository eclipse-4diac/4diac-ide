/*******************************************************************************
 * Copyright (c) 2023, 2025 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Patrick Aigner - converted into EditorPage
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.ui.editor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.globalconstantseditor.ui.Messages;
import org.eclipse.fordiac.ide.structuredtextcore.ui.cleanup.STCoreSaveActionsEditor;
import org.eclipse.fordiac.ide.structuredtextcore.ui.editor.STCoreEditorPreferences;
import org.eclipse.fordiac.ide.typeeditor.XtextTypeEditorPage;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.ui.IEditorInput;

import com.google.inject.Inject;

public class GlobalConstantsEditor extends XtextTypeEditorPage implements STCoreSaveActionsEditor {
	@Inject
	private STCoreEditorPreferences editorPreferences;

	@Override
	protected void doSetInput(final IEditorInput input) throws CoreException {
		super.doSetInput(input);
		setPartName(Messages.GlobalConstantsEditor);
		setTitleImage(FordiacImage.ICON_ALGORITHM.getImage());
	}

	@Override
	protected boolean shouldEnablePerformanceMode() {
		return getDocument().getNumberOfLines() > editorPreferences.getPerformanceModeThreshold();
	}

	@Override
	public String getEditorId() {
		return getLanguageName();
	}

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
