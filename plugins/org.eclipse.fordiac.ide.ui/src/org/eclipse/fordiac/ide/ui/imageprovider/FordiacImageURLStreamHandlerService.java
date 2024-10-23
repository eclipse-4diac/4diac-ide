/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH, 2018 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.imageprovider;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Dictionary;
import java.util.Map;

import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.url.AbstractURLStreamHandlerService;
import org.osgi.service.url.URLConstants;
import org.osgi.service.url.URLStreamHandlerService;

public class FordiacImageURLStreamHandlerService extends AbstractURLStreamHandlerService {

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
		final Bundle bundle = FrameworkUtil.getBundle(FordiacImageURLStreamHandlerService.class);
		final BundleContext bundleContext = bundle.getBundleContext();
		try {
			final Dictionary<String, String[]> properties = FrameworkUtil
					.asDictionary(Map.of(URLConstants.URL_HANDLER_PROTOCOL, new String[] { FORDIAC_IMAGE_PROTOCOL }));
			iconUrlHandler = bundleContext.registerService(URLStreamHandlerService.class, this, properties);
		} catch (final Exception e) {
			FordiacLogHelper.logError("Could not register icon URL handler.", e); //$NON-NLS-1$
		}
	}

	public void unregister() {
		try {
			if (iconUrlHandler != null) {
				iconUrlHandler.unregister();
				iconUrlHandler = null;
			}
		} catch (final Exception e) {
			FordiacLogHelper.logError("Could not un-register icon URL handler.", e); //$NON-NLS-1$
		}
	}

	@Override
	public URLConnection openConnection(final URL u) throws IOException {
		return new FordiacImageURLConnection(u);
	}

}
