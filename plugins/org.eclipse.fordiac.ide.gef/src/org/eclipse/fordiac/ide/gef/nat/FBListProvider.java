/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Prankur Agarawal	- initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;

public class FBListProvider extends ListDataProvider<FB>
implements FordiacInterfaceListProvider {

	public FBListProvider(final List<FB> list,
			final FBColumnAccessor columnAccessor) {
		super(list, columnAccessor);
	}

	protected FBColumnAccessor getColumnAccessor() {
		return (FBColumnAccessor) columnAccessor;
	}

	@Override
	public int getRowCount() {
		if (this.list != null) {
			return super.getRowCount();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends List<? extends INamedElement>> void setInput(final T list) {
		this.list = (List<FB>) list;
	}

	public void setTypeLib(final TypeLibrary typeLib) {
		getColumnAccessor().setTypeLib(typeLib);
	}

}