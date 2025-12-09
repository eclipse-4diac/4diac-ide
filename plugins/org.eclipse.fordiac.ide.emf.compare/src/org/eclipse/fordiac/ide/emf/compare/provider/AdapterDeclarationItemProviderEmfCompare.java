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

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.provider.AdapterDeclarationItemProvider;

public class AdapterDeclarationItemProviderEmfCompare extends AdapterDeclarationItemProvider {

	public AdapterDeclarationItemProviderEmfCompare(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public Object getParent(final Object object) {
		return ((AdapterDeclaration) object).eContainer();
	}

	@Override
	public Collection<?> getChildren(final Object object) {
		return Collections.emptyList();
	}

}
