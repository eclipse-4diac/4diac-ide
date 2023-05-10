/*******************************************************************************
 * Copyright (c) 2023 Primetals Technology Austria GmbH
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
package org.eclipse.fordiac.ide.hierarchymanager.ui.providers;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.provider.HierarchyItemProviderAdapterFactory;

public class HierarchyContentProvider extends AdapterFactoryContentProvider {

	public HierarchyContentProvider() {
		super(new HierarchyItemProviderAdapterFactory());
	}

	@Override
	public Object getParent(final Object object) {
		if (object instanceof RootLevel) {
			// we don't want to provide anything above our root level
			// this avoids an frame navigation buttons in the plant hierarchy view.
			return null;
		}
		return super.getParent(object);
	}
}
