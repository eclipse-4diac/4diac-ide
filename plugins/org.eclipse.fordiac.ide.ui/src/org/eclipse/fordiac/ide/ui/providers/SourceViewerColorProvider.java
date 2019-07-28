/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 * 	
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Alois Zoitl - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.providers;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.IFindReplaceTarget;
import org.eclipse.jface.text.IFindReplaceTargetExtension;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.texteditor.AbstractTextEditor;

public final class SourceViewerColorProvider {

	/**
	 * Apply the default colors to the given source viewer
	 * 
	 * This code is taken and adjusted from
	 * org.eclipse.ui.texteditor.AbstractTextEditor
	 * 
	 * @param viewer
	 */
	public static void initializeSourceViewerColors(ISourceViewer viewer) {
		IPreferenceStore store = getPreferenceStore();
		if (null != store) {

			StyledText styledText = viewer.getTextWidget();

			// ----------- foreground color --------------------
			Color color = store.getBoolean(AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND_SYSTEM_DEFAULT) ? null
					: getColor(AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND);
			styledText.setForeground(color);

			// ---------- background color ----------------------
			color = store.getBoolean(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT) ? null
					: getColor(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND);
			styledText.setBackground(color);

			// ----------- selection foreground color --------------------
			color = store.getBoolean(AbstractTextEditor.PREFERENCE_COLOR_SELECTION_FOREGROUND_SYSTEM_DEFAULT) ? null
					: getColor(AbstractTextEditor.PREFERENCE_COLOR_SELECTION_FOREGROUND);
			styledText.setSelectionForeground(color);

			// ---------- selection background color ----------------------
			color = store.getBoolean(AbstractTextEditor.PREFERENCE_COLOR_SELECTION_BACKGROUND_SYSTEM_DEFAULT) ? null
					: getColor(AbstractTextEditor.PREFERENCE_COLOR_SELECTION_BACKGROUND);
			styledText.setSelectionBackground(color);

			IFindReplaceTarget target = viewer.getFindReplaceTarget();
			if (target instanceof IFindReplaceTargetExtension) {
				((IFindReplaceTargetExtension) target)
						.setScopeHighlightColor(getColor(AbstractTextEditor.PREFERENCE_COLOR_FIND_SCOPE));
			}
		}
	}

	private static Color getColor(String symbolicName) {
		Color color = JFaceResources.getColorRegistry().get(symbolicName);
		if (null == color) {
			color = createColor(symbolicName);
		}
		return color;
	}

	@SuppressWarnings("restriction")
	private static IPreferenceStore getPreferenceStore() {
		return EditorsPlugin.getDefault().getPreferenceStore();
	}

	private static Color createColor(String symbolicName) {
		IPreferenceStore store = getPreferenceStore();
		if (store.contains(symbolicName)) {
			RGB rgb =  (store.isDefault(symbolicName)) ?
				PreferenceConverter.getDefaultColor(store, symbolicName) :
				PreferenceConverter.getColor(store, symbolicName);

			if (null != rgb) {
				JFaceResources.getColorRegistry().put(symbolicName, rgb);
				return JFaceResources.getColorRegistry().get(symbolicName);
			}
		}

		return null;
	}

	private SourceViewerColorProvider() {
		throw new AssertionError();
	}

}
