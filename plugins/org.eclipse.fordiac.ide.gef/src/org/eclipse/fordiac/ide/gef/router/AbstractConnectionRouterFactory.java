/*******************************************************************************
 * Copyright (c) 2012 Profactor GmbH 
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
package org.eclipse.fordiac.ide.gef.router;

import org.eclipse.draw2d.PolylineConnection;

public abstract class AbstractConnectionRouterFactory implements IConnectionRouterFactory {

	@Override
	public PolylineConnection createConnectionFigure() {
		return new PolylineConnection();
	}

}
