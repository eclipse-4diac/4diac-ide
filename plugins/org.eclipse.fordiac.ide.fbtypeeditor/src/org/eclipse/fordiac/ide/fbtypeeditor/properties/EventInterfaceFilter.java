/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GmbH
 * 				 2020        Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Virendra Ashiwal
 *     - extracted selectable objects to an seperate final class with
 *       static method (getSelectableObject)
 *
 *
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.jface.viewers.IFilter;

public class EventInterfaceFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		return (InterfaceFilterSelection.getSelectableInterfaceElementOfType(toTest) instanceof Event);
	}

}
