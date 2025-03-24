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

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.ui.IMemento;

public class BulkEditorSettings {
	private static final String TAG_BULKEDITOR_SETTINGS = "BULKEDITOR_SETTINGS"; //$NON-NLS-1$

	private static final String MODE_TAG = "_mode"; //$NON-NLS-1$
	public int modeSelection = 0;
	private static final String FB_TYPES_TAG = "_fbTypes"; //$NON-NLS-1$
	public boolean fbSubappTypes = true;
	private static final String FB_INSTANCES_TAG = "_fbInstances"; //$NON-NLS-1$
	public boolean fbTypedSubappInstance = true;
	private static final String UNTYPED_SUBAPPS_TAG = "_untypedSubapps"; //$NON-NLS-1$
	public boolean untypedSubapp = true;
	private static final String DATA_TYPES_TAG = "_dataTypes"; //$NON-NLS-1$
	public boolean dataTypes = true;
	private static final String ATTRIBUTE_TYPES_TAG = "_attributeTypes"; //$NON-NLS-1$
	public boolean attributeTypes = true;
	private static final String SCOPE_TAG = "_scope"; //$NON-NLS-1$
	public boolean projectScope = true;

	public static final List<String> whereSearchList = List.of("_where-name", "_where-type", "_where-comment",
			"_where-value");
	public static final List<String> inFBTypesSearchList = List.of("_inFBType-name", "_inFBType-type",
			"_inFBType-comment");
	public static final List<String> inFBInstanceSearchList = List.of("_inFBInstance-name", "_inFBInstance-type",
			"_inFBInstance-comment");
	public static final List<String> inUntypedSubAppSearchList = List.of("_inUntypedSubApp-name",
			"_inUntypedSubApp-type", "_inUntypedSubApp-comment");
	public static final List<String> inDataTypesSearchList = List.of("_inDT-name", "_inDT-type", "_inDT-comment");
	public static final List<String> inAttributeTypesSearchList = List.of("_inAT-name", "_inAT-type", "_inAT-comment");

	private final Map<String, BulkEditorSubSettings> subSettingsMap = Stream
			.of(whereSearchList, inFBTypesSearchList, inFBInstanceSearchList, inUntypedSubAppSearchList,
					inDataTypesSearchList, inAttributeTypesSearchList)
			.flatMap(List::stream).map(BulkEditorSettings::createSubSettingEntry)
			.collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

	private static Entry<String, BulkEditorSubSettings> createSubSettingEntry(final String ref) {
		return Map.entry(ref, new BulkEditorSubSettings(ref));
	}

	public BulkEditorSubSettings getSubSettings(final String ref) {
		return subSettingsMap.get(ref);
	}

	public void saveState(final IMemento memento) {
		final IMemento childMemento = memento.createChild(TAG_BULKEDITOR_SETTINGS);
		subSettingsMap.values().forEach(subSetting -> subSetting.saveState(childMemento));
		childMemento.putInteger(MODE_TAG, modeSelection);
		childMemento.putBoolean(FB_TYPES_TAG, fbSubappTypes);
		childMemento.putBoolean(FB_INSTANCES_TAG, fbTypedSubappInstance);
		childMemento.putBoolean(UNTYPED_SUBAPPS_TAG, untypedSubapp);
		childMemento.putBoolean(DATA_TYPES_TAG, dataTypes);
		childMemento.putBoolean(ATTRIBUTE_TYPES_TAG, attributeTypes);
		childMemento.putBoolean(SCOPE_TAG, projectScope);
	}

	public static BulkEditorSettings createFromMemento(final IMemento memento) {
		final BulkEditorSettings settings = new BulkEditorSettings();
		final IMemento childMemento = memento.getChild(TAG_BULKEDITOR_SETTINGS);

		settings.subSettingsMap.values().forEach(subSetting -> subSetting.changeFromMemento(childMemento));
		settings.modeSelection = childMemento.getInteger(MODE_TAG);
		settings.fbSubappTypes = childMemento.getBoolean(FB_TYPES_TAG);
		settings.fbTypedSubappInstance = childMemento.getBoolean(FB_INSTANCES_TAG);
		settings.untypedSubapp = childMemento.getBoolean(UNTYPED_SUBAPPS_TAG);
		settings.dataTypes = childMemento.getBoolean(DATA_TYPES_TAG);
		settings.attributeTypes = childMemento.getBoolean(ATTRIBUTE_TYPES_TAG);
		settings.projectScope = childMemento.getBoolean(SCOPE_TAG);

		return settings;
	}

	public static class BulkEditorSubSettings {
		private static final String SELECTED_TAG = "_selected"; //$NON-NLS-1$
		private static final String NAME_TAG = "_name"; //$NON-NLS-1$
		private static final String CASE_SENSITIVE_TAG = "_caseSensitve"; //$NON-NLS-1$
		private static final String WHOLE_WORD_TAG = "_wholeWord"; //$NON-NLS-1$
		private static final String EXACT_MATCH_TAG = "_exactMatch"; //$NON-NLS-1$
		private static final String REGULAR_EXPRESSION_TAG = "_regularExpression"; //$NON-NLS-1$

		private final String tagBulkEditorSubSettings;

		public boolean selected = false;
		public String textField = ""; //$NON-NLS-1$
		public boolean caseSensitive = false;
		public boolean wholeWord = false;
		public boolean exactMatch = false;
		public boolean regularExpression = false;

		public BulkEditorSubSettings(final String nameAddition) {
			tagBulkEditorSubSettings = nameAddition;
		}

		public void saveState(final IMemento memento) {
			final IMemento childMemento = memento.createChild(tagBulkEditorSubSettings);
			childMemento.putBoolean(SELECTED_TAG, selected);
			childMemento.putString(NAME_TAG, textField);
			childMemento.putBoolean(CASE_SENSITIVE_TAG, caseSensitive);
			childMemento.putBoolean(WHOLE_WORD_TAG, wholeWord);
			childMemento.putBoolean(EXACT_MATCH_TAG, exactMatch);
			childMemento.putBoolean(REGULAR_EXPRESSION_TAG, regularExpression);
		}

		public void changeFromMemento(final IMemento memento) {
			final IMemento childMemento = memento.getChild(tagBulkEditorSubSettings);
			selected = childMemento.getBoolean(SELECTED_TAG);
			textField = childMemento.getString(NAME_TAG);
			caseSensitive = childMemento.getBoolean(CASE_SENSITIVE_TAG);
			wholeWord = childMemento.getBoolean(WHOLE_WORD_TAG);
			exactMatch = childMemento.getBoolean(EXACT_MATCH_TAG);
			regularExpression = childMemento.getBoolean(REGULAR_EXPRESSION_TAG);
		}
	}
}