/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012, 2016 Profactor GbmH, fortiss GmbH 
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

import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;

/**
 * The Interface IChildrenProvider.
 * 
 * @author gebenh
 */
public interface IChildrenProvider {

	/**
	 * Returns a list of children,.
	 * 
	 * @param diagram the diagram
	 * 
	 * @return a list of children
	 */
	List<IEditPartCreator> getChildren(FBNetwork diagram);

	/**
	 * Checks if is enabled.
	 * 
	 * @return true, if is enabled
	 */
	boolean isEnabled();

}
