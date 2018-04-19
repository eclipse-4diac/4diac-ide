/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_lua;

import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends Abstract4DIACUIPlugin {

	private static Abstract4DIACUIPlugin plugin;
	
	public static Abstract4DIACUIPlugin getDefault() {
		return plugin;
	}
	
	public Activator() {
		//empty constructur 
	}

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

}
