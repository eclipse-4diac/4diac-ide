/********************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Ernst Blecha
 *    - copy existing Plugin Activator from model plugin
 ********************************************************************************/
package org.eclipse.fordiac.ide.ui.errormessages;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends Plugin {

	/** The Constant PLUGIN_ID. */
	public static final String PLUGIN_ID = "org.eclipse.fordiac.ide.ui.errormessages"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private static synchronized void setPlugin(final Activator instance) {
		plugin = instance;
	}

	/**
	 * The constructor.
	 */
	public Activator() {
		// empty constructor
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		setPlugin(this);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		setPlugin(null);
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
}
