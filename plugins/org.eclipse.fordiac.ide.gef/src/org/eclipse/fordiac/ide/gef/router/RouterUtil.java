/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012, 2014, 2015 Profactor GmbH, fortiss GmbH,
 * 				 2018 Johannes Kepler University 
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
package org.eclipse.fordiac.ide.gef.router;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;

/**
 * The Class RouterUtil.
 */
public class RouterUtil {

	/**
	 * Gets the connection router.
	 * 
	 * @param container the container
	 * 
	 * @return the connection router
	 */
	public static ConnectionRouter getConnectionRouter(IFigure container) {
		return getConnectionRouterFactory(container).getConnectionRouter(container);
	}
	
	
	/**
	 * Gets the connection router factory
	 * 
	 * @param container the container
	 * 
	 * @return the connection router
	 */
	public static IConnectionRouterFactory getConnectionRouterFactory(IFigure container) {
		Map<String, IConnectionRouterFactory> connectionRouter = new HashMap<>();

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elems = registry.getConfigurationElementsFor(
				Activator.PLUGIN_ID, "ConnectionRouterProvider"); //$NON-NLS-1$
		for (int i = 0; i < elems.length; i++) {
			IConfigurationElement element = elems[i];
			try {
				Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				String name = element.getAttribute("name"); //$NON-NLS-1$
				if (object instanceof IConnectionRouterFactory) {
					IConnectionRouterFactory routerFactory = (IConnectionRouterFactory) object;
					connectionRouter.put(name, routerFactory);
				}
			} catch (CoreException corex) {
				Activator.getDefault().logError("Error loading ConnectionRouter", corex); //$NON-NLS-1$
			}
		}
		String router = Activator.getDefault().getPreferenceStore().getString(
				DiagramPreferences.CONNECTION_ROUTER);

		IConnectionRouterFactory factory = connectionRouter.get(router);
		if (factory == null) { // the prefered router does not exist - use default
			// one
			factory = new AdjustableConnectionRouterNoJumplinksFactory();
		}
		return factory;
	}
	
	
	
}
