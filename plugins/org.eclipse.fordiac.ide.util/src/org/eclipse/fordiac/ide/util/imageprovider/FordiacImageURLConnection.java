/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
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
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class FordiacImageURLConnection extends URLConnection {

	private String imageName;

	protected FordiacImageURLConnection(URL url) {
		super(url);
		imageName = url.getAuthority();
		if(null == imageName){
			imageName = url.getFile();
			if((null != imageName) && (imageName.startsWith("/"))){ //$NON-NLS-1$
				imageName = imageName.substring(1);
			}
		}
	}
	
	@Override
	public InputStream getInputStream() throws IOException {
		InputStream is = null;
		try{
			FordiacImage selectedIcon = FordiacImage.valueOf(imageName);
			is = selectedIcon.getImageAsInputStream();
		} catch (Exception e) {
			is = FordiacImage.Missing.getImageAsInputStream();			
		}
		return is;
	}

	@Override
	public void connect() throws IOException {
	}

}
