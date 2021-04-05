/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.helpers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class FBNetworkElementHelper {

	public static boolean isContainedInTypedInstance(final FBNetworkElement element) {
		EObject obj = element;
		while (obj.eContainer() != null) {
			obj = obj.eContainer();
			if (((obj instanceof SubApp) && ((SubApp) obj).isTyped()) || (obj instanceof CFBInstance)) {
				return true;
			}
		}
		return false;
	}

}
