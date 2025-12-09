/*******************************************************************************
 * Copyright (c) 2023 Jonannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Prankur Agarwal - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.ui.providers;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultRowHeaderDataProvider;

public class RowHeaderDataProvider extends DefaultRowHeaderDataProvider {

	private final boolean isInput;

	public RowHeaderDataProvider(final IDataProvider bodyDataProvider, final boolean isInput) {
		super(bodyDataProvider);
		this.isInput = isInput;
	}

	public IDataProvider getBodyDataProvider() {
		return this.bodyDataProvider;
	}

	public boolean isInput() {
		return this.isInput;
	}

}
