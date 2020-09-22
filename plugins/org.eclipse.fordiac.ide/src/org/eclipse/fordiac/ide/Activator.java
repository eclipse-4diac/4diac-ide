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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImageURLStreamHandlerService;
import org.eclipse.jface.util.Policy;
import org.eclipse.jface.util.StatusHandler;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Version;

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

		setVersionAndBuildID(context);

		if (isDebugging()) {
			addLogListener();
		}
		disableJFaceErrorMessages();
		// As the ide plugin is the very first plugin loaded get here the system manager
		// instance to initialize the toollib and resource change listener.
		// The variable is not needed therefore the suppress warning
		@SuppressWarnings("unused")
		SystemManager mgr = SystemManager.INSTANCE;
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

	// this is somehow needed so that version and build ID are correctly shown in
	// About dialog
	private static void setVersionAndBuildID(final BundleContext context) {
		Version v = context.getBundle().getVersion();
		String version = v.getMajor() + "." + v.getMinor() + "." + v.getMicro(); //$NON-NLS-1$ //$NON-NLS-2$
		System.setProperty("org.eclipse.fordiac.ide.version", version); //$NON-NLS-1$

		String qualifier = v.getQualifier();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm"); //$NON-NLS-1$
			Date d = sdf.parse(qualifier);
			SimpleDateFormat sdfVisu = new SimpleDateFormat("yyyy-MM-dd_HHmm"); //$NON-NLS-1$
			qualifier = sdfVisu.format(d);
		} catch (Exception ex) {
			// can be ignored
		}

		System.setProperty("org.eclipse.fordiac.ide.buildid", qualifier); //$NON-NLS-1$

	}

}
