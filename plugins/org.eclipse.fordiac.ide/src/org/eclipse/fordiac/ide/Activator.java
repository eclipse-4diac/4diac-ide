/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012 - 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImageURLStreamHandlerService;
import org.eclipse.jface.util.Policy;
import org.eclipse.jface.util.StatusHandler;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends AbstractUIPlugin {

	/** The Constant PLUGIN_ID. */
	public static final String PLUGIN_ID = "org.eclipse.fordiac.ide"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private ILogListener listener;

	/**
	 * The constructor.
	 */
	public Activator() {
		// empty constructor
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(final BundleContext context) throws Exception {

		FordiacImageURLStreamHandlerService.getInstance().register();

		super.start(context);
		setPluginInstance(this);
		addLogListener();
		disableJFaceErrorMessages();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		removeLogListener();
		setPluginInstance(null);
		super.stop(context);
	}

	private static void setPluginInstance(Activator instance) {
		plugin = instance;
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	private static void disableJFaceErrorMessages() {
		// set a special status handler which will do nothing here.
		// this should then be correctly handled by automatic error reporting.
		Policy.setStatusHandler(new StatusHandler() {
			@Override
			public void show(IStatus status, String title) {
				// do nothing
			}
		});

	}

	private void addLogListener() {
		listener = new FordiacLogListener();
		Platform.addLogListener(listener);
	}

	private void removeLogListener() {
		if (null != listener) {
			Platform.removeLogListener(listener);
			listener = null;
		}
	}

}
