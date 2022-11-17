/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
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

import java.util.List;

import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;

public class VarDeclarationListProvider extends ListDataProvider<VarDeclaration> {

	public VarDeclarationListProvider(final AbstractSection section, final List<VarDeclaration> list) {
		super(list, new VarDeclarationColumnAccessor(section));
	}

	@Override
	public int getRowCount() {
		if (this.list != null) {
			return super.getRowCount();
		}
		return 0;
	}

	public void setInput(final Object inputElement) {
		if (inputElement instanceof BaseFBType) {
			this.list = ((BaseFBType) inputElement).getInternalVars();
		}
	}
}