/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.cleanup;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.Region;
import org.eclipse.xtext.ui.editor.IXtextEditorCallback;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.formatting.IContentFormatterFactory;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;

import com.google.inject.Inject;

public class STCoreCleanupEditorCallback extends IXtextEditorCallback.NullImpl {

	@Inject
	private STCoreSaveActionsPreferences preferences;

	@Inject(optional = true)
	private IContentFormatterFactory contentFormatterFactory;

	private boolean running = false;

	@Override
	public void afterSave(final XtextEditor editor) {
		if (!running && preferences.isEnableSaveActions()) {
			try {
				running = true;
				performSaveActions(editor);
				if (editor.isDirty()) {
					editor.doSave(new NullProgressMonitor());
				}
			} finally {
				running = false;
			}
		}
	}

	protected void performSaveActions(final XtextEditor editor) {
		final IXtextDocument document = editor.getDocument();
		if (contentFormatterFactory != null && preferences.isEnableFormat()) {
			contentFormatterFactory.createConfiguredFormatter(null, null).format(document,
					new Region(0, document.getLength()));
		}
	}
}
