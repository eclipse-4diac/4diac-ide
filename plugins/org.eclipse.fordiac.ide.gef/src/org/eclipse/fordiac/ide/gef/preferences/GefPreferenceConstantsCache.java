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
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.fordiac.ide.gef.draw2d.ConnectorBorder;
import org.eclipse.fordiac.ide.model.ui.preferences.IGraphicalPreferencesCache;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceStoreProvider;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;
import org.eclipse.jface.resource.JFaceResources;

public class GefPreferenceConstantsCache implements IGraphicalPreferencesCache {
	private final IProject project;
	private final PreferenceStoreProvider storeProvider;

	private final int maxDefaultValueLength;
	private final int maxHiddenConnectionLabelSize;
	private final int maxInterfaceBarSize;
	private final int maxPinLabelSize;
	private final int maxTypeLabelSize;
	private final int maxValueLabelSize;
	private final int minInterfaceBarSize;
	private final int minPinLabelSize;
	private final String pinLabelStyle;
	private final boolean snapToGrid;

	private static final int LEFT_RIGHT_MARGIN = 5;

	public GefPreferenceConstantsCache(final IProject project) {
		this.project = project;
		this.storeProvider = new PreferenceStoreProvider(GefPreferenceConstants.GEF_PREFERENCES_ID, project);
		final var store = storeProvider.getStore();

		final Dimension singleCharLength = FigureUtilities.getStringExtents(" ", //$NON-NLS-1$
				JFaceResources.getFontRegistry().get(UIPreferenceConstants.DIAGRAM_FONT));

		minInterfaceBarSize = singleCharLength.width * store.getInt(GefPreferenceConstants.MIN_INTERFACE_BAR_SIZE)
				+ ConnectorBorder.LR_MARGIN + LEFT_RIGHT_MARGIN;
		maxInterfaceBarSize = store.getInt(GefPreferenceConstants.MAX_INTERFACE_BAR_SIZE);
		maxHiddenConnectionLabelSize = singleCharLength.width
				* store.getInt(GefPreferenceConstants.MAX_HIDDEN_CONNECTION_LABEL_SIZE);
		maxDefaultValueLength = store.getInt(GefPreferenceConstants.MAX_DEFAULT_VALUE_LENGTH);
		maxTypeLabelSize = store.getInt(GefPreferenceConstants.MAX_TYPE_LABEL_SIZE);
		maxValueLabelSize = store.getInt(GefPreferenceConstants.MAX_VALUE_LABEL_SIZE);
		minPinLabelSize = store.getInt(GefPreferenceConstants.MIN_PIN_LABEL_SIZE);
		maxPinLabelSize = store.getInt(GefPreferenceConstants.MAX_PIN_LABEL_SIZE);
		pinLabelStyle = store.getString(GefPreferenceConstants.PIN_LABEL_STYLE);
		snapToGrid = store.getBoolean(GefPreferenceConstants.SNAP_TO_GRID);
	}

	@Override
	public PreferenceStoreProvider getStoreProvider() {
		return storeProvider;
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public int getMaxDefaultValueLength() {
		return maxDefaultValueLength;
	}

	@Override
	public int getMaxHiddenConnectionLabelSize() {
		return maxHiddenConnectionLabelSize;
	}

	@Override
	public int getMaxInterfaceBarSize() {
		return maxInterfaceBarSize;
	}

	@Override
	public int getMaxPinLabelSize() {
		return maxPinLabelSize;
	}

	@Override
	public int getMaxTypeLabelSize() {
		return maxTypeLabelSize;
	}

	@Override
	public int getMaxValueLabelSize() {
		return maxValueLabelSize;
	}

	@Override
	public int getMinInterfaceBarSize() {
		return minInterfaceBarSize;
	}

	@Override
	public int getMinPinLabelSize() {
		return minPinLabelSize;
	}

	@Override
	public String getPinLabelStyle() {
		return pinLabelStyle;
	}

	@Override
	public boolean isSnapToGrid() {
		return snapToGrid;
	}
}
