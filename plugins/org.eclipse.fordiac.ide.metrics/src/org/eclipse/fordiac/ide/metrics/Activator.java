/*******************************************************************************
 * Copyright (c) 2019 TU Wien, ACIN
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Peter Gsellmann - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.metrics;

import org.eclipse.fordiac.ide.ui.Abstract4DIACUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends Abstract4DIACUIPlugin {

	public static final String PLUGIN_ID = " org.eclipse.fordiac.ide.metrics"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}

}
