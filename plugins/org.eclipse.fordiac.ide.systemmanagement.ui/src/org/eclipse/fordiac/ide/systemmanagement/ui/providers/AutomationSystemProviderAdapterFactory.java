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
import org.eclipse.fordiac.ide.model.libraryElement.provider.AutomationSystemItemProvider;
import org.eclipse.fordiac.ide.model.libraryElement.provider.LibraryElementItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.provider.SystemConfigurationItemProvider;
import org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer.ApplicationItemProviderForSystem;
import org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer.SubAppItemProviderForSystem;

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
	public Adapter createSubAppAdapter() {
		if (subAppItemProvider == null) {
			subAppItemProvider = new SubAppItemProviderForSystem(this);
		}
		return subAppItemProvider;
	}

}
