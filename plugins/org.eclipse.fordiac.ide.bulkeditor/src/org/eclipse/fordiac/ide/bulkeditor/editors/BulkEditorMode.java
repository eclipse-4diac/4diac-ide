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
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.editors;

public enum BulkEditorMode {

	VARIABLE, SIMPLE_ATTRIBUTE, ADVANCED_ATTRIBUTE;

	private static final int COMBO_INDEX_VARIABLE = 0;
	private static final int COMBO_INDEX_ATTRIBUTE = 1;

	public static BulkEditorMode resolve(final int comboIndex, final boolean advanced) {
		if (comboIndex == COMBO_INDEX_ATTRIBUTE) {
			return advanced ? ADVANCED_ATTRIBUTE : SIMPLE_ATTRIBUTE;
		}
		return VARIABLE;
	}

	public static int getComboBoxIndex(final BulkEditorMode mode) {
		if (isAttributeMode(mode)) {
			return COMBO_INDEX_ATTRIBUTE;
		}

		return COMBO_INDEX_VARIABLE;
	}

	public static boolean isAttributeMode(final BulkEditorMode mode) {
		return mode == SIMPLE_ATTRIBUTE || mode == ADVANCED_ATTRIBUTE;
	}

	public static boolean isAdvancedMode(final BulkEditorMode mode) {
		return mode == VARIABLE || mode == ADVANCED_ATTRIBUTE;
	}
}