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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.IMemento;

public class BulkEditorSettings {
	enum ScopeOption {
		PROJECT, WORKSPACE, SUBAPP_HIERARCHY
	}

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
	public ScopeOption scope = ScopeOption.PROJECT;
	private static final String LINKED_LIBRARIES_TAG = "_ignoreLinkedLibraries"; //$NON-NLS-1$
	public boolean ignoreLinkedLibraries = true;
	private static final String SELECTED_SUBAPP_HIERARCHIES = "_selectedSubappHierarchies";
	private static final String SELECTED_SUBAPP_HIERARCHY_VALUE = "_hierarchyValue";
	public List<URI> subappHierarchies = Collections.emptyList();

	public static final List<String> whereSearchList = List.of("_where-name", "_where-type", "_where-comment", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"_where-value"); //$NON-NLS-1$
	public static final List<String> inFBTypesSearchList = List.of("_inFBType-name", "_inFBType-type", //$NON-NLS-1$ //$NON-NLS-2$
			"_inFBType-comment"); //$NON-NLS-1$
	public static final List<String> inFBInstanceSearchList = List.of("_inFBInstance-name", "_inFBInstance-type", //$NON-NLS-1$ //$NON-NLS-2$
			"_inFBInstance-comment"); //$NON-NLS-1$
	public static final List<String> inUntypedSubAppSearchList = List.of("_inUntypedSubApp-name", //$NON-NLS-1$
			"_inUntypedSubApp-type", "_inUntypedSubApp-comment"); //$NON-NLS-1$ //$NON-NLS-2$
	public static final List<String> inDataTypesSearchList = List.of("_inDT-name", "_inDT-type", "_inDT-comment"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	public static final List<String> inAttributeTypesSearchList = List.of("_inAT-name", "_inAT-type", "_inAT-comment"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

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
		childMemento.putInteger(SCOPE_TAG, scope.ordinal());
		childMemento.putBoolean(LINKED_LIBRARIES_TAG, ignoreLinkedLibraries);
		for (final URI uri : subappHierarchies) {
			final IMemento childChildMemento = childMemento.createChild(SELECTED_SUBAPP_HIERARCHIES);
			childChildMemento.putString(SELECTED_SUBAPP_HIERARCHY_VALUE, uri.toString());
		}
	}

	public static BulkEditorSettings createFromMemento(final IMemento memento) {
		final BulkEditorSettings settings = new BulkEditorSettings();
		final IMemento childMemento = memento.getChild(TAG_BULKEDITOR_SETTINGS);

		settings.subSettingsMap.values().forEach(subSetting -> subSetting.changeFromMemento(childMemento));
		settings.modeSelection = Optional.ofNullable(childMemento.getInteger(MODE_TAG)).orElse(Integer.valueOf(0))
				.intValue();

		final int scopeValue = Optional.ofNullable(childMemento.getInteger(SCOPE_TAG)).orElse(Integer.valueOf(0))
				.intValue();
		settings.scope = (scopeValue >= 0 && scopeValue < ScopeOption.values().length)
				? ScopeOption.values()[scopeValue]
				: ScopeOption.PROJECT;

		// !Boolean.FALSE.equals for null check with true as fallback value
		settings.fbSubappTypes = !Boolean.FALSE.equals(childMemento.getBoolean(FB_TYPES_TAG));
		settings.fbTypedSubappInstance = !Boolean.FALSE.equals(childMemento.getBoolean(FB_INSTANCES_TAG));
		settings.untypedSubapp = !Boolean.FALSE.equals(childMemento.getBoolean(UNTYPED_SUBAPPS_TAG));
		settings.dataTypes = !Boolean.FALSE.equals(childMemento.getBoolean(DATA_TYPES_TAG));
		settings.attributeTypes = !Boolean.FALSE.equals(childMemento.getBoolean(ATTRIBUTE_TYPES_TAG));
		settings.ignoreLinkedLibraries = !Boolean.FALSE.equals(childMemento.getBoolean(LINKED_LIBRARIES_TAG));

		final IMemento[] childrenChildMemento = childMemento.getChildren(SELECTED_SUBAPP_HIERARCHIES);
		settings.subappHierarchies = new ArrayList<>();
		for (final IMemento element : childrenChildMemento) {
			final String uriString = element.getString(SELECTED_SUBAPP_HIERARCHY_VALUE);
			if (uriString != null) {
				settings.subappHierarchies.add(URI.createURI(uriString));
			}
		}

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
			selected = Boolean.TRUE.equals(childMemento.getBoolean(SELECTED_TAG));
			textField = childMemento.getString(NAME_TAG);
			caseSensitive = Boolean.TRUE.equals(childMemento.getBoolean(CASE_SENSITIVE_TAG));
			wholeWord = Boolean.TRUE.equals(childMemento.getBoolean(WHOLE_WORD_TAG));
			exactMatch = Boolean.TRUE.equals(childMemento.getBoolean(EXACT_MATCH_TAG));
			regularExpression = Boolean.TRUE.equals(childMemento.getBoolean(REGULAR_EXPRESSION_TAG));
		}
	}
}