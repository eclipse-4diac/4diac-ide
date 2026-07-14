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

import java.util.Optional;

import org.eclipse.fordiac.ide.gef.dialogs.InitialValueVariableDialog;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.swt.widgets.Shell;

public class InitialValueVariableStructuredCellEditor<T> extends InitialValueStructuredCellEditor<T> {

	public InitialValueVariableStructuredCellEditor(final IRowDataProvider<? extends T> dataProvider,
			final InitialValueStructuredElementAccessor<T> elementAccessor) {
		super(dataProvider, elementAccessor);
	}

	public InitialValueVariableStructuredCellEditor(final IRowDataProvider<? extends T> dataProvider,
			final InitialValueStructuredElementAccessor<T> elementAccessor, final boolean moveSelectionOnEnter) {
		super(dataProvider, elementAccessor, moveSelectionOnEnter);
	}

	@Override
	protected Optional<String> openVariableDialog(final Shell shell, final ITypedElement element,
			final String initialValue) {
		return InitialValueVariableDialog.open(shell, element, initialValue);
	}
}
