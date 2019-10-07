/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GbmH 
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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
