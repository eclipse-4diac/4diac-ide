/*******************************************************************************
 * Copyright (c) 2015 Profactor GbmH 
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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
