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
package org.eclipse.fordiac.ide.hierachymanager.ui.providers;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.provider.HierachPackageItemProviderAdapterFactory;

public class HierachyProvider extends AdapterFactoryLabelProvider {

	public HierachyProvider() {
		super(new HierachPackageItemProviderAdapterFactory());
	}

}
