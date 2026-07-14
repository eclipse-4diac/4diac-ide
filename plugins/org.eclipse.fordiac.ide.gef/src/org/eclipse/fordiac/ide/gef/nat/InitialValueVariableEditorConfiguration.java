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

import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;

public class InitialValueVariableEditorConfiguration<T> extends InitialValueGenericEditorConfiguration<T> {

	public InitialValueVariableEditorConfiguration(final IRowDataProvider<? extends T> dataProvider,
			final InitialValueStructuredElementAccessor<T> elementAccessor) {
		super(dataProvider, elementAccessor);
	}

	@Override
	protected InitialValueStructuredCellEditor<T> createStructuredCellEditor() {
		return new InitialValueVariableStructuredCellEditor<>(getDataProvider(), getElementAccessor());
	}
}
