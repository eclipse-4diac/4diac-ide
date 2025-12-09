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
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.provider.AdapterTypeItemProvider;

public class AdapterTypeItemProviderEmfCompare extends AdapterTypeItemProvider {

	public AdapterTypeItemProviderEmfCompare(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public Object getParent(final Object object) {
		final EObject parent = ((AdapterType) object).eContainer();
		if (parent != null) {
			return parent;
		}
		return super.getParent(object);
	}

	@Override
	public Collection<?> getChildren(final Object object) {
		final AdapterType adt = (AdapterType) object;
		final Collection<Object> children = new ArrayList<>();
		children.add(adt.getName());
		children.add(adt.getComment());
		children.add(adt.getVersionInfo());
		children.add(adt.getCompilerInfo());
		children.add(adt.getInterfaceList());
		children.add(adt.getService());
		children.add(adt.getReturnType());
		return children;
	}
}
