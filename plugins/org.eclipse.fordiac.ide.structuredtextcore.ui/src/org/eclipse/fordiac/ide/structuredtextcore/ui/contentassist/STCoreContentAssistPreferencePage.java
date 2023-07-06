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
package org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist;

import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.xtext.ui.editor.preferences.AbstractPreferencePage;

public class STCoreContentAssistPreferencePage extends AbstractPreferencePage {

	@Override
	protected void createFieldEditors() {
		final Composite parent = getFieldEditorParent();
		final Group contentAssistGroup = new Group(parent, SWT.SHADOW_IN);
		contentAssistGroup.setText(Messages.STCoreContentAssistPreferencePage_ContentAssist);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).grab(true, false).applyTo(contentAssistGroup);
		GridLayoutFactory.swtDefaults().applyTo(contentAssistGroup);

		final Composite contentAssistComposite = new Composite(contentAssistGroup, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(contentAssistComposite);
		addField(new StringFieldEditor(STCoreContentAssistPreferences.COMPLETION_AUTO_ACTIVATION_CHARS,
				Messages.STCoreContentAssistPreferencePage_AutoActivationTriggers, contentAssistComposite));
		contentAssistGroup.pack();
	}

	@Override
	public boolean performOk() {
		if (super.performOk()) {
			MessageDialog.openInformation(getShell(), getTitle(),
					Messages.STCoreContentAssistPreferencePage_ChangedSettingsEffect);
			return true;
		}
		return false;
	}
}
