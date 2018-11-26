/*******************************************************************************
 * Copyright (c) 2015 Profactor GbmH 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.figures;

import org.eclipse.draw2d.geometry.Point;

public interface InteractionStyleFigure {

	int REGION_CONNECTION = 0; 
	int REGION_DRAG = 1; 
	
	int getIntersectionStyle(Point location);
	
}
