/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
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
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.eclipse.fordiac.ide.ui.UIPlugin;

public class FordiacImageURLConnection extends URLConnection {

	private String imageName;

	protected FordiacImageURLConnection(final URL url) {
		super(url);
		imageName = url.getAuthority();
		if (null == imageName) {
			imageName = url.getFile();
			if ((null != imageName) && (imageName.startsWith("/"))) { //$NON-NLS-1$
				imageName = imageName.substring(1);
			}
		}
	}

	@Override
	public InputStream getInputStream() throws IOException {
		InputStream is = null;
		try {
			final FordiacImage selectedIcon = FordiacImage.valueOf(imageName);
			is = selectedIcon.getImageAsInputStream();
		} catch (final Exception e) {
			UIPlugin.getDefault().logError(e.getMessage(), e);
			is = FordiacImage.MISSING.getImageAsInputStream();
		}
		return is;
	}

	@Override
	public void connect() throws IOException {
		// nothing to be done here
	}

}
