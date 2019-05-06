/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012 - 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImageURLStreamHandlerService;
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

		plugin = this;
		
		disableJFaceErrorMessages();
	}

	private static void disableJFaceErrorMessages() {
		//set a special status handler which will do nothing here.
		//this should then be correctly handled by automatic error reporting.
		Policy.setStatusHandler(new StatusHandler() {
			@Override
			public void show(IStatus status, String title) {				
				//do nothing
			}
		});
		
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
		plugin = null;
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
