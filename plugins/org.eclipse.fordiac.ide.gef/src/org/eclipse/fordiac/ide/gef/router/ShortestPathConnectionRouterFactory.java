/*******************************************************************************
 * Copyright (c) 2010, 2012, 2013 Uni Halle, Profactor GmbH, fortiss GmbH 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian Gerber, Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.router;

import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ShortestPathConnectionRouter;

/**
 * A factory for creating ShortestPathConnectionRouter objects.
 */
public class ShortestPathConnectionRouterFactory extends AbstractConnectionRouterFactory {


	@Override
	public ConnectionRouter getConnectionRouter(IFigure container) {
		return new ShortestPathConnectionRouter(container);
	}

}