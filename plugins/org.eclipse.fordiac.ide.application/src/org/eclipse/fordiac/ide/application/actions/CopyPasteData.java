/*******************************************************************************
 * Copyright (c) 2021, 2023 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.application.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.fordiac.ide.application.commands.ConnectionReference;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;

public record CopyPasteData(FBNetwork srcNetwork, List<FBNetworkElement> elements, Set<ConnectionReference> conns) {

	public CopyPasteData(final FBNetwork srcNetwork) {
		this(srcNetwork, new ArrayList<>(), new HashSet<>());
	}

	public boolean isEmpty() {
		return elements.isEmpty() && conns.isEmpty();
	}

	public static final CopyPasteData EMPTY_COPY_PASTE_DATA = new CopyPasteData(null);
}
