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

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.provider.VarDeclarationItemProvider;

public class VarDeclarationItemProviderEmfCompare extends VarDeclarationItemProvider {

	public VarDeclarationItemProviderEmfCompare(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public Object getParent(final Object object) {
		return ((VarDeclaration) object).eContainer();
	}

	@Override
	public Collection<?> getChildren(final Object object) {
		final Collection<Object> children = new ArrayList<>();
		children.addAll(((VarDeclaration) object).getWiths());
		children.add(((VarDeclaration) object).getValue());
		children.add(Boolean.valueOf(((VarDeclaration) object).isVarConfig()));
		return children;
	}

}
