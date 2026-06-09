/********************************************************************************
 * Copyright (c) 2026 Johannes Kepler University Austria
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Alois Zoitl - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.resource;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

public interface LibraryElementResource extends Resource {

	default LibraryElement getLibraryElement() {
		return (!getContents().isEmpty() && getContents().get(0) instanceof final LibraryElement le) ? le : null;
	}
}
