/********************************************************************************
 * Copyright (c) 2008, 2009, 2011 Profactor GmbH, TU Wien ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.libraryElement.DeviceType;

/**
 * The Interface IDeviceTypeImporter.
 */
public interface IDeviceTypeImporter {

	/**
	 * Import device type.
	 * 
	 * @param file the file
	 * 
	 * @return the device type
	 */
	DeviceType importDEVType(final IFile file);

	/**
	 * Supports type.
	 * 
	 * @param type the type
	 * 
	 * @return true, if successful
	 */
	boolean supportsType(String type);

}
