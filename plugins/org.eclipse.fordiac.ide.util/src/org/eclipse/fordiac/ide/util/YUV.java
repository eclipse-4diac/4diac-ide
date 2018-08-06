/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH, 2018 TU Vienna/ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Martin Melik Merkumians
 *     - reduces visibility of fields
 *******************************************************************************/
package org.eclipse.fordiac.ide.util;

import org.eclipse.swt.graphics.RGB;

/** simple class for representing colors in YUV color space
 * 
 */
public class YUV {
	
	private double y;
	private double u;
	private double v;
	
	public YUV(RGB rgb){
		y = 0.299 * rgb.red + 0.587 * rgb.green + 0.114 * rgb.blue;
		u = -0.14713 * rgb.red - 0.28886 * rgb.green + 0.436 * rgb.blue;
		v = 0.615 * rgb.red - 0.51499 * rgb.green - 0.10001 * rgb.blue;
	}

	public boolean nearbyColor(YUV yuv) {
		double diffY = y - yuv.y; 
		double diffU = u - yuv.u;
		double diffV = v - yuv.v;
		
		double squaredDistance =  (diffY * diffY + diffU * diffU + diffV * diffV);		
		return (squaredDistance < 600);
	}

}
