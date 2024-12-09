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
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.providers;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.fordiac.ide.model.edit.providers.CFBInstanceItemProviderForSystem;
import org.eclipse.fordiac.ide.model.edit.providers.UntypedSubAppItemProviderForSystem;
import org.eclipse.fordiac.ide.model.libraryElement.provider.AutomationSystemItemProvider;
import org.eclipse.fordiac.ide.model.libraryElement.provider.LibraryElementItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.provider.SystemConfigurationItemProvider;

public class AutomationSystemProviderAdapterFactory extends LibraryElementItemProviderAdapterFactory {

	@Override
	public Adapter createAutomationSystemAdapter() {
		if (automationSystemItemProvider == null) {
			automationSystemItemProvider = new AutomationSystemItemProvider(this);
		}
		return automationSystemItemProvider;
	}

	@Override
	public Adapter createApplicationAdapter() {
		if (applicationItemProvider == null) {
			applicationItemProvider = new ApplicationItemProviderForSystem(this);
		}
		return applicationItemProvider;
	}

	@Override
	public Adapter createSystemConfigurationAdapter() {
		if (systemConfigurationItemProvider == null) {
			systemConfigurationItemProvider = new SystemConfigurationItemProvider(this);
		}
		return systemConfigurationItemProvider;
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
	public Adapter createCFBInstanceAdapter() {
		if (cfbInstanceItemProvider == null) {
			cfbInstanceItemProvider = new CFBInstanceItemProviderForSystem(this);
		}
		return cfbInstanceItemProvider;
	}

}
