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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;

public class InitialValueVariableDataLayer extends DataLayer {

	public InitialValueVariableDataLayer(final IDataProvider dataProvider) {
		super(dataProvider);
	}

	@Override
	protected void registerCommandHandlers() {
		super.registerCommandHandlers();

		DefaultCellUpdateDataCommandHandler.register(this);
	}
}
