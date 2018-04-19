/*******************************************************************************
 * Copyright (c) 2011 Profactor GmbH
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
package org.eclipse.fordiac.ide.systemmanagement.extension;

import org.eclipse.core.runtime.IPath;

public interface ITagProvider {
	/**
	 * 
	 * @param project the path, where the ITagProvider should load his data,  filename is up to the provider (see save)
	 * @return true if loading was ok otherwise false
	 */
	boolean loadTagConfiguration(IPath project);
	/**
	 * 
	 * @param project the path, where the ITagProvider should save his data, filename is up to the provider
	 * @return true if loading was ok otherwise false
	 */
	boolean saveTagConfiguration(IPath project);

	/**
	 * returns a replaced string for values between %value%
	 * @param configuredObject an object like fb, device or resource
	 * @param value 
	 * @return the value of the (if exists) active configuration, otherwise null
	 */
	String getReplacedString(String value);
	
	/**
	 * the main element of the internal datastructure - is created in the initializeNewTagProvider() method
	 * @return
	 */
	Object getModelObject();
	
	/**
	 * is called, if a new TagProvider is created
	 */
	void initialzeNewTagProvider();
}
