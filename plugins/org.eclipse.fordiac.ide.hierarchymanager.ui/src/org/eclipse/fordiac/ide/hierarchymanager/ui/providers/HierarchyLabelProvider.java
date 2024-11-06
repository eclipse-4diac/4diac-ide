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
 *   Fabio Gandolfi - added Tooltip support
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.providers;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.provider.HierarchyItemProviderAdapterFactory;

public class HierarchyLabelProvider extends AdapterFactoryLabelProvider {

	public HierarchyLabelProvider() {
		super(new HierarchyItemProviderAdapterFactory());
	}
}
