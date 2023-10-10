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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.provider.AdapterFBTypeItemProvider;

public class AdapterFBTypeItemProviderEmfCompare extends AdapterFBTypeItemProvider {

	public AdapterFBTypeItemProviderEmfCompare(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public Object getParent(final Object object) {
		final EObject parent = ((AdapterFBType) object).eContainer().eContainer();
		if (parent != null) {
			return parent;
		}
		return super.getParent(object);
	}

	@Override
	public Collection<?> getChildren(final Object object) {
		final Collection<Object> children = new ArrayList<>();
		children.add(((AdapterFBType) object).getName());
		children.add(((AdapterFBType) object).getComment());
		children.add(((AdapterFBType) object).getVersionInfo());
		children.add(((AdapterFBType) object).getCompilerInfo());
		children.add(((AdapterFBType) object).getInterfaceList());
		children.add(((AdapterFBType) object).getService());
		children.add(((AdapterFBType) object).getReturnType());
		return children;
	}

}
