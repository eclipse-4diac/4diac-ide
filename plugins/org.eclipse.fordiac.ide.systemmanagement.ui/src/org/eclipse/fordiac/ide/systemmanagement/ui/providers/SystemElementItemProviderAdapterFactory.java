/*******************************************************************************
 * Copyright (c) 2015, 2017 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.providers;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.edit.providers.CFBInstanceItemProviderForSystem;
import org.eclipse.fordiac.ide.model.edit.providers.UntypedSubAppItemProviderForSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.provider.FBItemProvider;
import org.eclipse.fordiac.ide.model.libraryElement.provider.LibraryElementItemProviderAdapterFactory;

public class SystemElementItemProviderAdapterFactory extends LibraryElementItemProviderAdapterFactory {

	@Override
	public Adapter createApplicationAdapter() {
		if (applicationItemProvider == null) {
			applicationItemProvider = new ApplicationItemProviderForSystem(this);
		}
		return applicationItemProvider;
	}

	@Override
	public Adapter createTypedSubAppAdapter() {
		if (typedSubAppItemProvider == null) {
			typedSubAppItemProvider = new TypedSubAppItemProviderForSystem(this);
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
	public Adapter createFBAdapter() {
		if (fbItemProvider == null) {
			fbItemProvider = new FBItemProvider(this) {

				/**
				 * we are not showing the real parent of FBs (i.e., FBNetwork or SubAppNetwork)
				 * in the tree. In order to ensure correct CNF behavior we need to provide a
				 * special getparent
				 */
				@Override
				public Object getParent(final Object object) {
					final EObject cont = ((FB) object).eContainer();
					if (cont instanceof final FBNetwork fbn) {
						return fbn.eContainer();
					}
					return super.getParent(object);
				}
			};
		}
		return fbItemProvider;
	}

	@Override
	public Adapter createCFBInstanceAdapter() {
		if (cfbInstanceItemProvider == null) {
			cfbInstanceItemProvider = new CFBInstanceItemProviderForSystem(this);
		}
		return cfbInstanceItemProvider;
	}

}