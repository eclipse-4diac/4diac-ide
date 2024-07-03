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
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class LibraryPlugin extends Plugin {
	private static LibraryPlugin plugin;

	private static synchronized void setPlugin(final LibraryPlugin instance) {
		plugin = instance;
	}

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		setPlugin(this);
		LibraryManager.INSTANCE.startEventBroker(context);
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		LibraryManager.INSTANCE.stopEventBroker();
		setPlugin(null);
		super.stop(context);
	}

	public static LibraryPlugin getDefault() {
		return plugin;
	}
}
