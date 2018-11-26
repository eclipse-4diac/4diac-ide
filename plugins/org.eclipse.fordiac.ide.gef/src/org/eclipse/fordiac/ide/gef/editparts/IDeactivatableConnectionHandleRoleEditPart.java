/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GbmH 
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
package org.eclipse.fordiac.ide.gef.editparts;

/**
 * Interface for an editpart that makes possible that the connectionhandle role
 * can be disabled.
 */
public interface IDeactivatableConnectionHandleRoleEditPart {

	/**
	 * Specifies whether the connectionhandlerole should be enabled or disabled.
	 * 
	 * @param enabled the enabled
	 */
	void setConnectionHandleRoleEnabled(boolean enabled);

}
