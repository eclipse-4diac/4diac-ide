/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH, 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util.imageprovider;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.fordiac.ide.util.Activator;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.url.AbstractURLStreamHandlerService;
import org.osgi.service.url.URLConstants;
import org.osgi.service.url.URLStreamHandlerService;

public class FordiacImageURLStreamHandlerService extends
		AbstractURLStreamHandlerService {
	
	private static final String FORDIAC_IMAGE_PROTOCOL = "fordiacimage"; //$NON-NLS-1$

	private static FordiacImageURLStreamHandlerService instance;
	private ServiceRegistration<URLStreamHandlerService> iconUrlHandler;

	public static FordiacImageURLStreamHandlerService getInstance() {
		if (null == instance) {
			instance = new FordiacImageURLStreamHandlerService();
		}
		return instance;
	}

	public void register() {
		Bundle bundle = FrameworkUtil.getBundle(FordiacImageURLStreamHandlerService.class);
		BundleContext bundleContext = bundle.getBundleContext();
		try {
			Dictionary<String, String[]> properties = new Hashtable<>();
			properties.put(URLConstants.URL_HANDLER_PROTOCOL, new String[] { FORDIAC_IMAGE_PROTOCOL });
			iconUrlHandler = bundleContext.registerService(URLStreamHandlerService.class, this, properties);
		} catch (Exception e) {
			Activator.getDefault().logError("Could not register icon URL handler.", e);
		}
		Activator.getDefault().logError("Icon URL handler registered.");
	}

	public void unregister() {
		try {
			if (iconUrlHandler != null) {
				iconUrlHandler.unregister();
				iconUrlHandler = null;
			}
		} catch (Exception e) {
			Activator.getDefault().logError("Could not register icon URL handler.", e);
		}
	}

	@Override
	public URLConnection openConnection(URL u) throws IOException {
		return new FordiacImageURLConnection(u);
	}

}
