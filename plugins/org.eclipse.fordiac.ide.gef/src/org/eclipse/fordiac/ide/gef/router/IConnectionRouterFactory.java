/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012 Profactor GmbH 
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
package org.eclipse.fordiac.ide.gef.router;

import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;

/**
 * A factory for creating IConnectionRouter objects.
 */
public interface IConnectionRouterFactory {

	/**
	 * Gets the connection router.
	 * 
	 * @param container
	 *          the container
	 * 
	 * @return the connection router
	 */
	ConnectionRouter getConnectionRouter(IFigure container);
	
	/**
	 * Creates the Connectionfigure for this router which needs to be a PolylineConnection.
	 */
	PolylineConnection createConnectionFigure();

}
