/*******************************************************************************
 * Copyright (c) 2008 - 2015 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Thomas Strasser, Martin Melik Merkumians, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.branding.IProductConstants;
import org.eclipse.ui.splash.BasicSplashHandler;

/**
 * SplashHandler for the 4diac IDE diplaying a progress bar.
 * 
 * Although that this splash handler is only doing default things it helps us
 * that the ide plugin will be the first plugin that will be loaded. With that
 * the image url handler is also loaded correctly. Until we are not moving to an
 * full E4 model we need to keep this SplashHandler.
 * 
 * @author gebenh
 * 
 */
public class SplashHandler extends BasicSplashHandler {

	/**
	 * 
	 */
	public SplashHandler() {
		super();

	}

	@Override
	public void init(Shell splash) {
		super.init(splash);
		String progressRectString = null;
		IProduct product = Platform.getProduct();
		if (product != null) {
			progressRectString = product.getProperty(IProductConstants.STARTUP_PROGRESS_RECT);
		}
		Rectangle progressRect = StringConverter.asRectangle(progressRectString, new Rectangle(10, 10, 300, 15));
		setProgressRect(progressRect);
	}

}
