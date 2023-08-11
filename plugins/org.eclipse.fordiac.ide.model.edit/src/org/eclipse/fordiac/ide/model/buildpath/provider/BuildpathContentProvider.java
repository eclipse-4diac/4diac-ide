/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.buildpath.provider;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;

public class BuildpathContentProvider extends AdapterFactoryContentProvider {

	public BuildpathContentProvider() {
		super(new BuildpathItemProviderAdapterFactory());
	}

	@Override
	public Object getParent(final Object object) {
		if (object instanceof final EObject eObject && eObject.eContainer() == null) {
			return null; // prevent returning resource as parent
		}
		return super.getParent(object);
	}
}
