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
import org.eclipse.jface.viewers.IFilter;

public class TypedFBNetworkElementFilter implements IFilter {
	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof SubAppForFBNetworkEditPart) {
			return ((SubAppForFBNetworkEditPart) toTest).getModel().getType() != null; // only for typed
		}
		if (toTest instanceof AbstractFBNElementEditPart) {
			return true;
		}
		return false;
	}
}
