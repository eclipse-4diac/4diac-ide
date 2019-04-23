/********************************************************************************
 * Copyright (c) 2019 Johannes Kepler University, Linz
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model;

import org.eclipse.draw2d.FigureUtilities;


/** Helper class for transforming between the coordination resolution between IEC 61499-2 Annex B.1 
 * definitions to the resolution used in 4diac IDE editors.
 *
 */
public enum CoordinateConverter {
	INSTANCE;
	
	/** The scaler transformation value for coordinate conversion.
	 * 
	 * In general it the lineHeigth / 100 as defined in IEC 61499-2 Annex B.1
	 */
	private double transformationScale;
	
	private CoordinateConverter() {
		double lineHeight = FigureUtilities.getFontMetrics(null).getHeight();  //null means to use the default font, needs update when we get setable fonts
		transformationScale = lineHeight / 100.0; 
	}
	
	
	/** Take the string representing the value from the 61499-2 XML file to an 4diac IDE internal coordinate
	 * 
	 * @param value	string representation of a coordinate value
	 * @return 		4diac IDE internal coordinate value
	 */
	public int convertFrom1499XML(final String value) {
		double parsedValue = 0;
		if ((null != value) && (0 != value.length())){
			parsedValue = Double.parseDouble(value);					
		}
		return (int)(parsedValue * transformationScale);
	}
	
	/** Take an 4diac IDE internal coordinate and transform it to a string representation suitable for an IEC 61499-2 XML
	 * 
	 * @param value	4diac IDE internal coordinate
	 * @return 		string representation of the value in IEC 61499-2 format
	 */
	public String convertTo1499XML(final int value) {
		return Double.toString(value / transformationScale);
	}


}
