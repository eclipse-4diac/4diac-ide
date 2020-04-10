/*******************************************************************************
 * Copyright (c) 2008, 2009, 2013, 2014, 2016 Profactor GbmH, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.provider;

import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.jface.viewers.IStructuredContentProvider;

public class VersionContentProvider implements IStructuredContentProvider {
	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof FBType) {
			return ((FBType) inputElement).getVersionInfo().toArray();
		}
		return new Object[] {};
	}
}
