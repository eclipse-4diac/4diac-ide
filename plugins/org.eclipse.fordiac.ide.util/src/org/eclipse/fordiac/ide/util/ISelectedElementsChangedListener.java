/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GbmH
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
package org.eclipse.fordiac.ide.util;

/**
 * The listener interface for receiving ISelectedElementsChanged events.
 * The class that is interested in processing a ISelectedElementsChanged
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addISelectedElementsChangedListener<code> method. When
 * the ISelectedElementsChanged event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see ISelectedElementsChangedEvent
 */
public interface ISelectedElementsChangedListener {

	/**
	 * Selection changed.
	 */
	void selectionChanged();

}