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

import org.eclipse.fordiac.ide.model.ui.nat.AdapterTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.AdapterTypeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.ITypeSelectionContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class PinAdapterInfoSection extends PinEventInfoSection {

	@Override
	protected ITypeSelectionContentProvider getTypeSelectionContentProvider() {
		return AdapterTypeSelectionContentProvider.INSTANCE;
	}

	@Override
	protected ITreeContentProvider getTypeSelectionTreeContentProvider() {
		return AdapterTypeSelectionTreeContentProvider.INSTANCE;
	}
}
