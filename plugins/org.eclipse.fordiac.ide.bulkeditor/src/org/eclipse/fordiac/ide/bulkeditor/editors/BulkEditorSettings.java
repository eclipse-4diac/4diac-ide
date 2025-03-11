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

import org.eclipse.ui.IMemento;

public class BulkEditorSettings {
	private static final String TAG_BULKEDITOR_SETTINGS = "BULKEDITOR_SETTINGS"; //$NON-NLS-1$
	private static final String MODE_TAG = "_mode"; //$NON-NLS-1$
	private static final String NAME_SUB_SETTING_ADDITION = "_name_"; //$NON-NLS-1$

	public int modeSelection = 0;
	public BulkEditorSubSettings nameSubSettings = new BulkEditorSubSettings(NAME_SUB_SETTING_ADDITION);

	public void saveState(final IMemento memento) {
		memento.putInteger(TAG_BULKEDITOR_SETTINGS + MODE_TAG, modeSelection);
		nameSubSettings.saveState(memento);
	}

	public static BulkEditorSettings createFromMemento(final IMemento memento) {
		final BulkEditorSettings settings = new BulkEditorSettings();

		settings.modeSelection = memento.getInteger(TAG_BULKEDITOR_SETTINGS + MODE_TAG);
		settings.nameSubSettings = BulkEditorSubSettings.createFromMemento(NAME_SUB_SETTING_ADDITION, memento);

		return settings;
	}

	public static class BulkEditorSubSettings {
		private static final String SELECTED_TAG = "_selected"; //$NON-NLS-1$
		private static final String NAME_TAG = "_name"; //$NON-NLS-1$
		private static final String CASE_SENSITIVE_TAG = "_caseSensitve"; //$NON-NLS-1$
		private static final String WHOLE_WORD_TAG = "_wholeWord"; //$NON-NLS-1$
		private static final String REGULAR_EXPRESSION_TAG = "_regularExpression"; //$NON-NLS-1$

		private final String tagBulkEditorSubSettings;

		public boolean selected = false;
		public String textField = ""; //$NON-NLS-1$
		public boolean caseSensitve = false;
		public boolean wholeWord = false;
		public boolean regularExpression = false;

		public BulkEditorSubSettings(final String nameAddition) {
			tagBulkEditorSubSettings = TAG_BULKEDITOR_SETTINGS + nameAddition;
		}

		public void saveState(final IMemento memento) {
			memento.putBoolean(tagBulkEditorSubSettings + SELECTED_TAG, selected);
			memento.putString(tagBulkEditorSubSettings + NAME_TAG, textField);
			memento.putBoolean(tagBulkEditorSubSettings + CASE_SENSITIVE_TAG, caseSensitve);
			memento.putBoolean(tagBulkEditorSubSettings + WHOLE_WORD_TAG, wholeWord);
			memento.putBoolean(tagBulkEditorSubSettings + REGULAR_EXPRESSION_TAG, regularExpression);
		}

		public static BulkEditorSubSettings createFromMemento(final String nameAddition, final IMemento memento) {
			final BulkEditorSubSettings subSettings = new BulkEditorSubSettings(nameAddition);

			subSettings.selected = memento.getBoolean(subSettings.tagBulkEditorSubSettings + SELECTED_TAG);
			subSettings.textField = memento.getString(subSettings.tagBulkEditorSubSettings + NAME_TAG);
			subSettings.caseSensitve = memento.getBoolean(subSettings.tagBulkEditorSubSettings + CASE_SENSITIVE_TAG);
			subSettings.wholeWord = memento.getBoolean(subSettings.tagBulkEditorSubSettings + WHOLE_WORD_TAG);
			subSettings.regularExpression = memento
					.getBoolean(subSettings.tagBulkEditorSubSettings + REGULAR_EXPRESSION_TAG);

			return subSettings;
		}
	}
}