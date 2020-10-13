/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.elk;

import org.eclipse.fordiac.ide.ui.Abstract4DIACUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends Abstract4DIACUIPlugin {
	public static final String PLUGIN_ID = "org.eclipse.fordiac.ide.elk"; //$NON-NLS-1$
	// The shared instance.
	private static Activator plugin;

	/**
	 * The constructor.
	 */
	public Activator() {
		// empty constructor
	}

	/**
	 * This method is called upon plug-in activation.
	 *
	 * @param context the context
	 *
	 * @throws Exception the exception
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		// here reserve resources if needed for the plugin, "plugin constructor"
		super.start(context);
		setPlugin(this);
	}

	private static synchronized void setPlugin(Activator uiPlugin) {
		plugin = uiPlugin;
	}

	/**
	 * This method is called when the plug-in is stopped.
	 *
	 * @param context the context
	 *
	 * @throws Exception the exception
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		// here, free resources if not needed anymore, "plugin destructor"
		super.stop(context);
		setPlugin(null);
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the default
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
