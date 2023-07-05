/*******************************************************************************
 * Copyright (c) 2021 - 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *   Fabio Gandolfi - added ItemProviders for Model Compare
 *******************************************************************************/
package org.eclipse.fordiac.ide.emf.compare.provider;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.fordiac.ide.systemmanagement.ui.providers.AutomationSystemProviderAdapterFactory;

public class AutomationSystemItemProviderEmfCompare extends AutomationSystemProviderAdapterFactory {

	@Override
	public Adapter createApplicationAdapter() {
		if (applicationItemProvider == null) {
			applicationItemProvider = new ApplicationItemProviderEmfCompare(this);
		}
		return applicationItemProvider;
	}

	@Override
	public Adapter createFBNetworkElementAdapter() {
		if (fbNetworkElementItemProvider == null) {
			fbNetworkElementItemProvider = new FbNetworkElementItemProviderEmfCompare(this);
		}
		return fbNetworkElementItemProvider;
	}

	@Override
	public Adapter createSubAppAdapter() {
		if (subAppItemProvider == null) {
			subAppItemProvider = new SubAppItemProviderEmfCompare(this);
		}
		return subAppItemProvider;
	}

	@Override
	public Adapter createCFBInstanceAdapter() {
		if (cfbInstanceItemProvider == null) {
			cfbInstanceItemProvider = new CFBInstanceItemProviderEmfCompare(this);
		}
		return cfbInstanceItemProvider;
	}

	@Override
	public Adapter createFBAdapter() {
		if (fbItemProvider == null) {
			fbItemProvider = new FBItemProviderEmfCompare(this);
		}
		return fbItemProvider;
	}

	@Override
	public Adapter createEventConnectionAdapter() {
		if (eventConnectionItemProvider == null) {
			eventConnectionItemProvider = new EventConnectionItemProviderEmfCompare(this);
		}
		return eventConnectionItemProvider;
	}

	@Override
	public Adapter createDataConnectionAdapter() {
		if (dataConnectionItemProvider == null) {
			dataConnectionItemProvider = new DataConnectionItemProviderEmfCompare(this);
		}
		return dataConnectionItemProvider;
	}

	@Override
	public Adapter createAdapterConnectionAdapter() {
		if (adapterConnectionItemProvider == null) {
			adapterConnectionItemProvider = new AdapterConnectionItemProviderEmfCompare(this);
		}
		return adapterConnectionItemProvider;
	}

	@Override
	public Adapter createInterfaceListAdapter() {
		return new InterfaceListItemProviderEmfCompare(this);
	}

	@Override
	public Adapter createFBNetworkAdapter() {
		if (fbNetworkItemProvider == null) {
			fbNetworkItemProvider = new FBNetworkItemProviderEmfCompare(this);
		}
		return fbNetworkItemProvider;
	}

	@Override
	public Adapter createGroupAdapter() {
		if (groupItemProvider == null) {
			groupItemProvider = new GroupItemProviderEmfCompare(this);
		}
		return groupItemProvider;
	}

	@Override
	public Adapter createEventAdapter() {
		if (eventItemProvider == null) {
			eventItemProvider = new EventItemProviderEmfCompare(this);
		}
		return eventItemProvider;
	}

	@Override
	public Adapter createVarDeclarationAdapter() {
		if (varDeclarationItemProvider == null) {
			varDeclarationItemProvider = new VarDeclarationItemProviderEmfCompare(this);
		}
		return varDeclarationItemProvider;
	}

	@Override
	public Adapter createAdapterDeclarationAdapter() {
		if (adapterDeclarationItemProvider == null) {
			adapterDeclarationItemProvider = new AdapterDeclarationItemProviderEmfCompare(this);
		}
		return adapterDeclarationItemProvider;
	}

	@Override
	public Adapter createWithAdapter() {
		if (withItemProvider == null) {
			withItemProvider = new WithItemProviderEmfCompare(this);
		}
		return withItemProvider;
	}

	@Override
	public Adapter createValueAdapter() {
		return super.createValueAdapter();
	}
}
