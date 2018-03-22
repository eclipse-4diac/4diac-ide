/*******************************************************************************
 * Copyright (c) 2008, 2009, 2014, 2016, 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands;

import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * This class is the main class for the org.eclipse.fordiac.ide.application plugin. It
 * coordinates changes in data in different views. (e.g. changes in Workspace)
 * 
 */
public class ModelCommandsPlugin extends Abstract4DIACUIPlugin {
	public static final String PLUGIN_ID = "org.eclipse.fordiac.ide.model.commands"; //$NON-NLS-1$
	// The shared instance.
	private static Abstract4DIACUIPlugin plugin;
		
	public static Abstract4DIACUIPlugin getDefault() {
		return plugin;
	}
	
	public ModelCommandsPlugin() {
		//empty constructur 
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
		super.start(context);
		plugin = this;
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
		super.stop(context);
		plugin = null;
	}
}
