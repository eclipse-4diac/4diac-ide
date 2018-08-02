/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GmbH 
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

import org.eclipse.gef.EditPolicy;

public interface BendpointPolicyRouter {
	/**
	 * 
	 * @return a bendpoint editpolicy suitable for the router returned in
	 *         getConnectionRouter
	 */
	EditPolicy getBendpointPolicy(final Object modelObject);
}
