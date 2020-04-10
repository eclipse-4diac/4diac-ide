/*******************************************************************************
 * Copyright (c) 2009, 2012, 2015, 2017 Profactor GmbH, fortiss GmbH
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

import org.eclipse.gef.EditPart;

/**
 * Objects implementing this element can create an EditPart (for graphical
 * visualisation in GEF editors).
 */
public interface IEditPartCreator {

	/**
	 * Creates the edit part.
	 * 
	 * @return the created EditPart
	 */
	EditPart createEditPart();

}
