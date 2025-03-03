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
package org.eclipse.fordiac.ide.model.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceStoreProvider;

public interface IGraphicalPreferencesCache {
	PreferenceStoreProvider getStoreProvider();

	IProject getProject();

	int getMaxDefaultValueLength();

	int getMaxHiddenConnectionLabelSize();

	int getMaxInterfaceBarSize();

	int getMaxPinLabelSize();

	int getMaxTypeLabelSize();

	int getMaxValueLabelSize();

	int getMinInterfaceBarSize();

	int getMinPinLabelSize();

	String getPinLabelStyle();

	boolean isSnapToGrid();
}
