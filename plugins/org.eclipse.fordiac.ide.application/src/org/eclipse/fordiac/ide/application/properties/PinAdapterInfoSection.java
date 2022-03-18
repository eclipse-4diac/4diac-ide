/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.ui.widgets.ITypeSelectionContentProvider;

public class PinAdapterInfoSection extends PinEventInfoSection {


	@Override
	protected ITypeSelectionContentProvider getTypeSelectionContentProvider() {
		return () -> getTypeLibrary().getBlockTypeLib().getAdapterTypesSorted().stream()
				.map(AdapterTypePaletteEntry::getType).collect(Collectors.toList());
	}

}
