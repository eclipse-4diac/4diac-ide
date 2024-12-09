/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.subapptypeeditor.providers;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.fordiac.ide.model.edit.providers.CFBInstanceItemProviderForSystem;
import org.eclipse.fordiac.ide.model.edit.providers.UntypedSubAppItemProviderForSystem;
import org.eclipse.fordiac.ide.model.libraryElement.provider.LibraryElementItemProviderAdapterFactory;

public class TypedSubappProviderAdapterFactory extends LibraryElementItemProviderAdapterFactory {

	@Override
	public Adapter createSubAppTypeAdapter() {
		if (subAppTypeItemProvider == null) {
			subAppTypeItemProvider = new EditorSubAppTypeItemProvider(this);
		}
		return subAppTypeItemProvider;
	}

	@Override
	public Adapter createTypedSubAppAdapter() {
		if (typedSubAppItemProvider == null) {
			typedSubAppItemProvider = new SubAppItemProviderForTypedSubapps(this);
		}
		return typedSubAppItemProvider;
	}

	@Override
	public Adapter createUntypedSubAppAdapter() {
		if (untypedSubAppItemProvider == null) {
			untypedSubAppItemProvider = new UntypedSubAppItemProviderForSystem(this);
		}
		return untypedSubAppItemProvider;
	}

	@Override
	public Adapter createCFBInstanceAdapter() {
		if (cfbInstanceItemProvider == null) {
			cfbInstanceItemProvider = new CFBInstanceItemProviderForSystem(this);
		}
		return cfbInstanceItemProvider;
	}

}