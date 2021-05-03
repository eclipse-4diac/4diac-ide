/*******************************************************************************
 * Copyright (c) 2020 Sandor Bacsi
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sandor Bacsi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.validation;

import org.eclipse.fordiac.ide.ui.Abstract4DIACUIPlugin;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.OCL;
import org.osgi.framework.BundleContext;

public class Activator extends Abstract4DIACUIPlugin {

	public static final String PLUGIN_ID = "org.eclipse.fordiac.ide.validation"; //$NON-NLS-1$

	private static Activator plugin;

	private OCL oclInstance;

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		setPlugin(this);
		oclInstance = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		setPlugin(null);
		super.stop(context);
	}

	private static synchronized void setPlugin(Activator newPlugin) {
		plugin = newPlugin;
	}

	public static Activator getDefault() {
		return plugin;
	}

	public OCL getOclInstance() {
		return oclInstance;
	}
}
