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
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.emf.compare.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.provider.AdapterFBItemProvider;

public class AdapterFBItemProviderEmfCompare extends AdapterFBItemProvider {

	public AdapterFBItemProviderEmfCompare(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public Object getParent(final Object object) {
		return null;
	}

	@Override
	public Collection<?> getChildren(final Object object) {
		final Collection<Object> children = new ArrayList<>();
		children.add(((AdapterFB) object).getName());
		children.add(((AdapterFB) object).getComment());
		children.add(((AdapterFB) object).getAdapterDecl());
		children.add(((AdapterFB) object).getInterface());
		children.add(((AdapterFB) object).getReturnType());
		return children;
	}

}
