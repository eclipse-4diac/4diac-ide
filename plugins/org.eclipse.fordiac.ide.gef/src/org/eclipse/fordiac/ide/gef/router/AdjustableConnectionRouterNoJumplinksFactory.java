/*******************************************************************************
 * Copyright (c) 2012 - 2015 Profactor GmbH, fortiss GmbH 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.router;

import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.fordiac.ide.gef.figures.HideableConnection;

public class AdjustableConnectionRouterNoJumplinksFactory implements IConnectionRouterFactory {

	public AdjustableConnectionRouterNoJumplinksFactory() {
		// empty router
	}

	@Override
	public ConnectionRouter getConnectionRouter(IFigure container) {
		return new MoveableRouter();
	}

	@Override
	public PolylineConnection createConnectionFigure() {
		PolylineConnection connection = null;
		connection = new HideableConnection();
		return connection;
	}
}
