/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.jface.viewers.IFilter;

/**
 * filter that checks whether object toTest is a typed fbnetworkelement (i.e.,
 * an instance of a typed subapp or an fbtype). Untyped subapps are never
 * counted
 *
 */
public class FBNetworkElementTypeFilter implements IFilter {
	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof SubAppForFBNetworkEditPart) {
			final SubApp subapp = ((SubAppForFBNetworkEditPart) toTest).getModel();
			return isTyped(subapp);
		}
		return (toTest instanceof AbstractFBNElementEditPart);
	}

	private static boolean isTyped(final SubApp subapp) {
		return (subapp.getType() != null);
	}
}
