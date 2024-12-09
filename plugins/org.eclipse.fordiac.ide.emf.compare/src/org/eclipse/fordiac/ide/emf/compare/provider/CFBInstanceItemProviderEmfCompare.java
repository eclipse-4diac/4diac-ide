/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.emf.compare.provider;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.edit.providers.CFBInstanceItemProviderForSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;

public class CFBInstanceItemProviderEmfCompare extends CFBInstanceItemProviderForSystem {

	public CFBInstanceItemProviderEmfCompare(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	protected FBNetwork getFBNetwork(final Object object) {
		return null;
	}

	@Override
	public Object getParent(final Object object) {
		final EObject cont = ((CFBInstance) object).eContainer();
		if (cont != null) {
			return cont;
		}
		return super.getParent(object);
	}
}
