/*******************************************************************************
 * Copyright (c) 2021, 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *   Prankur Agarawal	- add handling for internal constant variables
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;

public class VarDeclarationListProvider extends ListDataProvider<VarDeclaration>
implements FordiacInterfaceListProvider<VarDeclaration> {

	public VarDeclarationListProvider(final List<VarDeclaration> list,
			final VarDeclarationColumnAccessor columnAccessor) {
		super(list, columnAccessor);
	}

	public VarDeclarationListProvider(final VarDeclarationColumnAccessor columnAccessor) {
		super(Collections.emptyList(), columnAccessor);
	}

	protected VarDeclarationColumnAccessor getColumnAccessor() {
		return (VarDeclarationColumnAccessor) columnAccessor;
	}

	@Override
	public int getRowCount() {
		if (this.list != null) {
			return super.getRowCount();
		}
		return 0;
	}

	@Override
	public void setInput(final List<VarDeclaration> list) {
		this.list = list;
	}

	public void setTypeLib(final DataTypeLibrary dataTypeLib) {
		getColumnAccessor().setTypeLib(dataTypeLib);
	}

}